package com.miempresa.jimenez_semana13.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(nombre: String, email: String, password: String): Result<Unit> {
        return try {
            // Crear usuario en Auth
            val data = auth.createUserWithEmailAndPassword(email, password).await()

            val uid = data.user?.uid ?: throw Exception("No se pudo obtener el UID.")

            // Guardar nombre en Firestore
            val userData = mapOf(
                "uid" to uid,
                "nombre" to nombre,
                "email" to email
            )

            db.collection("users").document(uid).set(userData).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun isUserLoggedIn(): Boolean = auth.currentUser != null
}
