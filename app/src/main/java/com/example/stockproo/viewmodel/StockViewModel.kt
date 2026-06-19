package com.example.stockproo.viewmodel

class StockViewModel {
}package com.example.stockproo.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.stockpro.model.Producto

class StockViewModel : ViewModel() {

    // Lista reactiva con 6 productos iniciales precargados
    val listaProductos = mutableStateListOf(
        Producto(1, "Martillo Pro", "Martillo de acero templado de 16oz", 12.50, 10),
        Producto(2, "Destornillador Phillips", "Destornillador imantado estrella", 4.20, 3),
        Producto(3, "Taladro Inalámbrico", "Taladro percutor de 20V con 2 baterías", 89.99, 6),
        Producto(4, "Cinta Métrica 5m", "Cinta de alta resistencia con seguro", 5.50, 2),
        Producto(5, "Juego de Llaves", "Set de 12 llaves fijas combinadas", 24.00, 12),
        Producto(6, "Organizador de Tornillos", "Caja plástica con 20 compartimentos", 8.00, 0)
    )

    fun obtenerProducto(id: Int): Producto? {
        return listaProductos.find { it.id == id }
    }

    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val index = listaProductos.indexOfFirst { it.id == id }
        if (index != -1 && nuevaCantidad >= 0) {
            // Actualizamos creando una copia o modificando el estado reactivo
            listaProductos[index] = listaProductos[index].copy(stockActual = nuevaCantidad)
        }
    }

    // Lógica financiera calculada en el ViewModel
    fun calcularValorTotalInventario(): Double {
        return listaProductos.sumOf { it.precio * it.stockActual }
    }

    fun obtenerProductosEnCero(): Int {
        return listaProductos.count { it.stockActual == 0 }
    }
}