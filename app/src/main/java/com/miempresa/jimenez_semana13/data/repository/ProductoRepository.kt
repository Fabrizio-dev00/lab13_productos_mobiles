package com.miempresa.jimenez_semana13.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.miempresa.jimenez_semana13.data.model.Producto
import kotlinx.coroutines.tasks.await

class ProductoRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    private val productosRef = db.collection("productos")

    suspend fun obtenerProductos(): List<Producto> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        return try {
            productosRef
                .whereEqualTo("userId", uid)           // ← FILTRO
                .get()
                .await()
                .toObjects(Producto::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerProducto(id: String): Producto? {
        return try {
            productosRef.document(id)
                .get()
                .await()
                .toObject(Producto::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun agregarProducto(producto: Producto) {
        val uid = auth.currentUser?.uid ?: return

        val nuevo = producto.copy(userId = uid)        // ← GUARDA UID

        productosRef.add(nuevo).await()
    }

    suspend fun actualizarProducto(producto: Producto) {
        if (producto.id.isBlank()) return
        productosRef.document(producto.id).set(producto).await()
    }

    suspend fun eliminarProducto(id: String) {
        productosRef.document(id).delete().await()
    }
}
