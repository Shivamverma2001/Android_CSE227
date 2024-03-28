import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfamily.ContactModel
import com.example.myfamilyproject.databinding.ItemInviteBinding

class InviteAdapter(private val listContacts: List<ContactModel>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<InviteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = ItemInviteBinding.inflate(inflater, parent, false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listContacts[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item.number)
        }
    }

    override fun getItemCount(): Int {
        return listContacts.size
    }

    class ViewHolder(private val item: ItemInviteBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(contact: ContactModel) {
            item.name.text = contact.name
        }
    }
}
