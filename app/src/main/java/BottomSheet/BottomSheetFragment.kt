package BottomSheet

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import bracket.BracketsFragment
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


        binding.firstTeam.setOnClickListener(){
            viewModel.setBracketId(args.bracketId)
            viewModel.setWinner(binding.firstTeam.text.toString())
            dismiss()
        }
        binding.secondTeam.setOnClickListener(){
            viewModel.setBracketId(args.bracketId)
            viewModel.setWinner(binding.secondTeam.text.toString())
            dismiss()
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BracketsViewModel::class.java)
    }
}