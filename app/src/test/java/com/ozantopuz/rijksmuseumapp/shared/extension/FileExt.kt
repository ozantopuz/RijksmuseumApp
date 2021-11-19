package com.ozantopuz.rijksmuseumapp.shared.extension

import com.ozantopuz.rijksmuseumapp.shared.reader.AssetReader
import java.io.IOException

@Throws(IOException::class)
fun asset(fileName: String) = AssetReader.getJsonDataFromAsset(fileName)
