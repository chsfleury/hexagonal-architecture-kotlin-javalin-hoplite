
CREATE TABLE fleet_starships (
    fleet_id VARCHAR(40) NOT NULL,
    name VARCHAR(255) NOT NULL,
    passengers_capacity INT NOT NULL,
    cargo_capacity DECIMAL(15, 0) NOT NULL,
    PRIMARY KEY (fleet_id, name)
)