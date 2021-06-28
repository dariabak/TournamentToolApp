package bracket

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tournamenttool.R

class BracketsView(context: Context): ConstraintLayout(context) {

   init {
       inflate(context, R.layout.bracket_layout, this)
   }

}