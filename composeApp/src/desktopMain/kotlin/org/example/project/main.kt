package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.initKoin
import org.example.project.ui.App
import org.koin.core.context.startKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "TiendaGaby2",
    ) {
        App()
    }
}

fun initKoinDesktop() {
    startKoin {
        modules()
    }
}