package com.duoc.teqgastos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.duoc.teqgastos.ui.screen.*
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: GastoViewModel) {
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onNavegarRegistro = { navController.navigate("registro") },
                onNavegarTeqRuta = { navController.navigate("teqruta") }
            )
        }
        composable("registro") {
            RegistroGastoScreen(
                viewModel = viewModel,
                onGuardadoExitoso = { navController.navigate("dashboard") }
            )
        }
        composable("teqruta") {
            ImportarTeqRutaScreen(
                viewModel = viewModel,
                onImportado = { navController.navigate("dashboard") }
            )
        }
        composable("aprobacion") {
            AprobacionJefaturaScreen(viewModel = viewModel)
        }
    }
}