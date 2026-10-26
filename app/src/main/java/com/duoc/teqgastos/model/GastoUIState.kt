package com.duoc.teqgastos.model

data class GastoUIState(
    val fecha: String = "20/10/2026",
    val categoria: String = "Peaje",
    val centroCosto: String = "CC-101 - Servicio Técnico Gran Concepción",
    val rutProveedor: String = "",
    val monto: String = "",
    val origenFondo: String = "Dinero Propio (Reembolso)",
    val imagenBoletaUri: String? = null,
    val observacion: String = ""
)