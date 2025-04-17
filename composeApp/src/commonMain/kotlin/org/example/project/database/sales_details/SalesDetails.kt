package org.example.project.database.sales_details

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.database.products.ProductsDao

@Entity
data class SalesDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var idSales: Int = 0,
    val idProduct: Int = 0,
    val price: Double = 0.0,
    var quantity: Int = 0,
    val nameProducts: String = ""
)