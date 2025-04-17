package org.example.project.ui.ventas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.example.project.database.sales.Sales
import org.example.project.database.sales_details.SalesDetails
import org.example.project.formatPrice
import org.example.project.getCurrentTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tiendagaby2.composeapp.generated.resources.Res
import tiendagaby2.composeapp.generated.resources.ic_close
import tiendagaby2.composeapp.generated.resources.ic_placeholder
import tiendagaby2.composeapp.generated.resources.mas
import tiendagaby2.composeapp.generated.resources.menos

@Composable
fun SalesScreen() {

    val viewModel: SalesViewModel = koinViewModel()
    var lstSalesDetails by remember { mutableStateOf<ArrayList<SalesDetails>>(arrayListOf()) }
    var valueReceived by remember { mutableStateOf(0.0) }
    var valueReceivedAdd by remember { mutableStateOf("") }
    val prices by remember { mutableStateOf(arrayOf(2000.0,5000.0,10000.0,20000.0,50000.0)) }

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }
    val focusRequester = remember { FocusRequester() }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                focusRequester.requestFocus()
            }
        }
        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .weight(2f)
                .fillMaxHeight(),
        ) {

            var search by remember { mutableStateOf("") }
            val keyboardController = LocalSoftwareKeyboardController.current
            var searchFast by remember { mutableStateOf(false) }

            Column {
                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    modifier = Modifier.padding(16.dp).fillMaxWidth().focusRequester(focusRequester),
                    label = { Text("Buscar productos") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search // o ImeAction.Done, según el caso
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            searchFast = true
                            keyboardController?.hide()
                        }
                    ),
                )
                if(searchFast) {
                    viewModel.products.find { it.nameProduct.lowercase().contains(search.lowercase()) || it.barcode.contains(search) }?.let { updateProduct ->
                        val lstSalesDetailsTemp : ArrayList<SalesDetails> = arrayListOf()
                        lstSalesDetailsTemp.addAll(lstSalesDetails)
                        lstSalesDetailsTemp.find { it.idProduct == updateProduct.id }?.let {
                            it.quantity++
                        } ?: kotlin.run {
                            lstSalesDetailsTemp.add(SalesDetails(idProduct = updateProduct.id, price = updateProduct.price, quantity = 1, nameProducts = updateProduct.nameProduct))
                        }
                        lstSalesDetails = arrayListOf()
                        lstSalesDetails = lstSalesDetailsTemp
                    }
                    search = ""
                    searchFast = false
                }

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
                                        val lstSalesDetailsTemp : ArrayList<SalesDetails> = arrayListOf()
                                        lstSalesDetailsTemp.addAll(lstSalesDetails)
                                        lstSalesDetailsTemp.find { it.idProduct == products.id }?.let {
                                            it.quantity++
                                        } ?: kotlin.run {
                                            lstSalesDetailsTemp.add(SalesDetails(idProduct = products.id, price = products.price, quantity = 1, nameProducts = products.nameProduct))
                                        }
                                        lstSalesDetails = arrayListOf()
                                        lstSalesDetails = lstSalesDetailsTemp
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
                                    modifier = Modifier.weight(0.9f),
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
                modifier = Modifier.padding(16.dp).fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                elevation = 0.dp
            ) {
                Column (modifier = Modifier.padding(horizontal = 16.dp)) {
                    LazyColumn (modifier = Modifier.weight(2f)) {
                        items(lstSalesDetails) { details ->
                            Card (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp),
                                elevation = 8.dp
                            ) {
                                Row (
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row (modifier = Modifier.weight(1f)) {
                                        Image(
                                            painter = painterResource(Res.drawable.ic_close),
                                            contentDescription = null,
                                            modifier = Modifier.weight(0.1f)
                                                .clickable {
                                                    val lstSalesDetailsTemp : ArrayList<SalesDetails> = arrayListOf()
                                                    lstSalesDetailsTemp.addAll(lstSalesDetails)
                                                    lstSalesDetailsTemp.find { it.idProduct == details.idProduct }?.let {
                                                        lstSalesDetailsTemp.remove(it)
                                                    }
                                                    lstSalesDetails = arrayListOf()
                                                    lstSalesDetails = lstSalesDetailsTemp
                                                },
                                            alignment = Alignment.TopCenter
                                        )
                                        Image(
                                            painter = painterResource(Res.drawable.ic_placeholder),
                                            contentDescription = null,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    Column (modifier = Modifier.weight(2f)) {
                                        Text(
                                            details.nameProducts,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1.5f).fillMaxWidth()
                                        )
                                        Text(
                                            formatPrice(details.price),
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1.5f).fillMaxWidth()
                                        )
                                        Row (
                                            modifier = Modifier.weight(2f),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(Res.drawable.menos),
                                                contentDescription = null,
                                                modifier = Modifier.weight(2f)
                                                    .clickable {
                                                        val lstSalesDetailsTemp : ArrayList<SalesDetails> = arrayListOf()
                                                        lstSalesDetailsTemp.addAll(lstSalesDetails)
                                                        lstSalesDetailsTemp.find { it.idProduct == details.idProduct }?.let {
                                                            it.quantity--
                                                        }
                                                        lstSalesDetails = arrayListOf()
                                                        lstSalesDetails = lstSalesDetailsTemp
                                                    }
                                            )
                                            Text(
                                                details.quantity.toString(),
                                                modifier = Modifier.weight(1f),
                                                textAlign = TextAlign.Center,
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Image(
                                                painter = painterResource(Res.drawable.mas),
                                                contentDescription = null,
                                                modifier = Modifier.weight(2f)
                                                    .clickable {
                                                        val lstSalesDetailsTemp : ArrayList<SalesDetails> = arrayListOf()
                                                        lstSalesDetailsTemp.addAll(lstSalesDetails)
                                                        lstSalesDetailsTemp.find { it.idProduct == details.idProduct }?.let {
                                                            it.quantity++
                                                        }
                                                        lstSalesDetails = arrayListOf()
                                                        lstSalesDetails = lstSalesDetailsTemp
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Divider()
                    Column (modifier = Modifier.weight(0.6f)) {
                        LazyRow (modifier = Modifier.weight(2.5f)) {
                            items(prices) { prices ->
                                Card (
                                    backgroundColor = Color(0xFF008000),
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.padding(8.dp)
                                        .clickable {
                                            valueReceived += prices
                                            focusRequester.requestFocus()
                                        }
                                ) {
                                    Text(
                                        formatPrice(prices),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                        OutlinedTextField(
                            value = valueReceivedAdd,
                            onValueChange = { valueReceivedAdd = it },
                            label = { Text("Valor") },
                            trailingIcon = {
                                Image(
                                    painter = painterResource(Res.drawable.mas),
                                    contentDescription = null,
                                    modifier = Modifier.size(35.dp).clickable {
                                        valueReceived += valueReceivedAdd.toDouble()
                                        valueReceivedAdd = ""
                                        focusRequester.requestFocus()
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth().weight(2.5f)
                        )
                        Row (modifier = Modifier.weight(3f)) {
                            Column (modifier = Modifier.weight(1f)) {
                                Text(
                                    "Valor recibido: ${formatPrice(valueReceived)}",
                                    modifier = Modifier.padding(top = 8.dp).weight(1f),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    "Total de venta: ${formatPrice(lstSalesDetails.sumOf { it.price * it.quantity })}",
                                    modifier = Modifier.padding(top = 8.dp).weight(1f),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Cambio: ${formatPrice(if(valueReceived != 0.0 && lstSalesDetails.isNotEmpty()) valueReceived - lstSalesDetails.sumOf { it.price * it.quantity } else 0.0)}",
                                    modifier = Modifier.padding(top = 8.dp).weight(1f),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Button(
                                onClick = {
                                    val sales = Sales(totalSales = lstSalesDetails.sumOf { it.price * it.quantity }, dateSales = getCurrentTime())
                                    viewModel.saveSales(sales,lstSalesDetails)
                                    lstSalesDetails = arrayListOf()
                                    valueReceived = 0.0
                                    valueReceivedAdd = ""
                                    focusRequester.requestFocus()
                                },
                                Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(backgroundColor =  Color(0xFF008000))
                            ) {
                                Text("Guardar venta")
                            }
                        }
                    }
                }
            }
        }
    }
}



