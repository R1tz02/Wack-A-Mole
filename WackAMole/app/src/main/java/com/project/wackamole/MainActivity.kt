package com.project.wackamole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var newGameButt: Button
    private lateinit var timeTxtView: TextView
    private lateinit var game: WackAMole


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGameButt = findViewById(R.id.new_game_button)
        timeTxtView = findViewById(R.id.timer)

        newGameButt.setOnClickListener(this::newGameBtnClick)
        game = WackAMole()
    }

    private fun newGameBtnClick(view: View) {
        game.newGame()

        //Code from https://www.geeksforgeeks.org/countdowntimer-in-android-using-kotlin/

        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                timeTxtView.text = (millisUntilFinished / 1000).toString()
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                game.gameOver()
                val text = getString(R.string.gameOver, game.getScore())
                Toast.makeText(applicationContext , text, Toast.LENGTH_LONG).show()
            }
        }.start()

    }




}