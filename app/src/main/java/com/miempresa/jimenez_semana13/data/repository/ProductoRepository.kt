package com.miempresa.jimenez_semana13.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.miempresa.jimenez_semana13.data.model.Producto
import kotlinx.coroutines.tasks.await

class ProductoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val productosRef = db.collection("productos")

    suspend fun agregarProducto(producto: Producto) {
        val docRef = productosRef.document()
        val productoConId = producto.copy(id = docRef.id)
        docRef.set(productoConId).await()
    }

    suspend fun actualizarProducto(producto: Producto) {
        productosRef.document(producto.id).set(producto).await()
    }

    suspend fun eliminarProducto(id: String) {
        productosRef.document(id).delete().await()
    }

    suspend fun obtenerProductos(): List<Producto> {
        return productosRef.get().await().toObjects(Producto::class.java)
    }

    suspend fun obtenerProducto(id: String): Producto? {
        return productosRef.document(id).get().await().toObject(Producto::class.java)
    }
}
