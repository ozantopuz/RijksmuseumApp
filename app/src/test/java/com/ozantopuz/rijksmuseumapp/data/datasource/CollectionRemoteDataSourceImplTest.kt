package com.ozantopuz.rijksmuseumapp.data.datasource

import com.google.common.truth.Truth
import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.data.entity.CollectionResponse
import com.ozantopuz.rijksmuseumapp.data.mapper.CollectionDomainMapper
import com.ozantopuz.rijksmuseumapp.data.service.CollectionService
import com.ozantopuz.rijksmuseumapp.domain.entity.ArtObject
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import com.ozantopuz.rijksmuseumapp.shared.assertion.test
import com.ozantopuz.rijksmuseumapp.shared.base.BaseTestClass
import com.ozantopuz.rijksmuseumapp.shared.extension.runBlockingTest
import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CollectionRemoteDataSourceImplTest : BaseTestClass() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var collectionService: CollectionService

    @MockK
    private lateinit var mapper: CollectionDomainMapper

    @RelaxedMockK
    private lateinit var collectionResponse: CollectionResponse

    @RelaxedMockK
    private lateinit var collection: Collection

    private lateinit var dataSource: CollectionRemoteDataSource
    private val collectionSlot = slot<Map<String, String>>()

    override fun setUp() {
        super.setUp()

        coEvery { collectionService.getCollection(capture(collectionSlot)) } returns collectionResponse
        coEvery { mapper.map(any()) } returns collection

        every { collection.artObjects } returns listOf(ArtObject(title = "title"))

        dataSource = CollectionRemoteDataSourceImpl(collectionService, mapper)
    }

    @Test
    fun `data source should return data`() = coroutinesTestRule.runBlockingTest {
        // Given
        val collectionParams = CollectionParams(page = 0, query = "")

        // When
        val flow = dataSource.fetchCollection(collectionParams)

        // Then
        flow.test {
            expectItem().run {
                Truth.assertThat(this).isNotNull()
                Truth.assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                Truth.assertThat(data.artObjects).isNotNull()
                Truth.assertThat(data.artObjects.first().title).isEqualTo("title")
            }
            cancel()
            expectNoMoreEvents()
        }

        coVerifyOrder {
            collectionService.getCollection(mapOf())
            mapper.map(any())
        }

        confirmVerified(collectionService)
        confirmVerified(mapper)
    }
}
