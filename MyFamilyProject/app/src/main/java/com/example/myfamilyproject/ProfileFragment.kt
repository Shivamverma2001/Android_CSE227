package com.example.myfamilyproject
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var imageViewProfile: ImageView
    private lateinit var logoutButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        textViewName = view.findViewById(R.id.textViewName)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        imageViewProfile = view.findViewById(R.id.imageViewProfile)
        logoutButton = view.findViewById(R.id.logoutButton)

        // Fetch user details and populate UI
        val currentUser = auth.currentUser
        currentUser?.let {
            // Set user name
            textViewName.text = it.displayName

            // Set user email
            textViewEmail.text = it.email

            // Set user profile image
            Glide.with(this)
                .load(it.photoUrl)
                .placeholder(R.drawable.default_profile_image)
                .error(R.drawable.default_profile_image)
                .into(imageViewProfile)
        }

        // Set click listener for logout button
        logoutButton.setOnClickListener {
            auth.signOut()

            // Clear any other stored data (e.g., shared preferences)

            // Navigate to the login screen or any other appropriate screen
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
