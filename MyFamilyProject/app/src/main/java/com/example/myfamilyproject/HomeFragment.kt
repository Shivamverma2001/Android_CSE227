import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfamily.ContactModel
import com.example.myfamilyproject.MemberAdapter
import com.example.myfamilyproject.MemberModel
import com.example.myfamilyproject.MyFamilyDatabase
import com.example.myfamilyproject.PrefConstants
import com.example.myfamilyproject.ProfileFragment
import com.example.myfamilyproject.R
import com.example.myfamilyproject.SharedPref
import com.example.myfamilyproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var inviteAdapter: InviteAdapter
    lateinit var mContext: Context
    private val listContacts: ArrayList<ContactModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMembers = listOf<MemberModel>(
            MemberModel(
                "Lokesh",
                "9th building, 2nd floor, maldiv road, manali 9th building, 2nd floor",
                "90%",
                "220"
            ),
            MemberModel(
                "Kedia",
                "10th building, 3rd floor, maldiv road, manali 10th building, 3rd floor",
                "80%",
                "210"
            ),
            MemberModel(
                "Durgesh",
                "11th building, 4th floor, maldiv road, manali 11th building, 4th floor",
                "70%",
                "200"
            ),
            MemberModel(
                "Ramesh",
                "12th building, 5th floor, maldiv road, manali 12th building, 5th floor",
                "60%",
                "190"
            ),
        )

        val adapter = MemberAdapter(listMembers)

        binding.recyclerMember.layoutManager = LinearLayoutManager(mContext)
        binding.recyclerMember.adapter = adapter

        inviteAdapter = InviteAdapter(listContacts) { contactNumber ->
            sendMessageToContact(contactNumber)
        }

        fetchDatabaseContacts()

        CoroutineScope(Dispatchers.IO).launch {
            insertDatabaseContacts(fetchContacts())
        }

        binding.recyclerInvite.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerInvite.adapter = inviteAdapter

        binding.iconThreeDots.setOnClickListener {
            SharedPref.putBoolean(PrefConstants.IS_USER_LOGGED_IN, false)
            FirebaseAuth.getInstance().signOut()
        }

        binding.iconLocation.setOnClickListener {
            openGoogleMaps()
        }
        binding.iconThreeDots.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_profile, ProfileFragment.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun fetchDatabaseContacts() {
        val database = MyFamilyDatabase.getDatabase(mContext)

        database.contactDao().getAllContacts().observe(viewLifecycleOwner) {
            listContacts.clear()
            listContacts.addAll(it)
            inviteAdapter.notifyDataSetChanged()
        }
    }

    private suspend fun insertDatabaseContacts(listContacts: ArrayList<ContactModel>) {
        val database = MyFamilyDatabase.getDatabase(mContext)
        database.contactDao().insertAll(listContacts)
    }

    @SuppressLint("Range")
    private fun fetchContacts(): ArrayList<ContactModel> {
        val cr = mContext.contentResolver
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        val listContacts: ArrayList<ContactModel> = ArrayList()

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhoneNumber > 0) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        ""
                    )

                    if (pCur != null && pCur.count > 0) {
                        while (pCur.moveToNext()) {
                            val phoneNum = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            listContacts.add(ContactModel(name, phoneNum))
                        }
                        pCur.close()
                    }
                }
            }
            cursor.close()
        }
        return listContacts
    }

    private fun openGoogleMaps() {
        val latitude = 31.2537567
        val longitude = 75.694175
        val label = "LPU"
        val uri = "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    private fun sendMessageToContact(contactNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$contactNumber")
        intent.putExtra("sms_body", "Please help me call police")
        startActivity(intent)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
