package com.android.sealanesshipping

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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


        setHasOptionsMenu(true)
        checkCurrentUser()
        return binding.root
    }

    private fun onClick() {
        signIn(binding.etEmaillogin.text.toString(), binding.etpasswordlogin.text.toString())
    }

    private fun checkCurrentUser() {
        // [START check_current_user]
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_destinationFragment)
        } else {
            // No user is signed in
            return
        }
        // [END check_current_user]
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
                    view?.findNavController()
                        ?.navigate(R.id.action_loginFragment_to_destinationFragment)
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        requireContext(), "Email/Password entered is wrong.",
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


    private fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    private fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
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

    override fun onStart() {
        super.onStart()
        checkCurrentUser()
    }

    companion object {
        private const val TAG = "Main"
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}