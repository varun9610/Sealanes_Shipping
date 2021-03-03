package com.android.sealanesshipping

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.sealanesshipping.databinding.FragmentWeightInputBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class WeightInput : Fragment() {


    private var _binding: FragmentWeightInputBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private var order_id: Int = 1
    val random = Random(100000)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeightInputBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        auth = Firebase.auth

        binding.button2.setOnClickListener {
            order_id = random.nextInt(100,10000)
            val args = WeightInputArgs.fromBundle(requireArguments())
            val orders = hashMapOf(
                "uuid" to auth.uid,
                "cargo_weight" to binding.etWeightInput.text.toString(),
                "source_location" to args.sourceLocation,
                "destination" to args.destinationLocation,
                "order_id" to order_id
            )
            db.collection("orders")
                .add(orders)
                .addOnSuccessListener { view?.findNavController()?.navigate(WeightInputDirections.actionWeightInputToSuccessFragment(
                    order_id
                )) }
        }
        return binding.root
    }
    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        Toast.makeText(requireContext(), "You are signed out", Toast.LENGTH_SHORT).show()
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