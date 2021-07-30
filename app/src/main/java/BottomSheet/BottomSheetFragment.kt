package BottomSheet

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import bracket.Winner
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.BottomSheetLayoutBinding

class BottomSheetFragment(val team1: String, val team2: String) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetLayoutBinding
    private var handler: ((Winner) -> Unit)? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_layout,
            container,
            false
        )
        binding.firstTeam.text = team1
        binding.secondTeam.text = team2
        binding.firstTeam.setOnClickListener(){
            handler?.invoke(Winner.TOP)
            dismiss()
        }
        binding.secondTeam.setOnClickListener(){
            handler?.invoke(Winner.BOTTOM)
            dismiss()
        }

        return binding.root
    }

    fun update(handler: (Winner) -> Unit) {
        this.handler = handler
    }
}