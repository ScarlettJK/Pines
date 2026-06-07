package com.example.pines.core.repositories

import com.example.pines.core.model.Pines
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PinRepository {

    private val firestore =
        FirebaseFirestore.getInstance()

    private val auth =
        FirebaseAuth.getInstance()

    suspend fun savePinToBoard(
        boardId: String,
        pin: Pines
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("boards")
            .document(boardId)
            .collection("pins")
            .document(pin.id)
            .set(pin)
            .await()
    }

    suspend fun getPinsFromBoard(
        boardId: String
    ): List<Pines> {

        val uid =
            auth.currentUser?.uid ?: return emptyList()

        val result =
            firestore.collection("users")
                .document(uid)
                .collection("boards")
                .document(boardId)
                .collection("pins")
                .get()
                .await()

        return result.toObjects(Pines::class.java)
        }

    suspend fun removePinFromBoard(
        boardId: String,
        pinId: String
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("boards")
            .document(boardId)
            .collection("pins")
            .document(pinId)
            .delete()
            .await()
    }



    }

