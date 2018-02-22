package info.adavis.dao

import org.slf4j.Logger
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

abstract class CSVDataImporter {

    abstract fun import(log: Logger)

    fun importFromCsv(inputStream: InputStream, consumer: (Array<String>)-> Unit) {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        try {
            return bufferedReader.useLines { lines ->
                lines.drop(1).forEach { line ->
                    consumer(line.split("\\s*,\\s*".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to perform import", e)
        }
    }

}