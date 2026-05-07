package com.example.pines.onboarding.personal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pines.R
import com.example.pines.core.FragmentCommunicator
import com.example.pines.databinding.FragmentPersonalInfoBinding
import com.example.pines.onboarding.personal.model.PersonalInfoViewModel

private var _binding: FragmentPersonalInfoBinding? = null
private val binding get() = _binding!!
private val viewModel by viewModels<PersonalInfoViewModel>()
private lateinit var communicator: FragmentCommunicator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false)
    }


}