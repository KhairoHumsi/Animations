package com.khairo.youtubeandfacebookanimation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel : ViewModel() {
    private val _cartCount = MutableStateFlow(0)
    val cartCount: StateFlow<Int> = _cartCount

    private val _cart = MutableStateFlow<ArrayList<ItemsAdapter.Data>>(ArrayList())
    val cart: StateFlow<ArrayList<ItemsAdapter.Data>> = _cart

    fun addItem(model: ItemsAdapter.Data, block: suspend (check: Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_cart.value.contains(model)) {
                withContext(Dispatchers.Main) {
                    block(false)
                }
            } else {
                withContext(Dispatchers.Main) {
                    block(true)
                }
                addOne(model)
            }
        }
    }


    private fun addOne(model: ItemsAdapter.Data) {
        _cart.value.add(model)
        _cartCount.value += 1
    }
}