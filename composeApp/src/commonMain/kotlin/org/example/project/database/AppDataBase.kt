package org.example.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.database.customer.CustomerDao
import org.example.project.database.customer.Customers

const val DATABASE_NAME = "tiendaGaby.db"

@Database(entities = [Customers::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
}
