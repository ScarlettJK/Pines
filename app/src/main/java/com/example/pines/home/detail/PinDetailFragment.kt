package com.example.pines.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pines.R
import com.example.pines.core.model.Pines
import com.example.pines.databinding.FragmentPinDetailBinding

class PinDetailFragment : Fragment() {

    private var _bimding: FragmentPinDetailBinding? = null
    private val binding get() = _bimding!!

    private val viewModel by viewModels<PinDetailFragment>()

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
        return inflater.inflate(R.layout.fragment_pin_detail, container, false)
    }

    companion object {

    }
}