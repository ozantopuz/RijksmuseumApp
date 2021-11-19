package com.ozantopuz.rijksmuseumapp.util.extension

fun String?.ignoreNull(defaultValue: String = ""): String = this ?: defaultValue
