package com.duoc.teqgastos.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel

@Composable
fun AprobacionJefaturaScreen(viewModel: GastoViewModel) {
    val gastos by viewModel.listaGastos.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Módulo Aprobación de Jefatura", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(gastos) { gasto ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Técnico: Juan Pérez | ${gasto.fecha}")
                        Text("Categoría: ${gasto.categoria} - Monto: $${gasto.monto} CLP")
                        Text("Origen: ${gasto.origenFondo}")

                        // Alerta automática de topes
                        if (gasto.categoria == "Alimentación" && gasto.monto > 20000) {
                            Text("⚠️ ALERTA: Excede el tope de Alimentación ($20.000)", color = Color.Red)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { viewModel.cambiarEstadoGasto(gasto, "APROBADO") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                            ) {
                                Text("Aprobar")
                            }
                            Button(
                                onClick = { viewModel.cambiarEstadoGasto(gasto, "RECHAZADO") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Rechazar")
                            }
                        }
                    }
                }
            }
        }
    }
}