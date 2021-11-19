package com.ozantopuz.rijksmuseumapp.data.repository

import com.google.common.truth.Truth
import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.data.datasource.CollectionRemoteDataSource
import com.ozantopuz.rijksmuseumapp.domain.entity.ArtObject
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.repository.CollectionRepository
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import com.ozantopuz.rijksmuseumapp.shared.assertion.test
import com.ozantopuz.rijksmuseumapp.shared.base.BaseTestClass
import com.ozantopuz.rijksmuseumapp.shared.extension.runBlockingTest
import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CollectionRepositoryImplTest : BaseTestClass() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var dataSource: CollectionRemoteDataSource

    @RelaxedMockK
    private lateinit var collection: Collection

    private lateinit var repository: CollectionRepository

    override fun setUp() {
        super.setUp()

        coEvery {
            dataSource.fetchCollection(CollectionParams(page = 0, query = null))
        } coAnswers {
            flowOf(Result.Success(collection))
        }

        every {
            collection.artObjects
        } returns listOf(ArtObject(title = "title"))

        repository = CollectionRepositoryImpl(dataSource)
    }

    @Test
    fun `repository should return data`() = coroutinesTestRule.runBlockingTest {
        // Given
        val collectionParams = CollectionParams(page = 0, query = null)

        // When
        val flow = repository.fetchCollection(collectionParams)

        // Then
        flow.test {
            expectItem().run {
                Truth.assertThat(this).isNotNull()
                Truth.assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                Truth.assertThat(data.artObjects).isNotNull()
                Truth.assertThat(data.artObjects.first().title).isEqualTo("title")
            }
            expectComplete()
        }

        coVerify(exactly = 1) {
            dataSource.fetchCollection(CollectionParams(page = 0, query = null))
        }
        confirmVerified(dataSource)
    }
}