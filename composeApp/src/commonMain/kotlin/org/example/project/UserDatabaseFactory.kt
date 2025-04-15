package org.example.project

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.database.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect class UserDatabaseFactory {
    fun create(): RoomDatabase.Builder<AppDatabase>
}

