package com.android.sealanesshipping

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.findNavController
import com.android.sealanesshipping.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth

    private var progressBar: ProgressBar? = null
    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.button3.setOnClickListener {
            onClick()
        }
        setProgressBar(binding.progressBar2)
        auth = Firebase.auth
        binding.textView21.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        return binding.root
    }

    private fun onClick() {
        signIn(binding.etEmaillogin.text.toString(), binding.etpasswordlogin.text.toString())
    }

    private fun signIn(email: String, password: String) {

        if (!validateForm()) {
            return
        }
        showProgressBar()

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_destinationFragment)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()


                }

                // [START_EXCLUDE]
                if (!task.isSuccessful) {
                    Toast.makeText(
                        requireContext(), "Failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // [END_EXCLUDE]
                hideProgressBar()
            }
        // [END sign_in_with_email]
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


    private fun validateForm(): Boolean {
        val email = binding.etEmaillogin.text.toString()
        var valid = true

        if (TextUtils.isEmpty(email)) {
            binding.etEmaillogin.error = "Required."
            valid = false
        } else {
            binding.etEmaillogin.error = null
        }

        val password = binding.etpasswordlogin.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.etpasswordlogin.error = "Required."
            valid = false
        } else {
            binding.etpasswordlogin.error = null
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