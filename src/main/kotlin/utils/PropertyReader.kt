package utils

import java.io.File
import java.io.FileReader
import java.nio.file.Paths
import java.util.*

class PropertyReader {

    companion object {
        private val properties: MutableMap<String, Properties> = HashMap()
        private var instance: PropertyReader? = null
        fun getInstance(): PropertyReader {
            if (instance == null) {
                instance = PropertyReader()
            }
            return instance!!
        }
    }

    fun getProperties(fileName: String): Properties? {
        if (!properties.containsKey(fileName)) {
            val props = Properties()

            val fileReader = FileReader(File(Paths.get(fileName).toAbsolutePath().toString()))
            props.load(fileReader)
            fileReader.close()
            properties[fileName] = props
        }
        return properties[fileName]
    }
}