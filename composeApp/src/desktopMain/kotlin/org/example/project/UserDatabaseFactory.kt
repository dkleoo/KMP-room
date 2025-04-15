package org.example.project

import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.database.AppDatabase
import org.example.project.database.DATABASE_NAME
import java.io.File

actual class UserDatabaseFactory {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "UserDb")
            os.contains("mac") -> File(userHome, "Library/Application Support/UserDb")
            else -> File(userHome, ".local/share/UserDb")
        }

        if (!appDataDir.exists()){
            appDataDir.mkdirs()
        }

        val dbFile = File(appDataDir, DATABASE_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}