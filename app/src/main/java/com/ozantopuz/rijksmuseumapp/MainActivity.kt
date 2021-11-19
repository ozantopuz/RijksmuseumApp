package com.ozantopuz.rijksmuseumapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozantopuz.rijksmuseumapp.ui.detail.DetailFragment
import com.ozantopuz.rijksmuseumapp.ui.entity.ArtObjectViewItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openDetailFragment(artObjectViewItem: ArtObjectViewItem) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, DetailFragment.newInstance(artObjectViewItem))
            .addToBackStack(null)
            .commit()
    }
}