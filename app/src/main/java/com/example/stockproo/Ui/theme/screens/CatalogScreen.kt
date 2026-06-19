package com.example.stockproo.Ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockpro.model.Producto
import com.example.stockpro.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    operario: String,
    viewModel: StockViewModel,
    onNavigateToEdicion: (Int) -> Unit,
    onNavigateToReporte: () -> Unit
) {
    var filtroCritico by remember { mutableStateOf(false) }

    val productosMostrar = if (filtroCritico) {
        viewModel.listaProductos.filter { it.stockActual < 5 }
    } else {
        viewModel.listaProductos
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToReporte,
                icon = { Icon(Icons.Default.Info, contentDescription = "Reporte") },
                text = { Text("Ver Reporte") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // UI Superior indicando operario
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Operario: $operario",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Filtros de visualización
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { filtroCritico = false },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!filtroCritico) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text("Ver Todo")
                }
                Button(
                    onClick = { filtroCritico = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filtroCritico) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text("Stock Crítico")
                }
            }

            // Listado
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productosMostrar) { producto ->
                    ProductoCard(producto = producto, onClick = { onNavigateToEdicion(producto.id) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoCard(producto: Producto, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = producto.nombre, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Precio: \$${String.format("%.2f", producto.precio)}")

                // Validación de color si stock < 5
                val colorStock = if (producto.stockActual < 5) Color.Red else Color.Unspecified
                Text(
                    text = "Stock: ${producto.stockActual}",
                    color = colorStock,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}