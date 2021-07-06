package helpers

import android.graphics.*
import android.view.View
import android.content.Context
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.bracket_layout.view.*

class BracketLineView(context: Context): View(context) {
    private var paint = Paint()
     private var path = Path()

    init {
        paint.style = Paint.Style.STROKE
        paint.color = Color.RED
    }

   override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var width = this.width.toFloat()
        var height = this.height.toFloat()
        paint.strokeWidth = 5f
        path.moveTo(0f, 0f)
        path.lineTo(width/2f, 0f)
        path.lineTo(width/2f, height)
        path.lineTo(width, height)
        canvas.drawPath(path, paint)
    }

    fun setLayoutParams(leftMargin: Int, topMargin: Int, width: Int, height: Int) {
        val params = FrameLayout.LayoutParams(
            width,
            height
        )

        params.setMargins(leftMargin, topMargin, 0, 0)
        this.layoutParams = params
    }

}