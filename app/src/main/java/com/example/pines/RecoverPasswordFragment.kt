package com.example.pines

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pines.databinding.FragmentRecoverPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordFragment : Fragment() {

    private var _binding: FragmentRecoverPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentRecoverPasswordBinding.inflate(
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

        super.onViewCreated(
            view,
            savedInstanceState
        )

        binding.btnBack.setOnClickListener {

            findNavController().navigateUp()
        }

        binding.btnRecuperar.setOnClickListener {

            sendRecoveryEmail()
        }
    }

    private fun sendRecoveryEmail() {

        val email =
            binding.emailTiet.text
                .toString()
                .trim()

        if (email.isEmpty()) {

            Toast.makeText(
                requireContext(),
                "Ingresa un correo",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Toast.makeText(
                requireContext(),
                "Correo inválido",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        FirebaseAuth
            .getInstance()
            .sendPasswordResetEmail(email)
            .addOnSuccessListener {

                Toast.makeText(
                    requireContext(),
                    "Correo enviado",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigateUp()
            }
            .addOnFailureListener {

                Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}