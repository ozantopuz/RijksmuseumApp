package com.ozantopuz.rijksmuseumapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import com.ozantopuz.rijksmuseumapp.domain.usecase.GetCollectionUseCase
import com.ozantopuz.rijksmuseumapp.shared.base.BaseTestClass
import com.ozantopuz.rijksmuseumapp.shared.lifecycle.LifeCycleTestOwner
import com.ozantopuz.rijksmuseumapp.shared.rule.CoroutinesTestRule
import com.ozantopuz.rijksmuseumapp.ui.entity.CollectionViewItem
import com.ozantopuz.rijksmuseumapp.ui.main.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTestClass() {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var useCase: GetCollectionUseCase

    @RelaxedMockK
    private lateinit var addCollectionObserver: Observer<CollectionViewItem>

    @RelaxedMockK
    private lateinit var setCollectionObserver: Observer<CollectionViewItem>

    @RelaxedMockK
    private lateinit var errorObserver: Observer<String>

    private lateinit var mainViewModel: MainViewModel
    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner

    override fun setUp() {
        super.setUp()

        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()

        mainViewModel = MainViewModel(useCase)
        mainViewModel.addListLiveData.observe(lifeCycleTestOwner, addCollectionObserver)
        mainViewModel.setListLiveData.observe(lifeCycleTestOwner, setCollectionObserver)
        mainViewModel.errorLiveData.observe(lifeCycleTestOwner, errorObserver)
    }

    @Test
    fun `collection view item should be filled after data has been fetched`() {
        coEvery {
            useCase.execute(CollectionParams(page = 0, query = ""))
        } coAnswers {
            flowOf(Result.Success(CollectionViewItem(listOf())))
        }

        lifeCycleTestOwner.onResume()

        mainViewModel.searchCollection("")

        val setListValue = mainViewModel.setListLiveData.value
        val errorValue = mainViewModel.errorLiveData.value

        Truth.assertThat(setListValue).isNotNull()
        Truth.assertThat(errorValue).isNull()

        coVerify(atLeast = 1) {
            useCase.execute(CollectionParams(page = 0, query = ""))
        }

        confirmVerified(useCase)
    }

    @Test
    fun `error message should be filled after the exception occurred`() {
        coEvery { useCase.execute(CollectionParams(page = 0, query = "")) } coAnswers {
            flowOf(Result.Error(Exception("Error message!")))
        }

        lifeCycleTestOwner.onResume()

        mainViewModel.searchCollection("")

        val setListValue = mainViewModel.setListLiveData.value
        val errorValue = mainViewModel.errorLiveData.value

        Truth.assertThat(setListValue?.artObjects).isNull()
        Truth.assertThat(errorValue).isNotNull()
        Truth.assertThat(errorValue).isEqualTo("Error message!")

        coVerify(atLeast = 1) {
            useCase.execute(CollectionParams(page = 0, query = ""))
        }

        confirmVerified(useCase)
    }

    @Test
    fun `network process should not happen in case of pause`() {
        coEvery { useCase.execute(CollectionParams(page = 0, query = "")) } returns emptyFlow()

        lifeCycleTestOwner.onResume()

        mainViewModel.searchCollection("")

        val setListValue = mainViewModel.setListLiveData.value
        val errorValue = mainViewModel.errorLiveData.value

        Truth.assertThat(setListValue?.artObjects).isNull()
        Truth.assertThat(errorValue).isNull()

        coVerify(atLeast = 1) {
            useCase.execute(CollectionParams(page = 0, query = ""))
        }

        confirmVerified(useCase)
    }

    override fun tearDown() {
        super.tearDown()
        lifeCycleTestOwner.onDestroy()
        mainViewModel.addListLiveData.removeObserver(addCollectionObserver)
        mainViewModel.addListLiveData.removeObservers(lifeCycleTestOwner)

        mainViewModel.setListLiveData.removeObserver(setCollectionObserver)
        mainViewModel.setListLiveData.removeObservers(lifeCycleTestOwner)

        mainViewModel.errorLiveData.removeObserver(errorObserver)
        mainViewModel.errorLiveData.removeObservers(lifeCycleTestOwner)
    }
}