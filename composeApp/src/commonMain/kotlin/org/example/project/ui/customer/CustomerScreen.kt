package org.example.project.ui.customer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.database.sales.SalesReport
import org.example.project.formatPrice
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CustomerScreen() {

    val viewModel: CustomerViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.totSales()
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Column (modifier = Modifier.fillMaxSize()) {
        Row (modifier = Modifier.fillMaxWidth(1f)) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.weight(1f)
                    .clickable {
                        scope.launch {
                            val newIndex = (listState.firstVisibleItemIndex + 1)
                                .coerceAtMost(viewModel.lstSalesReport.lastIndex)
                            listState.animateScrollToItem(newIndex)
                        }
                    }
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.weight(1f)
                    .clickable {
                        scope.launch {
                            val newIndex = (listState.firstVisibleItemIndex - 1).coerceAtLeast(0)
                            listState.animateScrollToItem(newIndex)
                        }
                    }
            )
        }
        LazyColumn (modifier = Modifier.weight(5f), state = listState) {
            items(viewModel.lstSalesReport) { info ->
                var infoSales by remember { mutableStateOf(false) }
                Card (modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Column {
                        Row (modifier = Modifier.padding(8.dp)) {
                            Text(
                                "fecha:  ${info.date}",
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "Total vendido:  ${formatPrice(info.sales.sumOf { it.sales.totalSales })}",
                                modifier = Modifier.weight(2f)
                            )
                            Icon(
                                imageVector = if(!infoSales) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.weight(0.5f)
                                    .clickable {
                                        infoSales = !infoSales
                                    }
                            )
                        }
                        if(infoSales) {
                            info.sales.forEach {
                                Row (modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        "Fecha de venta: ${it.sales.dateSales}",
                                        modifier = Modifier.weight(1f),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        "Valor venta: ${formatPrice(it.sales.totalSales)}",
                                        modifier = Modifier.weight(2f),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                it.details.forEach {
                                    Row (modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            "Producto: ${it.nameProducts}",
                                            modifier = Modifier.weight(2f)
                                        )
                                        Text(
                                            "Valor: ${formatPrice(it.price)}",
                                            modifier = Modifier.weight(2f)
                                        )
                                        Text(
                                            "Cantidad: ${it.quantity}",
                                            modifier = Modifier.weight(2f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}