package com.khairo.youtubeandfacebookanimation.music

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.khairo.youtubeandfacebookanimation.databinding.ItemImageBinding
import com.khairo.youtubeandfacebookanimation.databinding.MusicCellBinding
import com.khairo.youtubeandfacebookanimation.utils.load

class MusicAdapter(
    private val context: Context,
    private val items: List<Data>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MusicAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = MusicCellBinding.inflate(inflater)
        return Holder(binding, itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    inner class Holder(
        val binding: MusicCellBinding,
        private val itemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var data: Data

        init {
            binding.musicCell.setOnClickListener {
                itemClickListener.onItemClick(
                    itemView,
                    layoutPosition,
                    data
                )
            }
        }

        fun bind(data: Data) {
            this.data = data
            binding.apply {
                musicCellImage.load(data.imageRes)
                musicCellTitle.text = context.getString(data.title)
                musicCellDesc.text = context.getString(data.desc)
            }
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