package com.example.myfamilyproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfamilyproject.databinding.FragmentGuardBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class GuardFragment : Fragment(), InviteMailAdapter.OnActionClick {

    private lateinit var binding: FragmentGuardBinding
    private lateinit var adapter: InviteMailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        adapter = InviteMailAdapter(ArrayList(), this)
        binding.inviteRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.inviteRecycler.adapter = adapter

        // Set up button click listener
        binding.sendInvite.setOnClickListener {
            sendInvite()
        }
        binding.pinkCard.setOnClickListener {
            makePhoneCall("7895020951")
        }
        binding.greenCard.setOnClickListener {
            sendMessage("7895020951","I need help")
        }
        // Fetch invites
        getInvites()
    }

    private fun getInvites() {
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
        Log.d("Invite", currentUserEmail)
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("users")
            .document(currentUserEmail)
            .collection("invites")
            .whereEqualTo("invite_status", 0) // Filter invitations where accept status is 1
            .get()
            .addOnSuccessListener { documents ->
                val list: ArrayList<String> = ArrayList()
                for (document in documents) {
                    // Add each invite to the list
                    list.add(document.id)
                }

                Log.d("Invite", "Invites: $list")

                // Update adapter data
                adapter.updateInvites(list)
            }
            .addOnFailureListener { exception ->
                Log.w("Invite", "Error getting invites", exception)
            }
    }



    private fun sendInvite() {
        val receiverEmail = binding.inviteMail.text.toString()

        // Get the current user's email
        val senderEmail = FirebaseAuth.getInstance().currentUser?.email.toString()

        val firestore = Firebase.firestore

        val data = hashMapOf(
            "invite_status" to 0
        )

        // Set the invite document inside the receiver's collection
        firestore.collection("users")
            .document(receiverEmail)  // Use receiver's email here
            .collection("invites")
            .document(senderEmail)  // Use sender's email here
            .set(data)
            .addOnSuccessListener {
                sendMessage("7895020951", "Thank you for accepting invitation")
            }
            .addOnFailureListener {
                sendMessage("7895020951", "It's urgent call me")
            }
    }

    override fun onAcceptClick(mail: String) {
        Log.d("invite89", "onAcceptClick: $mail")
        updateInviteStatus(mail, 1)
    }

    override fun onDenyClick(mail: String) {
        Log.d("invite89", "onDenyClick: $mail")
        updateInviteStatus(mail, -1)
    }

    private fun updateInviteStatus(mail: String, status: Int) {
        val firestore = FirebaseFirestore.getInstance()

        val data = hashMapOf(
            "invite_status" to status
        )

        val senderMail = FirebaseAuth.getInstance().currentUser?.email.toString()

        firestore.collection("users")
            .document(senderMail)
            .collection("invites")
            .document(mail)
            .set(data)
            .addOnSuccessListener {
                // Invitation status updated successfully
                sendMessage("7895020951", "Thank you for accepting invitation")
            }
            .addOnFailureListener { exception ->
                Log.e("UpdateInvite", "Error updating invite status", exception)
                sendMessage("7895020951", "It's urgent call me")
                // Handle error
            }
    }
    private fun makePhoneCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }
    private fun sendMessage(number: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$number")
        intent.putExtra("sms_body", message)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GuardFragment()
    }
}
