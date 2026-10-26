package com.duoc.teqgastos.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GastoDao {

    @Insert
    suspend fun insertarGasto(gasto: Gasto)

    @Query("SELECT * FROM gastos_terreno ORDER BY id DESC")
    suspend fun obtenerTodosLosGastos(): List<Gasto>
}