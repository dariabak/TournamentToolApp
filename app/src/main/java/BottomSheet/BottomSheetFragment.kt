package BottomSheet

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import bracket.BracketsViewModel
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.BottomSheetLayoutBinding


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    private lateinit var viewModel: BracketsViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_layout,
            container,
            false
        )
        val args = BottomSheetFragmentArgs.fromBundle(requireArguments())
        binding.firstTeam.text = args.team1
        binding.secondTeam.text = args.team2

        viewModel = ViewModelProviders.of(this).get(BracketsViewModel::class.java)
        binding.bracketsViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setBracketId(args.bracketId)

        binding.firstTeam.setOnClickListener(){
            viewModel.setWinner(binding.firstTeam.text.toString())
            dismiss()
        }
        binding.secondTeam.setOnClickListener(){
            viewModel.setWinner(binding.secondTeam.text.toString())
            dismiss()
        }

        return binding.root
    }
}