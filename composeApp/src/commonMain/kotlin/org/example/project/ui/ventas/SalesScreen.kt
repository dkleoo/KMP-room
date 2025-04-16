package org.example.project.ui.ventas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.database.products.Products
import org.example.project.formatPrice
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tiendagaby2.composeapp.generated.resources.Res
import tiendagaby2.composeapp.generated.resources.ic_placeholder

@Composable
fun SalesScreen() {

    val viewModel: SalesViewModel = koinViewModel()
    var addProducts by remember { mutableStateOf<Products?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .weight(2f)
                .fillMaxHeight(),
        ) {
            var search by remember { mutableStateOf("") }
            Column {
                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    label = { Text("Buscar productos") }
                )
                LazyVerticalGrid(
                    GridCells.Fixed(4),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(viewModel.products.filter {
                        it.nameProduct.lowercase().contains(search.lowercase()) ||
                                it.barcode.contains(search)
                    }) { products ->
                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(8.dp),
                            elevation = 8.dp,
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Column (
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                                    .clickable {
                                        addProducts = products
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.ic_placeholder),
                                    contentDescription = "search-products",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.weight(2f)
                                )
                                Text(
                                    products.nameProduct,
                                    modifier = Modifier.weight(0.5f),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    formatPrice(products.price),
                                    modifier = Modifier.weight(0.5f),
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .weight(1.5f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text("Zona Azul", color = Color.White)
        }
    }
}
