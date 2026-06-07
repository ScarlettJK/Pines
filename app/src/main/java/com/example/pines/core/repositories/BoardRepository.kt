package com.example.pines.core.repositories

import com.example.pines.home.boards.Board
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BoardRepository {
    private val firestore =
        FirebaseFirestore.getInstance()

    private val auth =
        FirebaseAuth.getInstance()

    suspend fun createBoard(
        name: String
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        val boardId =
            firestore.collection("users")
                .document(uid)
                .collection("boards")
                .document()
                .id

        val board =
            Board(
                id = boardId,
                name = name
            )

        firestore.collection("users")
            .document(uid)
            .collection("boards")
            .document(boardId)
            .set(board)
            .await()
    }

    suspend fun getBoards(): List<Board> {

        val uid =
            auth.currentUser?.uid ?: return emptyList()

        val result =
            firestore.collection("users")
                .document(uid)
                .collection("boards")
                .get()
                .await()

        return result.toObjects(Board::class.java)
    }

    suspend fun increasePinCount(
        boardId: String
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        val boardRef =
            firestore.collection("users")
                .document(uid)
                .collection("boards")
                .document(boardId)

        firestore.runTransaction { transaction ->

            val snapshot =
                transaction.get(boardRef)

            val current =
                snapshot.getLong("pinCount")
                    ?: 0

            transaction.update(
                boardRef,
                "pinCount",
                current + 1
            )
        }.await()
    }

    suspend fun decreasePinCount(
        boardId: String
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        val boardRef =
            firestore.collection("users")
                .document(uid)
                .collection("boards")
                .document(boardId)

        firestore.runTransaction { transaction ->

            val snapshot =
                transaction.get(boardRef)

            val current =
                snapshot.getLong("pinCount")
                    ?: 0

            transaction.update(
                boardRef,
                "pinCount",
                maxOf(0, current - 1)
            )
        }.await()
    }


    suspend fun getBoardsCount(): Int {

        val uid =
            auth.currentUser?.uid
                ?: return 0

        return firestore.collection("users")
            .document(uid)
            .collection("boards")
            .get()
            .await()
            .size()
    }

    suspend fun getTotalPinsCount(): Int {

        val uid =
            auth.currentUser?.uid
                ?: return 0

        val result =
            firestore.collection("users")
                .document(uid)
                .collection("boards")
                .get()
                .await()

        var total = 0

        for (doc in result.documents) {

            total +=
                doc.getLong("pinCount")
                    ?.toInt()
                    ?: 0
        }

        return total
    }


    suspend fun deleteBoard(
        boardId: String
    ) {

        val uid =
            auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(uid)
            .collection("boards")
            .document(boardId)
            .delete()
            .await()
    }



}