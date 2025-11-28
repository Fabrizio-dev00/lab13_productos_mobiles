package com.miempresa.jimenez_semana13.data.repository

import com.miempresa.jimenez_semana13.data.local.Producto
import com.miempresa.jimenez_semana13.data.local.ProductoDao
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {

    fun getAllProductos(): Flow<List<Producto>> {
        return productoDao.getAllProductos()
    }

    fun buscarProductos(busqueda: String): Flow<List<Producto>> {
        return productoDao.buscarProductos(busqueda)
    }

    suspend fun insertProducto(producto: Producto) {
        productoDao.insertProducto(producto)
    }

    suspend fun updateProducto(producto: Producto) {
        productoDao.updateProducto(producto)
    }

    suspend fun deleteProducto(producto: Producto) {
        productoDao.deleteProducto(producto)
    }

    suspend fun getProductoById(id: Int): Producto? {
        return productoDao.getProductoById(id)
    }

    suspend fun verificarCodigoUnico(codigo: String): Boolean {
        return productoDao.getProductoByCodigo(codigo) == null
    }
}