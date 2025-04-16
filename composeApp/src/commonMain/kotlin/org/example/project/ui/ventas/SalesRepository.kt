package org.example.project.ui.ventas

import org.example.project.database.products.Products
import org.example.project.database.products.ProductsDao

class SalesRepository(private val productsDao: ProductsDao) {

    suspend fun getProducts(): List<Products> {
        return try {
            productsDao.getProduts()
        } catch (e: Exception) {
            listOf()
        }
    }

}