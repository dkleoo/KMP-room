package org.example.project.di

import org.example.project.database.customer.CustomerDao

data class AppDao(
    val customerDao: CustomerDao
)
