package org.example.project.ui.customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.database.customer.Customers
import org.example.project.database.sales.SalesByDate
import org.example.project.database.sales.SalesReport

class CustomerViewModel(private val repository: CustomerRepository): ViewModel() {

    var nameCustomer by mutableStateOf("")
    var lstSalesReport by mutableStateOf<List<SalesByDate>>(arrayListOf())


    fun saveCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveCustomer()
        }
    }

    fun totSales() {
        viewModelScope.launch (Dispatchers.IO) {
            lstSalesReport = repository.totSalesGroupedByDate()
        }
    }

}