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

    private val _listaGastos = MutableStateFlow<List<Gasto>>(emptyList())
    val listaGastos: StateFlow<List<Gasto>> = _listaGastos.asStateFlow()

    init {
        cargarGastos()
    }

    fun cargarGastos() {
        viewModelScope.launch {
            _listaGastos.value = repository.obtenerTodosLosGastos()
        }
    }

    fun actualizarMonto(monto: String) { _uiState.value = _uiState.value.copy(monto = monto) }
    fun actualizarCategoria(categoria: String) { _uiState.value = _uiState.value.copy(categoria = categoria) }
    fun actualizarCentroCosto(cc: String) { _uiState.value = _uiState.value.copy(centroCosto = cc) }
    fun actualizarRutProveedor(rut: String) { _uiState.value = _uiState.value.copy(rutProveedor = rut) }
    fun actualizarOrigenFondo(origen: String) { _uiState.value = _uiState.value.copy(origenFondo = origen) }
    fun actualizarImagenBoleta(uri: String) { _uiState.value = _uiState.value.copy(imagenBoletaUri = uri) }

    fun guardarGasto() {
        val estado = _uiState.value
        val montoNum = estado.monto.toIntOrNull() ?: 0

        if (montoNum > 0) {
            viewModelScope.launch {
                val nuevoGasto = Gasto(
                    fecha = estado.fecha,
                    categoria = estado.categoria,
                    centroCosto = estado.centroCosto,
                    rutProveedor = estado.rutProveedor,
                    monto = montoNum,
                    origenFondo = estado.origenFondo,
                    imagenBoletaUri = estado.imagenBoletaUri ?: ""
                )
                repository.insertarGasto(nuevoGasto)
                _uiState.value = GastoUIState()
                cargarGastos()
            }
        }
    }

    fun cambiarEstadoGasto(gasto: Gasto, nuevoEstado: String) {
        viewModelScope.launch {
            repository.actualizarGasto(gasto.copy(estado = nuevoEstado))
            cargarGastos()
        }
    }

    fun importarTrasladoTeqRuta(categoria: String, monto: Int, detalle: String) {
        viewModelScope.launch {
            val gastoImportado = Gasto(
                fecha = "20/10/2026",
                categoria = categoria,
                centroCosto = "CC-102 - Comisiones de Servicio Macrozona Sur",
                rutProveedor = "TeqRuta System",
                monto = monto,
                origenFondo = "Fondo por Rendir / Corp",
                imagenBoletaUri = "",
                observacion = detalle
            )
            repository.insertarGasto(gastoImportado)
            cargarGastos()
        }
    }
}