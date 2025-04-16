package org.example.project.ui.productos


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.database.products.Products
import org.example.project.formatPrice
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tiendagaby2.composeapp.generated.resources.Res
import tiendagaby2.composeapp.generated.resources.ic_placeholder


@Composable
fun ProductsScreen() {

    val viewModel: ProductsViewModel = koinViewModel()
    var updateProducts by remember { mutableStateOf<Products?>(null) }

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
                        updateProducts = null
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
                                        updateProducts = products
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

            Card (
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column (modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
                    var nameProducts by remember { mutableStateOf("") }
                    var descriptionProducts by remember { mutableStateOf("") }
                    var barcodeProducts by remember { mutableStateOf("") }
                    var costProducts by remember { mutableStateOf("") }
                    var priceProducts by remember { mutableStateOf("") }
                    var earings by remember { mutableStateOf("") }
                    var value by remember { mutableStateOf("") }

                    LaunchedEffect(updateProducts) {
                        updateProducts?.let {
                            nameProducts = it.nameProduct
                            descriptionProducts = it.descriptionProduct
                            barcodeProducts = it.barcode
                            costProducts = it.cost.toString()
                            priceProducts = it.price.toString()
                            earings = it.earnings.toString()
                            value = (priceProducts.toDouble() - costProducts.toDouble()).toString()
                        } ?: kotlin.run {
                            nameProducts = ""
                            descriptionProducts = ""
                            barcodeProducts = ""
                            costProducts = ""
                            priceProducts = ""
                            earings = ""
                            value = ""
                        }
                    }


                    OutlinedTextField(
                        value = nameProducts,
                        onValueChange = { nameProducts = it },
                        label = { Text("Nombre del producto") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = descriptionProducts,
                        onValueChange = { descriptionProducts = it },
                        label = { Text("Descripcion del producto") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = barcodeProducts,
                        onValueChange = { barcodeProducts = it },
                        label = { Text("Codigo de barras") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = costProducts,
                        onValueChange = {
                            costProducts = it
                            if(it.isNotEmpty() && priceProducts.isNotEmpty()) {
                                earings = calcularPorcentajeGanancia(costProducts.toDouble(),priceProducts.toDouble()).toString()
                                value = (priceProducts.toDouble() - costProducts.toDouble()).toString()
                            }
                        },
                        label = { Text("Precio de compra") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = priceProducts,
                        onValueChange = {
                            priceProducts = it
                            if(it.isNotEmpty() && priceProducts.isNotEmpty()) {
                                earings = calcularPorcentajeGanancia(costProducts.toDouble(),priceProducts.toDouble()).toString()
                                value = (priceProducts.toDouble() - costProducts.toDouble()).toString()
                            }
                        },
                        label = { Text("Precio de venta") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    Row (modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text("Ganancia", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("$earings%", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text("Valor:", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(value, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    }
                    Button(
                        onClick = {
                            viewModel.saveProducts(
                                Products(
                                    nameProduct = nameProducts,
                                    descriptionProduct = descriptionProducts,
                                    barcode = barcodeProducts,
                                    cost = costProducts.toDouble(),
                                    price = priceProducts.toDouble(),
                                    earnings = earings.toDouble()
                                ),
                                updateProducts
                            )
                            updateProducts = null
                        },
                        content = {
                            Text(if(updateProducts == null) "Guardar" else "Actualizar")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }

        }
    }
}

fun calcularPorcentajeGanancia(precioCosto: Double, precioVenta: Double): Double {
    return ((precioVenta - precioCosto) / precioCosto) * 100
}
