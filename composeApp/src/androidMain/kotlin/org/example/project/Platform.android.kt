package org.example.project

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.database.AppDatabase
import org.example.project.database.DATABASE_NAME

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


