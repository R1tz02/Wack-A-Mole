package com.project.wackamole

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        var yellowBtn = findViewById<RadioButton>(R.id.radio_yellow)
        var greenBtn = findViewById<RadioButton>(R.id.radio_green)
        var blueBtn = findViewById<RadioButton>(R.id.radio_blue)
        var redBtn = findViewById<RadioButton>(R.id.radio_red)

    }
}