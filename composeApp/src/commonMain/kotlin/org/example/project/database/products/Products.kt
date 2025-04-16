package org.example.project.database.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Products(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val nameProduct: String = "",
    val descriptionProduct: String = "",
    val barcode: String = "",
    val cost: Double,
    val price: Double,
    val earnings: Double
)