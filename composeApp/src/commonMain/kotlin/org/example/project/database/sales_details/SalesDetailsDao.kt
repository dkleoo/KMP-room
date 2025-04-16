package org.example.project.database.sales_details

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface SalesDetailsDao {

    @Insert
    suspend fun saveDetails(salesDetails: SalesDetails)

}