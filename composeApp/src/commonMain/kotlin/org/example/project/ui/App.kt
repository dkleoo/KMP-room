package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.example.project.database.DATABASE_NAME
import org.example.project.ui.customer.CustomerScreen
import org.example.project.ui.customer.CustomerViewModel
import org.example.project.ui.productos.ProductsScreen
import org.example.project.ui.ventas.SalesScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import tiendagaby2.composeapp.generated.resources.Res
import tiendagaby2.composeapp.generated.resources.ic_clientes
import tiendagaby2.composeapp.generated.resources.ic_productos
import tiendagaby2.composeapp.generated.resources.ic_ventas
import java.io.File

@Composable
@Preview
fun App() {
    MaterialTheme {
        SideDrawerWithIconsAndText()
    }
}

@Composable
fun SideDrawerWithIconsAndText() {
    var isExpanded by remember { mutableStateOf(true) }
    var selectIcon by remember { mutableStateOf<DrawerItem?>(null) }

    val viewModel: CustomerViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.saveCustomer()
    }

    val drawerItems = listOf(
        DrawerItem("Ventas", painterResource(Res.drawable.ic_ventas)),
        DrawerItem("Productos", painterResource(Res.drawable.ic_productos)),
        DrawerItem("Reportes", painterResource(Res.drawable.ic_clientes)),
    )

    Row(Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(if (isExpanded) 150.dp else 50.dp),
        ) {
            Column {
                IconButton(onClick = {
                    isExpanded = !isExpanded
                }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.Close else Icons.Default.Menu,
                        contentDescription = "Toggle Drawer"
                    )
                }

                drawerItems.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isExpanded = false
                                selectIcon = item
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                        if (isExpanded) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = item.title)
                        }
                    }
                }
            }
        }

        // Contenido principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            when (selectIcon?.title) {
                "Ventas" -> SalesScreen()
                "Productos" -> ProductsScreen()
                "Reportes" -> CustomerScreen()
                else -> SalesScreen()
            }
        }
    }
}

fun deleteDatabase(): Boolean {
    val os = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")
    val appDataDir = when {
        os.contains("win") -> File(System.getenv("APPDATA"), "UserDb")
        os.contains("mac") -> File(userHome, "Library/Application Support/UserDb")
        else -> File(userHome, ".local/share/UserDb")
    }

    val dbFile = File(appDataDir, DATABASE_NAME)
    return if (dbFile.exists()) {
        dbFile.delete().also { success ->
            if (success) println("Base de datos eliminada correctamente")
            else println("No se pudo eliminar la base de datos")
        }
    } else {
        println("La base de datos no existe")
        false
    }
}


data class DrawerItem(val title: String, val icon: Painter)

