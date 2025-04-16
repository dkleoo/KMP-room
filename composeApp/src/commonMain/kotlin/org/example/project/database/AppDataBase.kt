package org.example.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.database.customer.CustomerDao
import org.example.project.database.customer.Customers
import org.example.project.database.products.Products
import org.example.project.database.products.ProductsDao
import org.example.project.database.sales.Sales
import org.example.project.database.sales.SalesDao
import org.example.project.database.sales_details.SalesDetails
import org.example.project.database.sales_details.SalesDetailsDao

const val DATABASE_NAME = "tiendaGaby.db"

@Database(entities = [Customers::class,Products::class,Sales::class,SalesDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun productsDao(): ProductsDao
    abstract fun salesDao(): SalesDao
    abstract fun salesDetailsDao(): SalesDetailsDao
}
