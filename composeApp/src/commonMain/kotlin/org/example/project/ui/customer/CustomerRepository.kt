package org.example.project.ui.customer

import org.example.project.database.customer.CustomerDao
import org.example.project.database.customer.Customers
import org.example.project.database.customer.customerFast

class CustomerRepository(private val customerDao: CustomerDao) {

    suspend fun saveCustomer(): String {
        try {
           val customers = Customers()
            customerDao.saveCustomer(customers.customerFast())
            return customerDao.getCustomer().nameCustomer
        } catch (e:Exception) {
            e.cause
            return ""
        }
    }

}