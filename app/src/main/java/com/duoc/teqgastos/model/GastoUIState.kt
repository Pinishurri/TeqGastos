package com.duoc.teqgastos.model

data class GastoUIState(
    val fecha: String = "",
    val categoria: String = "",
    val centroCosto: String = "",
    val rutProveedor: String = "",
    val monto: String = "",
    val imagenBoletaUri: String? = null
)