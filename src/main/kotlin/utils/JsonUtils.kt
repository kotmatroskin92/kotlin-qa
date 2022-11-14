package utils

import com.google.gson.GsonBuilder
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class JsonUtils {

    companion object {
        fun <T> getObjectFromJson(jsonPath: String?, classOfT: Class<T>?): T {
            val gson = GsonBuilder().setLenient().setPrettyPrinting().create()
            var fileData: String? = null
            fileData = try {
                String(Files.readAllBytes(Paths.get(jsonPath)), StandardCharsets.UTF_8)
            } catch (e: IOException) {
                e.printStackTrace()
                throw RuntimeException(e.message, e)
            }
            return gson.fromJson(fileData, classOfT)
        }
    }
}