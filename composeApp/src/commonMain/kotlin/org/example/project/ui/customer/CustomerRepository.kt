package org.example.project.ui.customer

import org.example.project.database.customer.CustomerDao
import org.example.project.database.customer.Customers
import org.example.project.database.customer.customerFast

class CustomerRepository(private val customerDao: CustomerDao) {

    suspend fun saveCustomer() {
        try {
            val customersList = getCustomer()
            if (customersList.isEmpty()) {
                val customers = Customers()
                customerDao.saveCustomer(customers.customerFast())
            }
        } catch (e:Exception) {
            e.cause
        }
    }

    suspend fun getCustomer(): List<Customers> {
        return try {
            customerDao.getCustomer()
        } catch (e:Exception) {
            listOf()
        }
    }

}