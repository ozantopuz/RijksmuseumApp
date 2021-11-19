package com.ozantopuz.rijksmuseumapp.data.mapper

import com.google.common.truth.Truth
import com.ozantopuz.rijksmuseumapp.data.entity.ArtObjectResponse
import com.ozantopuz.rijksmuseumapp.data.entity.CollectionResponse
import com.ozantopuz.rijksmuseumapp.shared.extension.runBlockingTest
import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionDomainMapperTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var mapper: CollectionDomainMapper

    @Before
    fun setUp() {
        mapper = CollectionDomainMapper()
    }

    @Test
    fun `mapper should map remote data source item to domain item type properly`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val artObjects = listOf(ArtObjectResponse(title = "title"))
            val remoteDataSourceItem = CollectionResponse(artObjects = artObjects)

            // When
            val domainItem = mapper.map(remoteDataSourceItem)

            // Then
            Truth.assertThat(domainItem).isNotNull()
            Truth.assertThat(domainItem.artObjects).isNotNull()
            Truth.assertThat(domainItem.artObjects.first().title).isEqualTo("title")
        }
}