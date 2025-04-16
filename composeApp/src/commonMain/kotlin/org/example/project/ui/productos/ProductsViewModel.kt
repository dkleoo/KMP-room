package org.example.project.ui.productos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.database.products.Products

class ProductsViewModel(private val repository: ProductsRepository) : ViewModel() {

    var products by mutableStateOf<List<Products>>(listOf())

    fun saveProducts(products: Products,updateProducts: Products?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.saveProducts(products,updateProducts)
            if (response){
                getProducts()
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            products = repository.getProducts()
        }
    }

}