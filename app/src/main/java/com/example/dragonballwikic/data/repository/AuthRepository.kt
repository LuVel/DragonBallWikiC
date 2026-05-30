package com.example.dragonballwikic.data.repository

import com.example.dragonballwikic.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Usuario actual activo
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    // ── LOGIN ──────────────────────────────────────────────────────────────
    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── REGISTRO ───────────────────────────────────────────────────────────
    suspend fun register(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        username: String,
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            // 1. Crear usuario en Firebase Auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user!!

            // 2. Guardar datos extra en Firestore
            val user = User(
                uid = firebaseUser.uid,
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                username = username,
                email = email
            )
            saveUserToFirestore(user)

            Result.success(firebaseUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── GUARDAR EN FIRESTORE ───────────────────────────────────────────────
    private suspend fun saveUserToFirestore(user: User) {
        firestore
            .collection("users")
            .document(user.uid)
            .set(user)
            .await()
    }

    // ── OBTENER DATOS DEL USUARIO ──────────────────────────────────────────
    suspend fun getUserData(uid: String): Result<User> {
        return try {
            val document = firestore
                .collection("users")
                .document(uid)
                .get()
                .await()
            val user = document.toObject(User::class.java)
            Result.success(user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ── CERRAR SESIÓN ──────────────────────────────────────────────────────
    fun logout() {
        auth.signOut()
    }
}