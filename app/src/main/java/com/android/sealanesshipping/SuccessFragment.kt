package com.android.sealanesshipping

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.android.sealanesshipping.databinding.FragmentSuccessBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SuccessFragment : Fragment() {


    private var _binding: FragmentSuccessBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val args = SuccessFragmentArgs.fromBundle(requireArguments())
        val user = Firebase.auth.currentUser

        binding.textView10.text = args.orderId.toString()

        setHasOptionsMenu(true)
        return binding.root
    }

    // Method is called when options menu is created
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
    }
    // Method is called when options menu is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}