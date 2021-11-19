package com.ozantopuz.rijksmuseumapp.util.mapper

@FunctionalInterface
interface Mapper<in T, out R> {

    suspend fun map(item: T): R
}