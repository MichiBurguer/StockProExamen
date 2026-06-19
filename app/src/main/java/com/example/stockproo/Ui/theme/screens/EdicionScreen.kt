package com.example.stockproo.Ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stockpro.viewmodel.StockViewModel

@Composable
fun EdicionScreen(
    productoId: Int,
    viewModel: StockViewModel,
    onBack: () -> Unit
) {
    val producto = viewModel.obtenerProducto(productoId)

    if (producto == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Producto no encontrado")
        }
        return
    }

    // Estado local temporal para interactuar antes de guardar permanentemente
    var stockTemporal by remember { mutableStateOf(producto.stockActual) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = producto.nombre, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = producto.descripcion, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(48.dp))

            // Selector de cantidad en texto grande
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Stock Actual", style = MaterialTheme.typography.labelLarge)
                Text(text = "$stockTemporal", style = MaterialTheme.typography.displayLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Botón Restar con deshabilitación controlada
                    Button(
                        onClick = { if (stockTemporal > 0) stockTemporal-- },
                        enabled = stockTemporal > 0
                    ) {
                        Text("-1", style = MaterialTheme.typography.titleLarge)
                    }

                    Button(onClick = { stockTemporal++ }) {
                        Text("+1", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }

        // Persistencia y popBackStack
        Button(
            onClick = {
                viewModel.actualizarStock(productoId, stockTemporal)
                onBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar y Volver")
        }
    }
}