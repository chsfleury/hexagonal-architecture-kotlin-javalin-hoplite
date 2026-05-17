package rebelsrescue.infra.utils

import java.io.BufferedReader

fun getFileContent(path: String): String {
    return ClassLoader.getSystemResourceAsStream(path.removePrefix("/"))
        ?.bufferedReader()
        ?.use(BufferedReader::readText)
        ?: ""
}