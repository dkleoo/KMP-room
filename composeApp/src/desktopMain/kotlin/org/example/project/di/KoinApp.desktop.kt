package org.example.project.di

import org.example.project.UserDatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { UserDatabaseFactory() }
    }