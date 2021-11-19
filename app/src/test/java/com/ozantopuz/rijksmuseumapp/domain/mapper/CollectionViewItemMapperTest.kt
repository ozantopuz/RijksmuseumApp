package com.ozantopuz.rijksmuseumapp.domain.mapper

import com.google.common.truth.Truth
import com.ozantopuz.rijksmuseumapp.domain.entity.ArtObject
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.shared.extension.runBlockingTest
import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionViewItemMapperTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var mapper: CollectionViewItemMapper

    @Before
    fun setUp() {
        mapper = CollectionViewItemMapper()
    }

    @Test
    fun `mapper should map domain item to view item type properly`() =
        coroutinesTestRule.runBlockingTest {
            // Given
            val artObjects = listOf(ArtObject(title = "title"))
            val domainItem = Collection(artObjects = artObjects)

            // When
            val viewItem = mapper.map(domainItem)

            // Then
            Truth.assertThat(viewItem).isNotNull()
            Truth.assertThat(viewItem.artObjects).isNotNull()
            Truth.assertThat(viewItem.artObjects.first().title).isEqualTo("title")
        }
}