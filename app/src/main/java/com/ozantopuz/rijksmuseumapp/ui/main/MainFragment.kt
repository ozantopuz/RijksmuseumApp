package com.ozantopuz.rijksmuseumapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ozantopuz.rijksmuseumapp.MainActivity
import com.ozantopuz.rijksmuseumapp.R
import com.ozantopuz.rijksmuseumapp.databinding.FragmentMainBinding
import com.ozantopuz.rijksmuseumapp.util.delegate.viewBinding
import com.ozantopuz.rijksmuseumapp.util.extension.findLastVisibleItemPosition
import com.ozantopuz.rijksmuseumapp.util.extension.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val adapter: ArtObjectAdapter by lazy {
        ArtObjectAdapter { viewItem -> (activity as MainActivity).openDetailFragment(viewItem) }
    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lastVisibleItemPosition = recyclerView.findLastVisibleItemPosition()
            viewModel.paginateCollection(lastVisibleItemPosition)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        with(binding.recyclerView) {
            adapter = this@MainFragment.adapter
            addOnScrollListener(recyclerViewOnScrollListener)
        }

        binding.editText.doAfterTextChanged {
            val query = it.toString()
            viewModel.searchCollection(query)
            binding.progressBar.apply { if (!isVisible) isVisible = true }
        }

        viewModel.fetchCollection()
    }

    private fun observeViewModel() {
        viewModel.addListLiveData.observe(viewLifecycleOwner, { data ->
            binding.progressBar.isVisible = false
            adapter.addList(data.artObjects)
        })
        viewModel.setListLiveData.observe(viewLifecycleOwner, { data ->
            binding.progressBar.isVisible = false
            binding.recyclerView.scrollToPosition(0)
            adapter.setList(data.artObjects)
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, { message ->
            binding.progressBar.isVisible = false
            context.showErrorDialog(message)
        })
    }
}