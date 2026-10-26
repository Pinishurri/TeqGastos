package com.duoc.teqgastos.data

class GastoRepository(private val gastoDao: GastoDao) {

    suspend fun insertarGasto(gasto: Gasto) {
        gastoDao.insertarGasto(gasto)
    }

    suspend fun obtenerTodosLosGastos(): List<Gasto> {
        return gastoDao.obtenerTodosLosGastos()
    }
}