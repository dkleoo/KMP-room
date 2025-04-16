package org.example.project.database.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductsDao {

    @Insert
    suspend fun saveProduct(products: Products)

    @Query("SELECT * FROM products")
    suspend fun getProduts(): List<Products>

    @Update
    suspend fun updateProducts(products: Products)

}