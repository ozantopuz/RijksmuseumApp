package com.ozantopuz.rijksmuseumapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozantopuz.rijksmuseumapp.databinding.ItemArtObjectBinding
import com.ozantopuz.rijksmuseumapp.ui.entity.ArtObjectViewItem
import com.ozantopuz.rijksmuseumapp.util.extension.loadImage

class ArtObjectAdapter(
    private var list: ArrayList<ArtObjectViewItem> = arrayListOf(),
    private var block: (ArtObjectViewItem) -> Unit
) : RecyclerView.Adapter<ArtObjectAdapter.ArtObjectViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtObjectViewHolder {
        val itemBinding =
            ItemArtObjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArtObjectViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        val item: ArtObjectViewItem = list[position]
        with(holder.binding) {
            textView.text = item.title
            imageView.loadImage(item.webImageUrl)
        }

        holder.itemView.setOnClickListener { block.invoke(item) }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<ArtObjectViewItem>? = null) {
        if (list != null) this.list.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ArtObjectViewItem>? = null) {
        if (list != null) this.list = ArrayList(list)
        notifyDataSetChanged()
    }

    class ArtObjectViewHolder(val binding: ItemArtObjectBinding) :
        RecyclerView.ViewHolder(binding.root)
}