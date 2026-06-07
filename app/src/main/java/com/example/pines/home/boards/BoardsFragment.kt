package com.example.pines.home.boards

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pines.databinding.FragmentBoardsBinding
import kotlinx.coroutines.launch
import com.example.pines.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController

class BoardsFragment : Fragment() {

    private lateinit var adapter: BoardsAdapter
    private var _binding: FragmentBoardsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<BoardsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            FragmentBoardsBinding.inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?

    ) {
        super.onViewCreated(view, savedInstanceState)

        //adapter = BoardsAdapter()
        adapter = BoardsAdapter(

            onClick = { board ->

                val bundle = Bundle()

                bundle.putString(
                    "boardId",
                    board.id
                )

                bundle.putString(
                    "boardName",
                    board.name
                )

                findNavController().navigate(
                    R.id.action_boardsFragment_to_boardDetailFragment,
                    bundle
                )

            },

            onLongClick = { board ->
                showDeleteBoardDialog(board)
            }
        )

        binding.rvBoards.adapter = adapter

        binding.rvBoards.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        binding.btnNewBoard.setOnClickListener {
            showCreateBoardDialog()
        }

        observeBoards()
        viewModel.loadBoards()

    }

    private fun showCreateBoardDialog() {

        val input = EditText(requireContext())

        AlertDialog.Builder(requireContext())
            .setTitle("Nueva carpeta")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->

                val name =
                    input.text.toString()

                if (name.isNotBlank()) {
                    viewModel.createBoard(name)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun observeBoards() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.boards.collect { boards ->

                adapter.updateData(boards)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showDeleteBoardDialog(
        board: Board
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar carpeta")
            .setMessage(
                "¿Deseas eliminar \"${board.name}\"?"
            )
            .setPositiveButton("Eliminar") { _, _ ->

                viewModel.deleteBoard(
                    board.id
                )
            }
            .setNegativeButton(
                "Cancelar",
                null
            )
            .show()
    }


}