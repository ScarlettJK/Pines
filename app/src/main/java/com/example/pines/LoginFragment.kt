package com.example.pines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.pines.core.FragmentCommunicator
import com.example.pines.core.ResponseService
import com.example.pines.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch




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

        //binding.txtRegistro.setOnClickListener {
            //findNavController().navigate(R.id.action_loginFragment_to_registerFragment) //Busca el grafo de navegacion que esta enlazado al
        //}
        setupClickListeners()
        observeState()

        return binding.root
    }

    private fun setupValidation() {
        binding.signInButton.isEnabled = false
        /*
        binding.emailTiet.addTextChangedListener {
            validateFields()

        }

        binding.passwordTiet.addTextChangedListener {
            validateFields()

        }
        */
        binding.emailTiet.addTextChangedListener { validateAndEnable() }
        binding.passwordTiet.addTextChangedListener { validateAndEnable() }

    }

    private fun validateAndEnable() {
        val email = binding.emailTiet.text.toString().trim()
        val password = binding.passwordTiet.text.toString().trim()
        /*
        val isEmailValid = isValidEmail(email)
        val isPasswordValid = password.length >= 8

        binding.emailTil.error = if (email.isEmpty() || isEmailValid) null else "Correo invalido"
        binding.passwordTil.error = if (password.isEmpty() || isPasswordValid) null else "Minimo 8 caracteres"
        */

        binding.emailTil.error = viewModel.validateEmail(email)
        binding.passwordTil.error = viewModel.validatePassword(password)
        binding.signInButton.isEnabled = viewModel.isLoginFormValid(email, password)

        /*
        binding.signInButton.isEnabled =
            email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid
    }
    */
        }

        private fun setUpClickListeners(){
        binding.signInButton.setOnClickListener {
            val email = binding.emailTiet.text.toString().trim()
            val password = binding.passwordTiet.text.toString().trim()
            viewModel.requestLogin(email, password)
        }
            binding.registertext.setOnClickListener {
                findNavController()
                    .navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

    /*
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    */

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.state.STARTED) {
                viewModel.signInState.collect { state ->
                    when (state) {
                        is ResponseService.Loading -> {
                            communicator.manageLoader(true)
                            binding.signInButton.isEnabled = false
                        }
                        is ResponseService.Success -> {
                            communicator.manageLoader(false)
                            //TODO: navegar a Main activity
                        }
                        is ResponseService.Error -> {
                            communicator.manageLoader(false)
                            binding.signInButton.isEnabled  true
                            Snackbar.make(binding.root, state.error,
                                Snackbar.LENGTH_LONG).show()
                        }
                        null -> Unit
                    }
                }
            }
        }
    }

    }
