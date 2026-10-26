package com.duoc.teqgastos.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel
import java.io.File

@Composable
fun RegistroGastoScreen(viewModel: GastoViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var uriTemporalCamera by remember { mutableStateOf<Uri?>(null) }

    fun crearUriTemporal(): Uri {
        val archivo = File(context.cacheDir, "boleta_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", archivo)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { fotoTomadaConExito ->
        if (fotoTomadaConExito && uriTemporalCamera != null) {
            viewModel.actualizarImagenBoleta(uriTemporalCamera.toString())
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Registrar Nuevo Gasto",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.monto,
            onValueChange = { viewModel.actualizarMonto(it) },
            label = { Text("Monto (CLP)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.categoria,
            onValueChange = { viewModel.actualizarCategoria(it) },
            label = { Text("Categoría (Peaje, Alimentación, etc.)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.centroCosto,
            onValueChange = { viewModel.actualizarCentroCosto(it) },
            label = { Text("Centro de Costo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.rutProveedor,
            onValueChange = { viewModel.actualizarRutProveedor(it) },
            label = { Text("RUT Proveedor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val nuevaUri = crearUriTemporal()
                uriTemporalCamera = nuevaUri
                cameraLauncher.launch(nuevaUri)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            val textoBoton = if (uiState.imagenBoletaUri == null) {
                "Tomar Foto de Boleta"
            } else {
                "Boleta Adjuntada Correctamente"
            }
            Text(text = textoBoton)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.guardarGasto() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Gasto")
        }
    }
}