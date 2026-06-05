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

class PinDetailFragment : Fragment() {

    private var _binding: FragmentPinDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PinDetailViewModel>()

    private lateinit var pin: Pines

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pin = requireArguments().getParcelable("pin")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}