package org.example.project.database.sales

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface SalesDao {

    @Insert
    suspend fun saveSales(sales: Sales)

}