package com.duoc.teqgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.duoc.teqgastos.data.AppDatabase
import com.duoc.teqgastos.data.GastoRepository
import com.duoc.teqgastos.ui.screen.RegistroGastoScreen
import com.duoc.teqgastos.ui.theme.TeqGastosTheme
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "teqgastos_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val repository = GastoRepository(database.gastoDao())

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GastoViewModel(repository) as T
            }
        }
        val viewModel = ViewModelProvider(this, factory)[GastoViewModel::class.java]

        setContent {
            TeqGastosTheme {
                RegistroGastoScreen(viewModel = viewModel)
            }
        }
    }
}