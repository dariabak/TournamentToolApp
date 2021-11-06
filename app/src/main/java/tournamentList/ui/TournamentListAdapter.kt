package tournamentList.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.*
import com.example.tournamenttool.R
import androidx.fragment.app.Fragment
import tournamentList.business.Tournament


class TournamentListAdapter(private val dataSet: ArrayList<Tournament>, fragment: Fragment): RecyclerView.Adapter<TournamentListAdapter.ViewHolder>() {
    val fragment: Fragment = fragment
    var handler: ((String) -> Unit)? = null

    class ViewHolder(view: View, fragment: Fragment, handler: ((String) -> Unit)?) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val dateTextView: TextView
        val numberOfTeamsTextView: TextView
        var id: String

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tournament_text_view)
            dateTextView = view.findViewById(R.id.update_date_text_view)
            numberOfTeamsTextView = view.findViewById(R.id.number_of_teams_text_view)
            id = ""
            view.setOnClickListener{
                handler?.invoke(id)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tournament_layout, viewGroup, false)

        return ViewHolder(view, fragment, handler)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.textView.text = dataSet.get(position).name
        viewHolder.dateTextView.text = dataSet.get(position).update
        viewHolder.numberOfTeamsTextView.text = dataSet.get(position).numberOfTeams.toString()
        viewHolder.id = dataSet.get(position).id

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun setTapHandler(handler: (String) -> Unit){
        this.handler = handler
    }

}