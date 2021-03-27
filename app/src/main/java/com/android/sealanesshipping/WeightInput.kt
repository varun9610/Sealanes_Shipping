package com.android.sealanesshipping

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.sealanesshipping.databinding.FragmentWeightInputBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WeightInput : Fragment() {


    private var _binding: FragmentWeightInputBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    private var order_id: Int = 1
    private var rem_cap: String? = "0"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeightInputBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        val args = WeightInputArgs.fromBundle(requireArguments())
        setHasOptionsMenu(true)
        order_id = (1000..100000).random()
        binding.button2.setOnClickListener {

            val orders = hashMapOf(
                "ship_assigned" to args.documentid,
                "uuid" to auth.uid,
                "cargo_weight" to binding.etWeightInput.text.toString(),
                "source_location" to args.sourceLocation,
                "destination" to args.destinationLocation,
                "order_id" to order_id
            )

            db.collection("ships").document(args.documentid).collection("container")
                .whereLessThan("container_cap", binding.etWeightInput.text.toString())
                .get()
                .addOnFailureListener {
                  /*  Toast.makeText(
                        requireContext(),
                        "Not enough container space available.",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
                .addOnSuccessListener { queryDocumentSnapshots ->
                    for (snap in queryDocumentSnapshots) {
                        Log.d(TAG, "${snap.id} => ${snap.data}")
                        val container_cap = snap.data["container_cap"].toString()
                        rem_cap = (container_cap.toInt() - (binding.etWeightInput.text.toString()).toInt()).toString()
                        if (rem_cap!!.toInt() < 0){
                            Toast.makeText(
                                requireContext(),
                                "Not enough container space available.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        else{

                            db.collection("ships").document(args.documentid).collection("container")
                                .document("container")
                                .update("container_cap", rem_cap)
                            db.collection("orders")
                                .add(orders)
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                }
                                .addOnSuccessListener {
                                    view?.findNavController()?.navigate(
                                        WeightInputDirections.actionWeightInputToSuccessFragment(
                                            order_id
                                        )
                                    )
                                }
                        }
                        Log.d(TAG, rem_cap!!)
                        Log.d(TAG,container_cap)

                    }

                }

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