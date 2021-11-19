package com.ozantopuz.rijksmuseumapp.shared.reader

import java.io.IOException

object AssetReader {

    @Throws(IOException::class)
    @JvmStatic
    fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String?
        try {
            jsonString = javaClass.classLoader?.getResourceAsStream(fileName)
                ?.bufferedReader()
                ?.use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}
