package com.miempresa.jimenez_semana13.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val idProducto: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int,
    val codigoBarras: String,
    val descripcion: String = ""
)