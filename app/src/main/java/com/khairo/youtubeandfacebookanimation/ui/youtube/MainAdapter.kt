package com.khairo.youtubeandfacebookanimation.ui.youtube

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khairo.youtubeandfacebookanimation.databinding.YoutubeCellBinding
import com.khairo.youtubeandfacebookanimation.models.youtube.YoutubeModel

class MainAdapter(private val items: List<YoutubeModel>, private val viewModel: MainViewModel) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: YoutubeCellBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: YoutubeModel) {
            binding.let {
                it.model = model
//                it.viewModel = viewModel
//                it.viewHolder = this
                it.executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = YoutubeCellBinding.inflate(inflater)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    override fun getItemCount(): Int = items.size
}


//class MainAdapter(private val viewModel: MainViewModel) :
//    BaseAdapter<YoutubeModel, BaseViewHolder<YoutubeModel>, YoutubeCellBinding>(R.layout.youtube_cell) {
//
//    override fun initBinding(view: View) = YoutubeCellBinding.bind(view)
//
//    override fun initViewHolder(layout: Int, view: View): BaseViewHolder<YoutubeModel> =
//        ViewHolder()
//
//    inner class ViewHolder : BaseViewHolder<YoutubeModel>(binding.root) {
//
//        override fun bind(model: YoutubeModel, position: Int) {
//            binding.let {
//                it.model = model
////                it.viewModel = viewModel
////                it.viewHolder = this
//                it.executePendingBindings()
//            }
//        }
//
////        fun check(amount: Int) {
////            selectedPosition = if (selectedPosition == layoutPosition) {
////                viewModel.changeAmount(0)
////                -1
////
////            } else {
////                viewModel.changeAmount(amount)
////                layoutPosition
////            }
////        }
//    }
//}
