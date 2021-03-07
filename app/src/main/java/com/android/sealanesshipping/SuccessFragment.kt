package com.android.sealanesshipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val args1 = SuccessFragmentArgs.fromBundle(requireArguments())
        val user = Firebase.auth.currentUser

        binding.textView10.text = args1.orderId.toString()
        if (user != null) {
            binding.textView7.text = user.displayName
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}