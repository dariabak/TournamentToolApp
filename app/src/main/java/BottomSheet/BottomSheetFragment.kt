package BottomSheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.widget.Toast
import android.view.View
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.tournamenttool.R



class BottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.bottom_sheet_layout, container, false)
}