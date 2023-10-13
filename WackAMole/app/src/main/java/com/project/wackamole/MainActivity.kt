package com.project.wackamole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var newGameButt: Button
    private lateinit var timeTxtView: TextView
    private lateinit var moleGridLayout: GridLayout
    private lateinit var game: WackAMole
    private lateinit var scoreTxtView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGameButt = findViewById(R.id.new_game_button)
        timeTxtView = findViewById(R.id.timer)
        scoreTxtView = findViewById(R.id.score)
        moleGridLayout = findViewById(R.id.mole_grid)
        for(gridButton in moleGridLayout.children) {
            gridButton.setOnClickListener(this::wackAMoleClick)
        }

        newGameButt.setOnClickListener(this::newGameBtnClick)
        game = WackAMole()
    }

    private fun newGameBtnClick(view: View) {
        game.newGame()
        setMole()

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
                setMole()
                val text = getString(R.string.gameOver, game.getScore())
                Toast.makeText(applicationContext , text, Toast.LENGTH_LONG).show()
            }
        }.start()

    }

    private fun wackAMoleClick(view: View){
        //taken from Lights Out Program
        val buttonIndex = moleGridLayout.indexOfChild(view)
        val row = buttonIndex / BOARD_SIZE
        val col = buttonIndex % BOARD_SIZE
        game.wackAMole(row, col)
        scoreTxtView.text = game.getScore().toString()
        setMole()
    }

    //Sets the color of the buttons
    private fun setMole(){
        for(buttonIndex in 0 until moleGridLayout.childCount){
            val gridButton = moleGridLayout.getChildAt(buttonIndex)
            val row = buttonIndex / BOARD_SIZE
            val col = buttonIndex % BOARD_SIZE
            if(game.molePresent(row, col)){
                gridButton.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                gridButton.contentDescription = this.getString(R.string.Mole)
            }
            else {
                gridButton.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                gridButton.contentDescription = this.getString(R.string.Hill)
            }
        }
    }

}