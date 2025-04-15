package org.example.project

import android.app.Application
import android.content.Context
import org.example.project.di.getKoinModules
import org.example.project.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AppAplication)
            androidLogger()
        }
    }
}


fun initKoinAndroid(context: Context) {
    startKoin {
        provideAndroidContext(context)
        modules(getKoinModules())
    }
}