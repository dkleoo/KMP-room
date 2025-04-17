package org.example.project.ui.customer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import org.example.project.database.customer.Customers
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CustomerScreen() {

    val viewModel: CustomerViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.getCustomer()
    }

    var search by remember { mutableStateOf("") }
    val updateCustomer by remember { mutableStateOf<Customers?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            Modifier
                .weight(2f)
                .fillMaxHeight(),
        ) {
            Column {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    label = { Text("Buscar cliente") },
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                )
                LazyColumn  {
                    items(viewModel.lstCustomer) { customer ->
                        Card (modifier = Modifier.padding(16.dp), shape = RoundedCornerShape(8.dp)) {
                            Row (modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Cliente: ${customer.nameCustomer}",
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "Telefono: ${customer.phone}",
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    "Saldo pendiente: ${customer.balance}",
                                    modifier = Modifier.weight(1f)
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
                Column {
                    var nameCustomer by remember { mutableStateOf("") }
                    var phone by remember { mutableStateOf("") }
                    var balance by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = nameCustomer,
                        onValueChange = { nameCustomer = it },
                        label = { Text("Nombre del cliente") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Telefono") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = balance,
                        onValueChange = { balance = it },
                        label = { Text("Saldo pendiente") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                    Button(
                        onClick = {

                        },
                        content = {
                            Text(if(updateCustomer == null) "Guardar" else "Actualizar")
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