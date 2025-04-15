package org.example.project.ui.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerViewModel(private val repository: CustomerRepository): ViewModel() {

    var nameCustomer by mutableStateOf("")


    fun saveCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            nameCustomer = repository.saveCustomer()
        }
    }

}