package com.android.sealanesshipping

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.android.sealanesshipping.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.R.attr.button
import android.R.attr.onClick


class RegistrationFragment : Fragment() {


    private var _binding: FragmentRegistrationBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.button4.setOnClickListener {
            onClick()
        }
        auth = Firebase.auth

        return binding.root
    }

    private fun onClick() {
        createAccount(binding.etEmail.text.toString(),binding.etpassword.text.toString())
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        requireContext(), "Authentication successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = auth.currentUser
                    view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }
        // [END create_user_with_email]
    }

    private fun validateForm(): Boolean {
        val email = binding.etEmail.text.toString()
        var valid = true

        if (TextUtils.isEmpty(email)) {
            binding.etEmail.error = "Required."
            valid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etpassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etpassword.error = "Required."
            valid = false
        } else {
            binding.etpassword.error = null
        }

        return valid
    }

    companion object {
        private const val TAG = "Main"
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}