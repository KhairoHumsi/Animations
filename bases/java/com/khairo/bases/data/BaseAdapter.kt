package com.khairo.bases.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.khairo.bases.utils.setAnimation
import java.util.*
import kotlin.collections.HashMap

abstract class BaseAdapter<T : Any, B : BaseViewHolder<T>, VB : ViewDataBinding>(
    @LayoutRes private val viewId: Int
) : PagingDataAdapter<T, B>(COMPARATOR<T>().diffUtil) {

    /**
     * Animation Info
     */
    private var animationEnabled = false
    private var animationType = Animation.RELATIVE_TO_SELF
    private var animationFromX = 0f
    private var animationToX = 1f
    private var animationFromY = 0f
    private var animationToY = 1f


    /**
     * Paging Info
     */
    private var pagingEnabled = true


    /**
     * Adapter Info
     */
    var selectedPosition = -1

    private var _binding: VB? = null
    open val binding get() = _binding!!


    /**
     * Selection Map
     * You need to use it if you want to select mutable items at once
     */
    private val selectionMap = HashMap<Int, T>()

    /** Add item to your selection */
    fun addToSelectionMap(position: Int, model: T) {
        selectionMap[position] = model
    }

    /** Remove the item from your selection */
    fun removeFromSelectionMap(position: Int) {
        selectionMap.remove(position)
    }

    /** Check if the item is selected or not depend on it's [position] */
    fun isSelectionMapItemSelected(position: Int): Boolean = selectionMap[position] != null

    /** Getting the full [selectionMap] */
    fun getSelectionMap(): HashMap<Int, T> = selectionMap

    /** Clear your [selectionMap] */
    fun clearSelectionMap() {
        selectionMap.clear()
    }


    private var items = Collections.emptyList<T>()

    /** Adding the list to your adapter. */
    fun setItems(list: List<T>) {
        if (!isPagingEnabled())
            items = list
    }

    /** Getting the full list. */
    fun getItems(): List<T> = items

    /** Get single item from your list dedent on the position you want. */
    fun getRow(position: Int): T = items[position]

    /**
     * Remove item from your list dedent on the position you want, but you need to use notifyItemRemoved(position)
     * to see the changes in your adapter
     * */
    fun removeRow(position: Int) {
        if (!isPagingEnabled())
            items.removeAt(position)
    }

    /**
     * Add item to your list dedent on the position you want, but you need to use notifyItemInserted(position)
     * to see the changes in your adapter
     * */
    fun addRow(position: Int, model: T) {
        if (!isPagingEnabled())
            items.add(position, model)
    }

    /**
     * Add item to your list, but you need to use notifyItemInserted(items.size)
     * to see the changes in your adapter
     * */
    fun addRow(model: T) {
        if (!isPagingEnabled())
            items.add(model)
    }

    /**
     * Update item in your list, but you need to use notifyItemChanged(position)
     * to see the changes in your adapter
     * */
    fun updateRow(position: Int, model: T) {
        if (!isPagingEnabled())
            items[position] = model
    }

    /**
     * isPagingEnabled setter and getter
     */
    fun isPagingEnabled(): Boolean = pagingEnabled

    fun setPagingEnabled(check: Boolean) {
        pagingEnabled = check
    }

    /**
     * Animation setter and getter
     */
    fun isAnimationEnabled(): Boolean = animationEnabled

    fun setAnimationEnabled(check: Boolean) {
        animationEnabled = check
    }

    /** To set the animation setting */
    fun setAnimationInfo(
        type: Int = Animation.RELATIVE_TO_SELF, fromX: Float = 0f,
        toX: Float = 1f, fromY: Float = 0f, toY: Float = 1f
    ) {
        animationType = type
        animationFromX = fromX
        animationToX = toX
        animationFromY = fromY
        animationToY = toY
    }

    /** This function here to init your [binding] */
    protected abstract fun initBinding(view: View): VB

    protected abstract fun initViewHolder(layout: Int, view: View): B

    private fun inflateView(viewGroup: ViewGroup): View =
        LayoutInflater.from(viewGroup.context).inflate(viewId, viewGroup, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): B {
        _binding = initBinding(view = inflateView(parent))
        return initViewHolder(viewType, inflateView(parent))
    }

    override fun onBindViewHolder(holder: B, position: Int) {
        if (isPagingEnabled()) getItem(position)?.let { holder.bind(it, position) }
        else holder.bind(getRow(position), position)
        if (isAnimationEnabled())
            position.setAnimation(
                holder.itemView,
                selectedPosition,
                animationFromX,
                animationToX,
                animationFromY,
                animationToY,
                animationType
            )
    }
}

class COMPARATOR<T> {
    val diffUtil = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    }
}
