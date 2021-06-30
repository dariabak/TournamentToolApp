package title

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
        val spinner = rootView.findViewById<View>(R.id.number_of_teams_spinner) as Spinner
        var items = arrayOf(1, 2, 3, 4, 5, 6)
        val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(requireActivity(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        submitButton.setOnClickListener{
          val numberOfTeams =  spinner.selectedItem as Int
            rootView.findNavController().navigate(WelcomeFragmentDirections.actionWelcomeToBrackets(numberOfTeams))
        }
        return rootView
    }
}