package com.duoc.teqgastos.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.duoc.teqgastos.ui.viewmodel.GastoViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroGastoScreen(viewModel: GastoViewModel, onGuardadoExitoso: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var uriTemporalCamera by remember { mutableStateOf<Uri?>(null) }

    fun crearUriTemporal(): Uri {
        val archivo = File(context.cacheDir, "boleta_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", archivo)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito && uriTemporalCamera != null) {
            viewModel.actualizarImagenBoleta(uriTemporalCamera.toString())
        }
    }

    // Opciones para Dropdowns
    val categorias = listOf("Peaje", "Alimentación", "Estacionamiento", "Combustible", "Alojamiento", "Insumos Urgentes", "Otros")
    val centrosCosto = listOf(
        "CC-101 - Servicio Técnico Gran Concepción",
        "CC-102 - Comisiones de Servicio Macrozona Sur",
        "CC-103 - Mantenimiento Clínicas y Hospitales"
    )

    var expandedCat by remember { mutableStateOf(false) }
    var expandedCC by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Registrar Rendición de Gasto", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Monto
        OutlinedTextField(
            value = uiState.monto,
            onValueChange = { viewModel.actualizarMonto(it) },
            label = { Text("Monto Rendido (CLP)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown Categorías
        ExposedDropdownMenuBox(
            expanded = expandedCat,
            onExpandedChange = { expandedCat = !expandedCat }
        ) {
            OutlinedTextField(
                value = uiState.categoria,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría de Gasto") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedCat,
                onDismissRequest = { expandedCat = false }
            ) {
                categorias.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            viewModel.actualizarCategoria(item)
                            expandedCat = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown Centro de Costo
        ExposedDropdownMenuBox(
            expanded = expandedCC,
            onExpandedChange = { expandedCC = !expandedCC }
        ) {
            OutlinedTextField(
                value = uiState.centroCosto,
                onValueChange = {},
                readOnly = true,
                label = { Text("Centro de Costo Autorizado") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCC) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedCC,
                onDismissRequest = { expandedCC = false }
            ) {
                centrosCosto.forEach { cc ->
                    DropdownMenuItem(
                        text = { Text(cc) },
                        onClick = {
                            viewModel.actualizarCentroCosto(cc)
                            expandedCC = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.rutProveedor,
            onValueChange = { viewModel.actualizarRutProveedor(it) },
            label = { Text("RUT Proveedor / Establecimiento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Origen de Fondos:", style = MaterialTheme.typography.bodyMedium)
        Row {
            RadioButton(
                selected = uiState.origenFondo == "Dinero Propio (Reembolso)",
                onClick = { viewModel.actualizarOrigenFondo("Dinero Propio (Reembolso)") }
            )
            Text("Dinero Propio (Reembolso)", modifier = Modifier.padding(top = 12.dp))
        }
        Row {
            RadioButton(
                selected = uiState.origenFondo == "Fondo por Rendir / Corp",
                onClick = { viewModel.actualizarOrigenFondo("Fondo por Rendir / Corp") }
            )
            Text("Fondo por Rendir / Tarjeta Corp", modifier = Modifier.padding(top = 12.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Captura y Previsualización de Cámara
        Button(
            onClick = {
                val uri = crearUriTemporal()
                uriTemporalCamera = uri
                cameraLauncher.launch(uri)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (uiState.imagenBoletaUri == null) "📷 Fotografiar Comprobante" else "📷 Cambiar Foto de Comprobante")
        }

        uiState.imagenBoletaUri?.let { uriStr ->
            Spacer(modifier = Modifier.height(8.dp))
            Card(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(uriStr),
                    contentDescription = "Previsualización de Boleta",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.guardarGasto()
                onGuardadoExitoso()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("💾 Guardar Gasto en SQLite")
        }
    }
}