package org.example.project.ui.customer

import org.example.project.database.customer.CustomerDao
import org.example.project.database.customer.Customers
import org.example.project.database.customer.customerFast
import org.example.project.database.sales.Sales
import org.example.project.database.sales.SalesByDate
import org.example.project.database.sales.SalesDao
import org.example.project.database.sales.SalesReport
import org.example.project.database.sales_details.SalesDetailsDao
import java.text.SimpleDateFormat
import java.util.Locale

class CustomerRepository(private val customerDao: CustomerDao,private val salesDao: SalesDao,private val detailsDao: SalesDetailsDao) {

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

    suspend fun totSalesGroupedByDate(): List<SalesByDate> {
        return try {
            val salesReport: ArrayList<SalesReport> = arrayListOf()
            val sales = salesDao.getSales()

            sales.forEach {
                salesReport.add(SalesReport(it, detailsDao.getDetail(it.id)))
            }

            // Agrupar por fecha sin la hora
            salesReport.groupBy { report ->
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                val dateOnlyFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = formatter.parse(report.sales.dateSales)
                dateOnlyFormat.format(date!!)
            }.map { (date, salesList) ->
                SalesByDate(date, salesList)
            }.sortedByDescending { it.date } // Opcional: para ver los días más recientes primero

        } catch (e: Exception) {
            emptyList()
        }
    }


}