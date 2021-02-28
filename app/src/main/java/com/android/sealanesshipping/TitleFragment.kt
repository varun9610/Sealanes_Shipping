package com.android.sealanesshipping

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.sealanesshipping.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {


    private var _binding: FragmentTitleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTitleBinding.inflate(inflater, container, false)
        binding.button5.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_loginFragment)
        }
        binding.button6.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_registrationFragment)
        }
        binding.button7.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.before_login, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}