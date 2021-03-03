package com.android.sealanesshipping

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.sealanesshipping.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegistrationFragment : Fragment() {

    private var progressBar: ProgressBar? = null
    private var _binding: FragmentRegistrationBinding? = null
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

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
        setHasOptionsMenu(true)

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
                        requireContext(), "Signup Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    val user = hashMapOf(
                        "uuid" to auth.uid,
                        "email" to binding.etEmail.text.toString(),
                        "name" to binding.etName.text.toString(),
                        "phonenumber" to binding.etPhonenumber.text.toString()
                    )
                    db.collection("users")
                        .add(user)
                    sendEmailVerification()
                    binding.etEmail.setText("")
                    binding.etName.setText("")
                    binding.etPhonenumber.setText("")
                    binding.etpassword.setText("")

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        requireContext(),
                        "Registration failed. Please try again after some time.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                hideProgressBar()
            }
        // [END create_user_with_email]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.before_login, menu)
    }

    // Method is called when an option is selected from options menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)

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