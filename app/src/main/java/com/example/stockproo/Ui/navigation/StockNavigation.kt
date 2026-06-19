package com.example.stockproo.Ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockpro.ui.screens.*
import com.example.stockpro.viewmodel.StockViewModel

@Composable
fun StockNavigation() {
    val navController = rememberNavController()
    val stockViewModel: StockViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {

        // Pantalla 1: Ingreso de Operario
        composable("login") {
            LoginScreen(
                onNavigateToCatalogo = { nombre ->
                    navController.navigate("catalogo/$nombre")
                }
            )
        }

        // Pantalla 2: Catálogo de Inventario
        composable(
            route = "catalogo/{operario}",
            arguments = listOf(navArgument("operario") { type = NavType.StringType })
        ) { backStackEntry ->
            val operario = backStackEntry.arguments?.getString("operario") ?: "Desconocido"
            CatalogoScreen(
                operario = operario,
                viewModel = stockViewModel,
                onNavigateToEdicion = { id ->
                    navController.navigate("edicion/$id")
                },
                onNavigateToReporte = {
                    navController.navigate("reporte")
                }
            )
        }
