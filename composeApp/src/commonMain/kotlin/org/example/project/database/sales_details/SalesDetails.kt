package org.example.project.database.sales_details

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SalesDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idProduct: Int = 0,
    val price: Double = 0.0,
    val quantity: Int = 0,
)