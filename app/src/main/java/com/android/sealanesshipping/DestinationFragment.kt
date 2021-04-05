package com.android.sealanesshipping

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.sealanesshipping.databinding.FragmentDestinationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Date
import java.text.DateFormat
import java.text.SimpleDateFormat


class DestinationFragment : Fragment() {

    private var _binding: FragmentDestinationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDestinationBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
         lateinit var documentid: String
        binding.button.setOnClickListener {
            db.collection("ships")
                .whereEqualTo("shipfrom", binding.etSourceDestination.text.toString())
                .whereEqualTo("shipto", binding.etfinalDestination.text.toString())
                .get()
                .addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "No ships available on that route.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.w(TAG, "Error getting document", e)
                }
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        documentid = document.id
                        val shipfrom = document.data["shipfrom"].toString()
                        val shipto = document.data["shipto"].toString()

                        val timestamp = document.data["ship_sail"] as com.google.firebase.Timestamp
                        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                        Log.d(TAG, milliseconds.toString())
                        val sdf = SimpleDateFormat("dd/MM/yyyy")
                        val netDate = Date(milliseconds)
                        //Log.d(TAG, netDate.toString())
                        val date = sdf.format(netDate)
                        //Log.d(TAG, date)

                        val str_date: String = binding.editTextDate.text.toString()
                        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                        val date1 = formatter.parse(str_date) as java.util.Date
                        val output = date1.time / 1000L
                        val str = java.lang.Long.toString(output)
                        val timestamp1 = str.toLong() * 1000
                        Log.d(TAG, timestamp1.toString())
                        if (timestamp1 > milliseconds){
                            Toast.makeText(
                                requireContext(),
                                "No ships available on that route and date.Please retry again later",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        else{
                            view?.findNavController()?.navigate(
                                DestinationFragmentDirections.actionDestinationFragmentToWeightInput(
                                    binding.etSourceDestination.text.toString(),
                                    binding.etfinalDestination.text.toString(),
                                    documentid
                                )
                            )
                        }

                        if (binding.etSourceDestination.text.toString() != shipfrom || binding.etfinalDestination.text.toString() != shipto){
                            Toast.makeText(
                                requireContext(),
                                "No ships available on that route.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

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

    // Method is called when options menu is selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        signOut()
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}