package org.example.project.database.sales

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SalesDao {

    @Insert
    suspend fun saveSales(sales: Sales): Long

    @Query("SELECT * FROM Sales")
    suspend fun getSales(): List<Sales>

}