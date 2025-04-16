package org.example.project.ui.productos

import org.example.project.database.products.Products
import org.example.project.database.products.ProductsDao

class ProductsRepository(private val productsDao: ProductsDao) {

    suspend fun saveProducts(products: Products,updateProducts: Products?): Boolean {
        try {
            updateProducts?.let {
                products.id = updateProducts.id
                productsDao.updateProducts(products)
            } ?: kotlin.run {
                productsDao.saveProduct(products)
            }
            return true
        } catch (e:Exception) {
            e.cause
            return false
        }
    }

    suspend fun getProducts(): List<Products> {
        return try {
            productsDao.getProduts()
        } catch (e:Exception) {
            e.cause
            listOf()
        }
    }

}