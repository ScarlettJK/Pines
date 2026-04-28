package com.example.pines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pines.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    //PENDIENTE
    private val viewModel by viewModels<SignInViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_register, container, false)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        //PENDIENTE
        binding.signUpButton.setOnClickListener { //In ?
            viewModel.requestSignUp(binding.emailTiet.text.toString().trim(), binding.passwordTiet.text.toString().trim())
        }
        return binding.root
    }
    }

