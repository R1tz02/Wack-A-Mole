package com.project.wackamole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton

const val MOLE_COLOR = "com.project.wackamole.color"


class SettingsActivity : AppCompatActivity() {
    private var colorId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var yellowBtn = findViewById<RadioButton>(R.id.radio_yellow)
        var greenBtn = findViewById<RadioButton>(R.id.radio_green)
        var blueBtn = findViewById<RadioButton>(R.id.radio_blue)
        var redBtn = findViewById<RadioButton>(R.id.radio_red)
        val okBtn = findViewById<Button>(R.id.okBtn)

        yellowBtn.setOnClickListener(this::onColorSelected)
        redBtn.setOnClickListener(this::onColorSelected)
        greenBtn.setOnClickListener(this::onColorSelected)
        blueBtn.setOnClickListener(this::onColorSelected)
        okBtn.setOnClickListener(this::okClick)

    }

    private fun okClick(view: View?) {
        val dataIntent = Intent()
        dataIntent.putExtra(MOLE_COLOR, colorId)
        setResult(RESULT_OK, dataIntent)
        finish()
    }

    private fun onColorSelected(view: View) {
        colorId = when(view.id){
            R.id.radio_red -> R.color.red
            R.id.radio_green -> R.color.green
            R.id.radio_blue -> R.color.blue
            else -> R.color.yellow
        }
    }
}