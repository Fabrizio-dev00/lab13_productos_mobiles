package com.miempresa.jimenez_semana13.data.model

data class Producto(
    val id: String = "",
    val nombre: String = "",
    val categoria: String = "",
    val precio: Double = 0.0,
    val stock: Int = 0,
    val codigoBarras: String = "",
    val descripcion: String = "",
    val userId: String = ""
)
