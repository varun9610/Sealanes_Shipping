package com.android.sealanesshipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.sealanesshipping.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordFragment : Fragment() {


    private var _binding: FragmentForgotPasswordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        binding.button8.setOnClickListener {
            sendPasswordReset()
        }
        return binding.root
    }


    private fun sendPasswordReset() {
        // [START send_password_reset]
        Firebase.auth.sendPasswordResetEmail(binding.etEmailid.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), "Please check your Email.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        // [END send_password_reset]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}