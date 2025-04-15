package org.example.project

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.project.database.AppDatabase
import org.example.project.database.DATABASE_NAME

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserDatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}