package com.example.pines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pines.databinding.FragmentLoginBinding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.pines.core.FragmentCommunicator


class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()

    private lateinit var communicator: FragmentCommunicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        //Implementacion communicator
        communicator = requireActivity() as FragmentCommunicator
        setupValidation()
        //Ejecucion communicator
        //communicator.manageLoader(isVisible = true)
        binding.txtRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment) //Busca el grafo de navegacion que esta enlazado al
        }

        return binding.root
    }

    private fun setupValidation() {
        binding.signInButton.isEnabled = false

        binding.emailTiet.addTextChangedListener {
            validateFields()

        }

        binding.passwordTiet.addTextChangedListener {
            validateFields()

        }

    }

    private fun validateFields() {
        val email = binding.emailTiet.text.toString().trim()
        val password = binding.passwordTiet.text.toString().trim()

        val isEmailValid = isValidEmail(email)
        val isPasswordValid = password.length >= 8

        binding.emailTil.error = if (email.isEmpty() || isEmailValid) null else "Correo invalido"
        binding.passwordTil.error = if (password.isEmpty() || isPasswordValid) null else "Minimo 8 caracteres"

        binding.signInButton.isEnabled =
            email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    }
