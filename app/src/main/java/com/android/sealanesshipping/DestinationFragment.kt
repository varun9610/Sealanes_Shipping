package com.android.sealanesshipping

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.sealanesshipping.databinding.FragmentDestinationBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DestinationFragment : Fragment() {

    private var _binding: FragmentDestinationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDestinationBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding.root
    }
    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        Toast.makeText(requireContext(),"You are signed out",Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_destinationFragment_to_titleFragment)
        // [END auth_sign_out]
    }

    // Method is called when options menu is created
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.after_login, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        signOut()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}