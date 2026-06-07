package com.example.pines.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pines.R
import com.example.pines.core.model.Pines
import com.example.pines.databinding.FragmentPinDetailBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

import android.content.Intent
import android.widget.PopupMenu
import android.widget.Toast

import android.app.AlertDialog

import com.example.pines.home.boards.Board

import com.example.pines.core.repositories.BoardRepository
import com.example.pines.core.repositories.PinRepository

class PinDetailFragment : Fragment() {

    private var _binding: FragmentPinDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PinDetailViewModel>()

    private lateinit var pin: Pines

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pin = requireArguments().getParcelable("pin")
            ?: error("pin argument required")

        android.util.Log.d(
            "PIN_DETAIL",
            "Fragment creado"
        )

        pin = arguments?.getSerializable("pin") as? Pines
            ?: error("pin argument required")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_pin_detail, container, false)
        _binding = FragmentPinDetailBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindPinInfo()
        setupListeners()
        observeViewModel()

        viewModel.loadPin(pin)
    }

    private fun bindPinInfo() {

        Glide.with(binding.ivPhoto)
            .load(pin.urls.regular)
            .centerCrop()
            .into(binding.ivPhoto)

        binding.tvDescription.text =
            pin.altDescription ?: "Sin descripción"

        binding.tvUsername.text =
            "@${pin.user.username}"

        binding.tvName.text =
            pin.user.name

        binding.tvLocation.text =
            pin.user.location ?: "Ubicación no disponible"
    }

    private fun setupListeners() {

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnLike.setOnClickListener {
            viewModel.toggleLike()
        }

        binding.btnOptions.setOnClickListener {


            val popupMenu =
                PopupMenu(requireContext(), binding.btnOptions)

            popupMenu.menuInflater.inflate(
                R.menu.pin_options_menu,
                popupMenu.menu
            )


            for (i in 0 until popupMenu.menu.size()) {

                val item = popupMenu.menu.getItem(i)

                val spannable = android.text.SpannableString(item.title)

                spannable.setSpan(
                    android.text.style.ForegroundColorSpan(
                        resources.getColor(R.color.purple, null)
                    ),
                    0,
                    spannable.length,
                    0
                )

                item.title = spannable
            }


            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_save_board -> {
                        showBoardsDialog()
                        Toast.makeText(
                            requireContext(),
                            "Guardar en Board",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }

                    R.id.action_share -> {
                        sharePin()
                        true
                    }

                    R.id.action_download -> {
                        Toast.makeText(
                            requireContext(),
                            "Descargando imagen...",
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {

                    viewModel.likes.collect { likes ->

                        binding.tvLikes.text =
                            likes.toString()
                    }
                }

                launch {

                    viewModel.liked.collect { liked ->

                        binding.btnLike.setImageResource(
                            if (liked)
                                R.drawable.ic_heart_filled
                            else
                                R.drawable.ic_heart_outline
                        )
                    }
                }
            }
        }
    }

    private fun sharePin() {

        val intent = Intent().apply {

            action = Intent.ACTION_SEND

            putExtra(
                Intent.EXTRA_TEXT,
                pin.urls.regular
            )

            type = "text/plain"
        }

        startActivity(
            Intent.createChooser(
                intent,
                "Compartir pin"
            )
        )
    }

    private fun showBoardsDialog() {

        viewLifecycleOwner.lifecycleScope.launch {

            val boards =
                BoardRepository().getBoards()

            if (boards.isEmpty()) {
                return@launch
            }

            val names =
                boards.map { it.name }
                    .toTypedArray()

            AlertDialog.Builder(requireContext())
                .setTitle("Guardar en carpeta")
                .setItems(names) { _, which ->

                    val board =
                        boards[which]

                    savePin(board)
                }
                .show()
        }
    }

    private fun savePin(
        board: Board
    ) {

        viewLifecycleOwner.lifecycleScope.launch {

            PinRepository()
                .savePinToBoard(
                    board.id,
                    pin
                )

            BoardRepository()
                .increasePinCount(
                    board.id
                )

            Toast.makeText(
                requireContext(),
                "Guardado en ${board.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}