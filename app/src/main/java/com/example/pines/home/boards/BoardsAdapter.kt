package com.example.pines.home.boards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pines.databinding.ItemBoardBinding

class BoardsAdapter (
    //: RecyclerView.Adapter<BoardsAdapter.BoardViewHolder>()
    private val onClick: (Board) -> Unit,
    private val onLongClick: (Board) -> Unit
) : RecyclerView.Adapter<BoardsAdapter.BoardViewHolder>() {

    private val boards = mutableListOf<Board>()

    fun updateData(
        newBoards: List<Board>
    ) {
        boards.clear()
        boards.addAll(newBoards)
        notifyDataSetChanged()
    }

    inner class BoardViewHolder(
        private val binding: ItemBoardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(board: Board) {
            /*
            binding.tvBoardName.text =
                board.name
        }
        */
            binding.root.setOnClickListener {
                onClick(board)
            }

            binding.tvPinCount.text =
                "${board.pinCount} Pines"

            binding.tvBoardName.text = board.name

            binding.root.setOnLongClickListener {
                onLongClick(board)
                true
            }

        }
    }
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BoardViewHolder {

            val binding = ItemBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return BoardViewHolder(binding)
        }

        override fun onBindViewHolder(
            holder: BoardViewHolder,
            position: Int
        ) {
            holder.bind(boards[position])
        }

        override fun getItemCount(): Int {
            return boards.size
        }
    }

