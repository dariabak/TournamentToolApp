package tournamentList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.tournamenttool.R
import com.example.tournamenttool.databinding.TournamentLayoutBinding

class TournamentView(context: Context): ConstraintLayout(context) {
    private var binding: TournamentLayoutBinding

    init {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.tournament_layout,this , true)
    }
}