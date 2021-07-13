package welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tournamenttool.R


class WelcomeFragment: Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_welcome_layout, container, false)
        val submitButton = rootView.findViewById<View>(R.id.submit_button) as Button
        val numberOfTeamsSpinner = rootView.findViewById<View>(R.id.number_of_teams_spinner) as Spinner
        var items = arrayOf( 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(requireActivity(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        numberOfTeamsSpinner.adapter = adapter

        val typeOfScheduleSpinner = rootView.findViewById<View>(R.id.type_of_schedule_spinner) as Spinner
        var types = arrayOf("Tournament Single Elimination")
        val typeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeOfScheduleSpinner.adapter = typeAdapter

        submitButton.setOnClickListener{
          var numberOfTeams =  numberOfTeamsSpinner.selectedItem as Int
            val action = WelcomeFragmentDirections.actionWelcomeToBrackets()
            action.numberOfTeams = numberOfTeams
            rootView.findNavController().navigate(action)
        }
        return rootView
    }
}