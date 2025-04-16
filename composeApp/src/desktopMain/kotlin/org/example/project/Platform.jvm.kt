package org.example.project

import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.database.AppDatabase
import org.example.project.database.DATABASE_NAME
import java.io.File

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), DATABASE_NAME)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}

// desktopMain
actual fun formatPrice(price: Double): String {
    val formatter = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("en", "US")).apply {
        maximumFractionDigits = if (price % 1 == 0.0) 0 else 2
    }
    return formatter.format(price)
}

