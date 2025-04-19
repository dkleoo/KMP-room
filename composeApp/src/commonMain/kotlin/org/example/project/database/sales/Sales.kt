package org.example.project.database.sales

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.example.project.database.sales_details.SalesDetails

@Entity
data class Sales(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val totalSales: Double = 0.0,
    val dateSales: String = "",
    val idCustomer: Int = 0,
    val balance: Double = 0.0,
)

data class SalesReport(
    val sales: Sales,
    val details: List<SalesDetails>
)

data class SalesByDate(
    val date: String, // formato: "dd/MM/yyyy"
    val sales: List<SalesReport>
)
