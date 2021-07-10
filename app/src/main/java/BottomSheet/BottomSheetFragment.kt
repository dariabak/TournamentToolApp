package BottomSheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.BottomSheetLayoutBinding


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding

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
            //this.findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetToBracketsFragment(binding.firstTeam.text.toString()))
        }
        binding.secondTeam.setOnClickListener(){
           // this.findNavController().navigate(BottomSheetFragmentDirections.actionBottomSheetToBracketsFragment(binding.secondTeam.text.toString()))
        }
        return binding.root
    }
}