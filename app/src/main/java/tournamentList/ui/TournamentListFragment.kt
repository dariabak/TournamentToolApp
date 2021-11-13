package tournamentList.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.FragmentTournamentListLayoutBinding
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import tournamentList.business.TournamentListViewModel
import tournamentList.business.TournamentListViewModelFactory
import tournamentList.data.TournamentListRepo
import tournamentList.data.TournamentListRepoInterface
import tournamentList.data.TournamentListService
import tournamentList.data.TournamentListServiceInterface

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
        viewModel = ViewModelProviders.of(this, TournamentListViewModelFactory(repo)).get(
            TournamentListViewModel::class.java)
        var tournamentsArrayList = viewModel.getTournamentsMap()

        linearLayoutManager = LinearLayoutManager(requireActivity())
        binding.tournamentsList.layoutManager = linearLayoutManager
        adapter = TournamentListAdapter(tournamentsArrayList,this)
        binding.tournamentsList.adapter = adapter

        adapter.setTapHandler { tournamentId ->
            navigateToBracketsFragment(tournamentId)
        }
        (activity as? androidx.appcompat.app.AppCompatActivity)?.supportActionBar?.title = "Tournament Tool"
        return binding.root
    }

    private fun navigateToBracketsFragment(tournamentId: String) {

        var action =
            TournamentListFragmentDirections.actionTournamentListFragmentToBracketsFragment()
        action.tournamentId = tournamentId
        findNavController().navigate(action)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.privacy_policy -> {
                val openUrl = Intent(Intent.ACTION_VIEW)
                openUrl.data = Uri.parse("https://www.szafulski.com/tournamenttool/privacy-policy/")
                startActivity(openUrl)
                return true
            }
            R.id.terms_and_conditions -> {
                val openUrl = Intent(Intent.ACTION_VIEW)
                openUrl.data = Uri.parse("https://www.szafulski.com/tournamenttool/terms-and-conditions/")
                startActivity(openUrl)
                return true
            }
            R.id.acknowledgement -> {
                val intent = Intent(requireActivity(), OssLicensesMenuActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }

}