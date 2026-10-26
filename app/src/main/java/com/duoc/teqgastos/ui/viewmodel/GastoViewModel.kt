package com.duoc.teqgastos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.teqgastos.data.Gasto
import com.duoc.teqgastos.data.GastoRepository
import com.duoc.teqgastos.model.GastoUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GastoViewModel(private val repository: GastoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GastoUIState())
    val uiState: StateFlow<GastoUIState> = _uiState.asStateFlow()

    fun actualizarMonto(nuevoMonto: String) {
        _uiState.value = _uiState.value.copy(monto = nuevoMonto)
    }

    fun actualizarCategoria(nuevaCategoria: String) {
        _uiState.value = _uiState.value.copy(categoria = nuevaCategoria)
    }

    fun actualizarCentroCosto(nuevoCentroCosto: String) {
        _uiState.value = _uiState.value.copy(centroCosto = nuevoCentroCosto)
    }

    fun actualizarRutProveedor(nuevoRut: String) {
        _uiState.value = _uiState.value.copy(rutProveedor = nuevoRut)
    }

    fun actualizarImagenBoleta(uri: String) {
        _uiState.value = _uiState.value.copy(imagenBoletaUri = uri)
    }

    fun guardarGasto() {
        val estado = _uiState.value
        val montoNumerico = estado.monto.toIntOrNull() ?: 0

        if (montoNumerico > 0 && estado.imagenBoletaUri != null) {
            viewModelScope.launch {
                val nuevoGasto = Gasto(
                    fecha = "20/10/2026",
                    categoria = estado.categoria,
                    centroCosto = estado.centroCosto,
                    rutProveedor = estado.rutProveedor,
                    monto = montoNumerico,
                    imagenBoletaUri = estado.imagenBoletaUri
                )
                repository.insertarGasto(nuevoGasto)
                _uiState.value = GastoUIState()
            }
        }
    }
}