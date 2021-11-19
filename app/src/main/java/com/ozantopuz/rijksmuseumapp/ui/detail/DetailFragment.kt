package com.ozantopuz.rijksmuseumapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ozantopuz.rijksmuseumapp.R
import com.ozantopuz.rijksmuseumapp.databinding.FragmentDetailBinding
import com.ozantopuz.rijksmuseumapp.ui.entity.ArtObjectViewItem
import com.ozantopuz.rijksmuseumapp.util.delegate.viewBinding
import com.ozantopuz.rijksmuseumapp.util.extension.loadImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var viewItem: ArtObjectViewItem
    private val binding: FragmentDetailBinding by viewBinding()

    companion object {
        fun newInstance(viewItem: ArtObjectViewItem) =
            DetailFragment().apply { this.viewItem = viewItem }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            imageView.loadImage(viewItem.webImageUrl)
            textViewLongTitle.text = viewItem.longTitle
            textViewTitle.text = getString(R.string.label_title, viewItem.title)
            textViewMaker.text = getString(R.string.label_marker, viewItem.principalOrFirstMaker)
            textViewProductionPlaces.text =
                getString(R.string.label_production_places, viewItem.productionPlaces)
        }
    }
}