package org.example.project

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.database.AppDatabase
import org.example.project.database.DATABASE_NAME
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

lateinit var androidAppContext: Context

fun provideAndroidContext(context: Context) {
    androidAppContext = context.applicationContext
}

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = androidAppContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder(
        androidAppContext,
        AppDatabase::class.java,
        dbFile.absolutePath
    )
}


actual fun formatPrice(price: Double): String {
    val formatter = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("en", "US"))
    return formatter.format(price)
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun getCurrentTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return current.format(formatter)
}