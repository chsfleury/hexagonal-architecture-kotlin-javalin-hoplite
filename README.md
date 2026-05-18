# Hexagonal Architecture with Kotlin, Javalin and Hoplite

Simple sample project showing a hexagonal architecture implementation in Kotlin.

The domain is a Star Wars rescue fleet assembler: given a number of passengers, the application selects starships from SWAPI, stores the assembled fleet, and exposes it through an HTTP API.

This project is inspired by [hexagonal-architecture-java-springboot](https://gitlab.com/beyondxscratch/hexagonal-architecture-java-springboot), with a different technical stack.

## Stack

- Kotlin
- Maven
- Javalin for the HTTP API
- Hoplite for configuration
- http4k for HTTP client calls
- jOOQ for persistence
- Flyway for database migrations
- HikariCP for datasource management
- H2 for local/test database usage
- Jib Maven Plugin for Docker image builds
- JUnit 5, AssertJ, JSONAssert and WireMock for tests

## Architecture

The code is split around hexagonal architecture boundaries:

- `domain/{model}/model`: domain objects
- `domain/{model}/service`: domain services
- `domain/{model}/api`: primary ports . Use cases exposed by the domain
- `domain/{model}/spi`: secondary ports. Dependencies required by the domain
- `infra/api`: HTTP controllers, DTOs and API resources
- `infra/client`: external SWAPI adapter
- `infra/persistence`: database adapter
- `infra/configuration`: application configuration
- `infra/profile`: dependency wiring

## Requirements

- JDK 25
- Maven

## Run Tests

```bash
mvn test
```

## Build

```bash
mvn package
```

## Docker Image with Jib

The project uses `jib-maven-plugin` to build a Docker image without a Dockerfile.

The configured base image is:

```text
amazoncorretto:25
```

Build the project image into your local Docker daemon:

```bash
mvn compile jib:dockerBuild -Dimage=hexagonal-architecture-kotlin-javalin-hoplite:local
```

Run the image:

```bash
docker run --rm -p 8080:8080 hexagonal-architecture-kotlin-javalin-hoplite:local
```

You can also pass an external configuration file to the container. The file must be mounted in the container, then passed as the application argument:

```bash
docker run --rm -p 8080:8080 \
  -v /path/to/application.yml:/config/application.yml:ro \
  hexagonal-architecture-kotlin-javalin-hoplite:local \
  /config/application.yml
```

To build and push an image to a registry:

```bash
mvn compile jib:build -Dimage=registry.example.com/rebels-rescue:1.0.0
```

For private registries, authenticate with Docker first or configure the credential helper used by Jib.

## Run the Application

```bash
mvn exec:java -Dexec.mainClass=rebelsrescue.infra.StarwarsRebelsRescueApplication
```

The application uses `src/main/resources/application.yml` by default.

You can also pass a configuration file path as the first launch argument, for example to use a file outside the classpath:

```bash
mvn exec:java \
  -Dexec.mainClass=rebelsrescue.infra.StarwarsRebelsRescueApplication \
  -Dexec.args="/path/to/application.yml"
```

Default server port:

```text
8080
```

## API

### Create a rescue fleet

```bash
curl -X POST http://localhost:8080/rescueFleets \
  -H "Content-Type: application/json" \
  -d '{ "numberOfPassengers": 5 }'
```

Example response:

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "starships": [
    {
      "name": "Millennium Falcon",
      "capacity": 6,
      "passengersCapacity": 6,
      "deprecations": {
        "capacity": "passengersCapacity"
      }
    }
  ]
}
```

### Get a rescue fleet

```bash
curl http://localhost:8080/rescueFleets/{uuid}
```

## Configuration

Configuration is loaded with Hoplite from:

- environment variables
- `/application.yml` by default
- the configuration file path passed as the first launch argument

Default configuration:

```yaml
server:
  port: 8080

swapi:
  baseUrl: https://swapi.info/api/

database:
  username: rebels_rescue
  url: jdbc:mysql://localhost:3306/rebels_rescue?allowMultiQueries=true
  driver: com.mysql.cj.jdbc.Driver
  dialect: MYSQL
```

## Database

Database migrations are stored in:

- `src/main/resources/db/migration/h2`
- `src/main/resources/db/migration/mysql`

jOOQ Kotlin sources are generated from the H2 migration scripts during the Maven build.
