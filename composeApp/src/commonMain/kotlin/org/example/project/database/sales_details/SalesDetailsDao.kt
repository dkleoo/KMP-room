package org.example.project.database.sales_details

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SalesDetailsDao {

    @Insert
    suspend fun saveDetails(salesDetails: SalesDetails)

    @Query("Select * from SalesDetails where idSales = :idSales")
    suspend fun getDetail(idSales: Int): List<SalesDetails>

    @Query("Select * from SalesDetails")
    suspend fun getDetails(): List<SalesDetails>

}