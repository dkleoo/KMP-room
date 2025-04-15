package org.example.project.database.customer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CustomerDao {

    @Insert
    suspend fun saveCustomer(customers: Customers)

    @Query("SELECT * FROM Customers")
    suspend fun getCustomer(): Customers

}