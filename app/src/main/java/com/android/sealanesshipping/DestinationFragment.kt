package com.android.sealanesshipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.sealanesshipping.databinding.FragmentDestinationBinding


class DestinationFragment : Fragment() {

    private var _binding: FragmentDestinationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDestinationBinding.inflate(inflater, container, false)

        return binding.root
    }


}