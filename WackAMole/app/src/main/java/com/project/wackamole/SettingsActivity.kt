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

        colorId = intent.getIntExtra(MOLE_COLOR, R.color.yellow)

        val radioId = when (colorId) {
            R.color.red -> R.id.radio_red
            R.color.blue -> R.id.radio_blue
            R.color.green -> R.id.radio_green
            else -> R.id.radio_yellow
        }

        val radioButton = findViewById<RadioButton>(radioId)
        radioButton.isChecked = true

        val yellowBtn = findViewById<RadioButton>(R.id.radio_yellow)
        val greenBtn = findViewById<RadioButton>(R.id.radio_green)
        val blueBtn = findViewById<RadioButton>(R.id.radio_blue)
        val redBtn = findViewById<RadioButton>(R.id.radio_red)
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