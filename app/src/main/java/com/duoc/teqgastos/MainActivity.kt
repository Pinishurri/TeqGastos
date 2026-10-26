package com.duoc.teqgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.duoc.teqgastos.data.AppDatabase
import com.duoc.teqgastos.data.GastoRepository
import com.duoc.teqgastos.navigation.AppNavigation
import com.duoc.teqgastos.ui.theme.TeqGastosTheme
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "teqgastos_db"
        ).build()

        val repository = GastoRepository(database.gastoDao())
        val viewModel = GastoViewModel(repository)

        setContent {
            TeqGastosTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("TeqGastos - TEQMED SPA") }
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentRoute == "dashboard",
                                onClick = { navController.navigate("dashboard") },
                                label = { Text("Inicio") },
                                icon = { Text("🏠") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "registro",
                                onClick = { navController.navigate("registro") },
                                label = { Text("Nuevo") },
                                icon = { Text("➕") }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "aprobacion",
                                onClick = { navController.navigate("aprobacion") },
                                label = { Text("Aprobación") },
                                icon = { Text("📋") }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}