package org.example.project.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.UserDatabaseFactory
import org.example.project.database.AppDatabase
import org.example.project.database.customer.CustomerDao
import org.example.project.ui.customer.CustomerRepository
import org.example.project.ui.customer.CustomerViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.core.module.Module


expect val platformModule: Module

//fun getKoinModules() = listOf(moduleDatabase, moduleApp,platformModule)

val appDao: Module = module {
    single <AppDao> { AppDao(customerDao = get()) }
}

val dataBaseModule : Module = module {
    single {
        get<UserDatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<AppDatabase>().customerDao() }
}

val moduleApp = module {
    factoryOf(::CustomerRepository)
    viewModelOf(::CustomerViewModel)
}


