package com.android.sealanesshipping

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.sealanesshipping.databinding.FragmentTitleBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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
        checkCurrentUser()

        // This indicates that the fragment has an option menu
        setHasOptionsMenu(true)
        return binding.root
    }

    // Method is called when options menu is created
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.before_login, menu)
    }

    // Method is called when an option is selected from options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)

    }

    override fun onStart() {
        super.onStart()
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            view?.findNavController()?.navigate(R.id.action_titleFragment_to_destinationFragment)
        } else {
            // No user is signed in
            return
        }
        // [END check_current_user]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}