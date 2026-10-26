package com.duoc.teqgastos.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel

@Composable
fun ImportarTeqRutaScreen(viewModel: GastoViewModel, onImportado: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Importar Traslados calculados por TeqRuta", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ruta: Concepción -> Los Ángeles (Comisión Sur)")
                Text("Combustible estimado: $18.500 CLP")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    viewModel.importarTrasladoTeqRuta("Combustible", 18500, "Importado desde TeqRuta - Gira Los Ángeles")
                    onImportado()
                }) {
                    Text("📥 Importar como Gasto")
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ruta: Peajes Autopista del Itata")
                Text("Peajes calculados: $4.800 CLP")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    viewModel.importarTrasladoTeqRuta("Peaje", 4800, "Importado desde TeqRuta - Peajes Itata")
                    onImportado()
                }) {
                    Text("📥 Importar como Gasto")
                }
            }
        }
    }
}