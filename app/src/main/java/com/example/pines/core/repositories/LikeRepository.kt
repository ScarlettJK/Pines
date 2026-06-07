package com.example.pines.core.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LikeRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val auth =
        FirebaseAuth.getInstance()

    suspend fun saveLike(pinId: String) {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("likes")
            .document(pinId)
            .set(mapOf("liked" to true))
            .await()
    }

    suspend fun removeLike(pinId: String) {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("likes")
            .document(pinId)
            .delete()
            .await()
    }

    suspend fun isLiked(
        pinId: String
    ): Boolean {

        val uid =
            auth.currentUser?.uid ?: return false

        val doc =
            firestore.collection("users")
                .document(uid)
                .collection("likes")
                .document(pinId)
                .get()
                .await()

        return doc.exists()
    }
}