package org.example.project.database.customer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customers(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nameCustomer: String = "",
    val lastNameCustomer: String = "",
    val phone: String = "",
    val email: String = "",
    val balance: Double = 0.0
)

fun Customers.customerFast(): Customers {
    return Customers(
        id = 0,
        nameCustomer = "Cliente",
        lastNameCustomer = "Rapido",
        phone = "22222222",
        email = "clienterapido@gmail.com",
        balance = 0.0
    )
}