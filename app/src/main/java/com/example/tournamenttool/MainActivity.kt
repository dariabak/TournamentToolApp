package com.example.tournamenttool


import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import bracket.BracketsView
import kotlinx.android.synthetic.main.bracket_layout.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var submit_button: Button
    private lateinit var  layout: ViewGroup
    private var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        submit_button = findViewById(R.id.submit)
        layout = findViewById<View>(R.id.brackets_layout) as ViewGroup


        createBrackets("111111", 0)
        createBrackets("222222", 1)

        createBrackets("333333", 2)

    }
    fun createBrackets(text: String, i: Int) {
        val bracket = BracketsView(this)

        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0,0 + 200*i , 0, 0)
        bracket.layoutParams = params
        bracket.team1.text = text
        bracket.team2.text = text
        layout.addView(bracket)

    }
}