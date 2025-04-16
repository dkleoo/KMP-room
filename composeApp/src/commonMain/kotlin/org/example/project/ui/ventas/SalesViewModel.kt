package org.example.project.ui.ventas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.database.products.Products

class SalesViewModel(private val repository: SalesRepository) : ViewModel() {

    var products by mutableStateOf<List<Products>>(arrayListOf())

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            products = repository.getProducts()
        }
    }

}