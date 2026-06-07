package com.example.pines.home.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pines.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

import android.content.Intent
import com.example.pines.home.HomeActivity
import com.example.pines.onboarding.MainActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentAccountBinding.inflate(
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

        val uid =
            FirebaseAuth
                .getInstance()
                .currentUser
                ?.uid
                ?: return

        viewModel.loadUser(uid)

        viewModel.loadStats()

        observeUser()


        binding.btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val intent =
                Intent(
                    requireContext(),
                    MainActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            requireActivity().finish()
        }

    }

    private fun observeUser() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.user.collect { user ->

                if (user != null) {

                    binding.tvName.text =
                        "${user.firstName} ${user.lastName}"

                    binding.tvUsername.text =
                        "@${user.userName}"

                    binding.tvPhone.text =
                        user.phone

                    binding.tvBirthDate.text =
                        user.birthDate

                    binding.tvEmail.text =
                        FirebaseAuth
                            .getInstance()
                            .currentUser
                            ?.email
                            ?: ""
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.boardsCount.collect { count ->

                binding.tvBoardsCount.text =
                    count.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.pinsCount.collect { count ->

                binding.tvPinsCount.text =
                    count.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}