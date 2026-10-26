package com.duoc.teqgastos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos_terreno")
data class Gasto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val categoria: String,
    val centroCosto: String,
    val rutProveedor: String,
    val monto: Int,
    val origenFondo: String, // "Fondo por Rendir / Corp" o "Dinero Propio (Reembolso)"
    val imagenBoletaUri: String,
    val estado: String = "PENDIENTE", // "PENDIENTE", "APROBADO", "RECHAZADO"
    val observacion: String = ""
)