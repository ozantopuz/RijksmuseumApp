package com.ozantopuz.rijksmuseumapp.domain.usecase

import com.google.common.truth.Truth
import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.domain.entity.Collection
import com.ozantopuz.rijksmuseumapp.domain.mapper.CollectionViewItemMapper
import com.ozantopuz.rijksmuseumapp.domain.repository.CollectionRepository
import com.ozantopuz.rijksmuseumapp.shared.assertion.test
import com.ozantopuz.rijksmuseumapp.shared.base.BaseTestClass
import com.ozantopuz.rijksmuseumapp.shared.extension.runBlockingTest
import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import com.ozantopuz.rijksmuseumapp.ui.entity.CollectionViewItem
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
class GetCollectionUseCaseTest : BaseTestClass() {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var repository: CollectionRepository

    @MockK
    private lateinit var mapper: CollectionViewItemMapper

    @RelaxedMockK
    private lateinit var collection: Collection

    @RelaxedMockK
    lateinit var collectionViewItem: CollectionViewItem

    private lateinit var useCase: GetCollectionUseCase

    override fun setUp() {
        super.setUp()

        coEvery { repository.fetchCollection(CollectionParams(page = 0, query = "")) } coAnswers {
            flowOf(Result.Success(collection))
        }

        coEvery { mapper.map(any()) } returns CollectionViewItem(listOf())

        every { collectionViewItem.artObjects } returns listOf()

        useCase = GetCollectionUseCase(repository, mapper)
    }

    @Test
    fun `use case should return data properly`() = coroutinesTestRule.runBlockingTest {
        // Given
        val collectionParams = CollectionParams(page = 0, query = "")

        // Then
        val flow = useCase.execute(collectionParams)

        // Then
        flow.test {
            expectItem().run {
                Truth.assertThat(this).isNotNull()
                Truth.assertThat(this).isInstanceOf(Result.Success::class.java)
                this as Result.Success
                Truth.assertThat(data.artObjects).isNotNull()
            }
            expectComplete()
        }

        coVerify(exactly = 1) { repository.fetchCollection(CollectionParams(page = 0, query = "")) }
        confirmVerified(repository)
    }
}