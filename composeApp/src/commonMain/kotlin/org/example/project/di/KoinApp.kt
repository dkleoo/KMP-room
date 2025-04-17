package org.example.project.di


import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.UserDatabaseFactory
import org.example.project.database.AppDatabase
import org.example.project.ui.customer.CustomerRepository
import org.example.project.ui.productos.ProductsRepository
import org.example.project.ui.productos.ProductsViewModel
import org.example.project.ui.customer.CustomerViewModel
import org.example.project.ui.ventas.SalesViewModel
import org.example.project.ui.ventas.SalesRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.core.module.Module


expect val platformModule: Module


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
    single { get<AppDatabase>().productsDao() }
    single { get<AppDatabase>().salesDao() }
    single { get<AppDatabase>().salesDetailsDao() }
}

val moduleApp = module {
    factoryOf(::CustomerRepository)
    viewModelOf(::CustomerViewModel)
    factoryOf(::ProductsRepository)
    viewModelOf(::ProductsViewModel)
    factoryOf(::SalesRepository)
    viewModelOf(::SalesViewModel)
}


