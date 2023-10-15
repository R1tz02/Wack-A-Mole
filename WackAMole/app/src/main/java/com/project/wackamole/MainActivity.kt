package com.project.wackamole

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.children
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var settingsButt: Button
    private lateinit var newGameButt: Button
    private lateinit var timeTxtView: TextView
    private lateinit var moleGridLayout: GridLayout
    private lateinit var game: WackAMole
    private lateinit var scoreTxtView: TextView
    private var timeRemaining:Long = 0
    private var moleColor: Int = 0
    private var moleColorId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingsButt = findViewById(R.id.settings_button)
        newGameButt = findViewById(R.id.new_game_button)
        timeTxtView = findViewById(R.id.timer)
        scoreTxtView = findViewById(R.id.score)
        moleGridLayout = findViewById(R.id.mole_grid)
        for(gridButton in moleGridLayout.children) {
            gridButton.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            gridButton.setOnClickListener(this::wackAMoleClick)
        }

        newGameButt.setOnClickListener(this::newGameBtnClick)
        settingsButt.setOnClickListener(this::settingsBtnClick)
        moleColor = ContextCompat.getColor(this, R.color.yellow)
        moleColorId = R.color.yellow
        game = WackAMole()

        if (savedInstanceState != null) {
            timeRemaining = savedInstanceState.getLong("CURRENT_TIME")
            game.setScore(savedInstanceState.getInt("GAME_SCORE"))
            game.state = savedInstanceState.getBooleanArray("GAME_STATE")!!
            timeTxtView.text = timeRemaining.toString()
            scoreTxtView.text = game.getScore().toString()
            moleColor = savedInstanceState.getInt("MOLE_COLOR")
            setMole()
            if(timeRemaining != 0L) {
                newGameButt.isClickable = false
                newGameButt.alpha = 0.5F
                setTime(timeRemaining)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("GAME_SCORE", game.getScore())
        outState.putLong("CURRENT_TIME", timeRemaining)
        outState.putBooleanArray("GAME_STATE", game.state)
        outState.putInt("MOLE_COLOR", moleColor)
    }

    private fun settingsBtnClick(view: View)  {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra(MOLE_COLOR, moleColorId)
        settingsResultLauncher.launch(intent)
    }

    private val settingsResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                moleColorId = result.data!!.getIntExtra(MOLE_COLOR, R.color.yellow)
                moleColor = ContextCompat.getColor(this, moleColorId)
                setMole()
            }
    }

    private fun newGameBtnClick(view: View) {
        game.newGame()
        setMole()
        timeRemaining = 30000
        setTime(timeRemaining)

        //found these properties by trial and error
        newGameButt.isClickable = false
        newGameButt.alpha = 0.5F
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
                gridButton.setBackgroundColor(moleColor)
                gridButton.contentDescription = this.getString(R.string.Mole)
            }
            else {
                gridButton.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                gridButton.contentDescription = this.getString(R.string.Hill)
            }
        }
    }

    private fun setTime(timeRemain: Long)
    {
        //Code from https://www.geeksforgeeks.org/countdowntimer-in-android-using-kotlin/

        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(timeRemain, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                timeTxtView.text = (millisUntilFinished / 1000).toString()
                timeRemaining = millisUntilFinished
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                game.gameOver()
                setMole()
                newGameButt.isClickable = true
                newGameButt.alpha = 1F
            }
        }.start()
    }

}