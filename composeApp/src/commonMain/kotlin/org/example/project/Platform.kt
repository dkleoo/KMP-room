package org.example.project

import androidx.room.RoomDatabase
import org.example.project.database.AppDatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getDatabaseBuilder() : RoomDatabase.Builder<AppDatabase>


