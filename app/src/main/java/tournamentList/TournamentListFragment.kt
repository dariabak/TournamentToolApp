package tournamentList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.FragmentTournamentListLayoutBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import tournament.TournamentViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import bracket.Winner
import tournament.TournamentFragmentDirections

class TournamentListFragment: Fragment() {
    private lateinit var binding: FragmentTournamentListLayoutBinding
    private lateinit var viewModel: TournamentListViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: TournamentListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tournament_list_layout,
            container,
            false
        )
        setHasOptionsMenu(true)

        binding.addNewButton.setOnClickListener {
            this.findNavController().navigate(TournamentListFragmentDirections.actionTournamentListToTournament())
        }
        val service: TournamentListServiceInterface = TournamentListService(requireActivity())
        val repo: TournamentListRepoInterface = TournamentListRepo(service)
        viewModel = ViewModelProviders.of(this, TournamentListViewModelFactory(repo)).get(TournamentListViewModel::class.java)
        var tournamentsArrayList = viewModel.getTournamentsMap()

        linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.tournamentsList.layoutManager = linearLayoutManager
        adapter = TournamentListAdapter(tournamentsArrayList,this)
        binding.tournamentsList.adapter = adapter

        adapter.setTapHandler( { tournamentName ->
            navigateToBracketsFragment(tournamentName)
        })

        return binding.root
    }

    fun navigateToBracketsFragment(tournamentName: String) {
        Log.d("TournamentList", "ClickListener works $tournamentName")
        var action = TournamentListFragmentDirections.actionTournamentListFragmentToBracketsFragment()
        action.tournamentName = tournamentName
        findNavController().navigate(action)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

}