package com.duoc.teqgastos.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel

@Composable
fun DashboardScreen(viewModel: GastoViewModel, onNavegarRegistro: () -> Unit, onNavegarTeqRuta: () -> Unit) {
    val gastos by viewModel.listaGastos.collectAsState()
    val totalRendido = gastos.sumOf { it.monto }
    val totalAprobado = gastos.filter { it.estado == "APROBADO" }.sumOf { it.monto }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Resumen de Gastos en Terreno", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Rendido: $$totalRendido CLP", style = MaterialTheme.typography.bodyLarge)
                Text("Total Aprobado: $$totalAprobado CLP", color = Color(0xFF2E7D32))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onNavegarRegistro, modifier = Modifier.weight(1f).padding(end = 4.dp)) {
                Text("➕ Nuevo Gasto")
            }
            Button(onClick = onNavegarTeqRuta, modifier = Modifier.weight(1f).padding(start = 4.dp)) {
                Text("🚚 Importar TeqRuta")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Últimos Movimientos Guardados (SQLite):", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(gastos) { gasto ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    ListItem(
                        headlineContent = { Text("${gasto.categoria} - $${gasto.monto}") },
                        supportingContent = { Text("${gasto.centroCosto} | ${gasto.origenFondo}") },
                        trailingContent = {
                            val colorEstado = when (gasto.estado) {
                                "APROBADO" -> Color(0xFF2E7D32)
                                "RECHAZADO" -> Color.Red
                                else -> Color(0xFFE65100)
                            }
                            Text(gasto.estado, color = colorEstado)
                        }
                    )
                }
            }
        }
    }
}