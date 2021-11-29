package com.khairo.youtubeandfacebookanimation.marvel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.khairo.youtubeandfacebookanimation.databinding.ItemImageBinding
import com.khairo.youtubeandfacebookanimation.utils.load

class CharacterAdapter(
    private val items: List<Data>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CharacterAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemImageBinding.inflate(inflater)
        return Holder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    class Holder(
        val binding: ItemImageBinding,
        private val itemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var data: Data

        init {
            binding.img.setOnClickListener {
                itemClickListener.onItemClick(
                    itemView,
                    layoutPosition,
                    data
                )
            }
        }

        fun bind(data: Data) {
            this.data = data
            binding.img.load(data.imageRes)
        }
    }

    data class Data(
        @DrawableRes val imageRes: Int,
        @DrawableRes val headerRes: Int,
        @StringRes val title: Int,
        @StringRes val desc: Int
    )

    interface OnItemClickListener {
        fun onItemClick(
            view: View,
            position: Int,
            data: Data
        )
    }
}