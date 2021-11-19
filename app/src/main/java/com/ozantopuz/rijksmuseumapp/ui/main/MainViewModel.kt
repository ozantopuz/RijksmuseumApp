package com.ozantopuz.rijksmuseumapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozantopuz.rijksmuseumapp.data.Result
import com.ozantopuz.rijksmuseumapp.domain.usecase.CollectionParams
import com.ozantopuz.rijksmuseumapp.domain.usecase.GetCollectionUseCase
import com.ozantopuz.rijksmuseumapp.ui.entity.CollectionViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val collectionUseCase: GetCollectionUseCase
) : ViewModel() {

    private var page: Int = 0
    private var artObjectsSize: Int = 0
    private var query: String? = null
    private var isFetchingData: Boolean = false
    private var queryTextChangedJob: Job? = null

    private val _addListLiveData = MutableLiveData<CollectionViewItem>()
    val addListLiveData: LiveData<CollectionViewItem>
        get() = _addListLiveData

    private val _setListLiveData = MutableLiveData<CollectionViewItem>()
    val setListLiveData: LiveData<CollectionViewItem>
        get() = _setListLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    fun fetchCollection() {
        if (isFetchingData) return
        isFetchingData = true

        val params = CollectionParams(page = page, query = query)
        viewModelScope.launch {
            collectionUseCase.execute(params).collect { result ->
                when (result) {
                    is Result.Error ->
                        if (page == 0) _errorLiveData.value = result.exception.localizedMessage
                    is Result.Success -> result.data.let {
                        artObjectsSize += it.artObjects.size
                        if (page == 0) _setListLiveData.value = it else _addListLiveData.value = it
                        if (it.artObjects.isNotEmpty()) page ++
                        isFetchingData = false
                    }
                }
            }
        }
    }

    fun paginateCollection(lastVisibleItemPosition: Int) {
        if (lastVisibleItemPosition < artObjectsSize - PAGINATION_THRESHOLD) return
        fetchCollection()
    }

    fun searchCollection(query: String){
        queryTextChangedJob?.cancel()
        queryTextChangedJob = viewModelScope.launch(Dispatchers.Main) {
            delay(SEARCH_DELAY)
            this@MainViewModel.query = query
            clearData()
            fetchCollection()
        }
    }

    private fun clearData() {
        page = 0
        artObjectsSize = 0
    }

    companion object {
        private const val SEARCH_DELAY = 1_000L
        private const val PAGINATION_THRESHOLD = 20
    }
}