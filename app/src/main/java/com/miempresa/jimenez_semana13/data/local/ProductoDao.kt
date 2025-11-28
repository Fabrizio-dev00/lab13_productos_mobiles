package com.miempresa.jimenez_semana13.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    fun getAllProductos(): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :busqueda || '%' OR categoria LIKE '%' || :busqueda || '%'")
    fun buscarProductos(busqueda: String): Flow<List<Producto>>

    @Query("SELECT * FROM productos WHERE idProducto = :id")
    suspend fun getProductoById(id: Int): Producto?

    @Insert
    suspend fun insertProducto(producto: Producto)

    @Update
    suspend fun updateProducto(producto: Producto)

    @Delete
    suspend fun deleteProducto(producto: Producto)

    @Query("SELECT * FROM productos WHERE codigoBarras = :codigo")
    suspend fun getProductoByCodigo(codigo: String): Producto?
}