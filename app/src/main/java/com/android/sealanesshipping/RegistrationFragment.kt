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
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.getSystemService


class RegistrationFragment : Fragment() {

    private var progressBar: ProgressBar? = null
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
        setProgressBar(binding.progressBar)
        auth = Firebase.auth

        return binding.root
    }

    fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }


    private fun onClick() {
        createAccount(binding.etEmail.text.toString(), binding.etpassword.text.toString())
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        showProgressBar()
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        requireContext(), "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    sendEmailVerification()
                    val user = auth.currentUser
                    view?.findNavController()
                        ?.navigate(R.id.action_registrationFragment_to_loginFragment)

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        requireContext(),
                        "Registration failed. Please Try Again in a after some time.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                hideProgressBar()
            }
        // [END create_user_with_email]
    }

    private fun sendEmailVerification() {


        // Send verification email
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(requireActivity()) { task ->
                // [START_EXCLUDE]

                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // [END_EXCLUDE]
            }
        // [END send_email_verification]
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