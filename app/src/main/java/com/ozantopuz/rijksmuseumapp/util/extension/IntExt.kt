package com.ozantopuz.rijksmuseumapp.util.extension

fun Int?.ignoreNull(defaultValue: Int = 0): Int = this ?: defaultValue
