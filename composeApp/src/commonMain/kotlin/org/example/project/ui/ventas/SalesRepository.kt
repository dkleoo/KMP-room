package org.example.project.ui.ventas

import org.example.project.database.products.Products
import org.example.project.database.products.ProductsDao
import org.example.project.database.sales.Sales
import org.example.project.database.sales.SalesDao
import org.example.project.database.sales_details.SalesDetails
import org.example.project.database.sales_details.SalesDetailsDao

class SalesRepository(private val productsDao: ProductsDao,private val salesDao: SalesDao,private val salesDetailsDao: SalesDetailsDao) {

    suspend fun getProducts(): List<Products> {
        return try {
            productsDao.getProduts()
        } catch (e: Exception) {
            listOf()
        }
    }

    suspend fun saveSales(sales: Sales, lstSalesDetails: ArrayList<SalesDetails>) {
        try {
            val id = salesDao.saveSales(sales)
            lstSalesDetails.forEach {
                it.idSales = id.toInt()
                salesDetailsDao.saveDetails(it)
            }
        } catch (e:Exception) {
            e.cause
        }
    }

}