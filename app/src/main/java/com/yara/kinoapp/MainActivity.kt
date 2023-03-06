package com.yara.kinoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMenuButtons()
    }

    private fun initMenuButtons() {
        val button_menu = findViewById<Button>(R.id.button_menu)
        button_menu.setOnClickListener() {
            Toast.makeText(this, "Меню", Toast.LENGTH_SHORT).show()
        }

        val button_fav = findViewById<Button>(R.id.button_fav)
        button_fav.setOnClickListener() {
            Toast.makeText(this, button_fav.text, Toast.LENGTH_SHORT).show()
        }

        val button_watch_later = findViewById<Button>(R.id.button_watch_later)
        button_watch_later.setOnClickListener() {
            Toast.makeText(this, button_watch_later.text, Toast.LENGTH_SHORT).show()
        }

        val button_selections = findViewById<Button>(R.id.button_selections)
        button_selections.setOnClickListener() {
            Toast.makeText(this, button_selections.text, Toast.LENGTH_SHORT).show()
        }

        val button_settings = findViewById<Button>(R.id.button_settings)
        button_settings.setOnClickListener() {
            Toast.makeText(this, button_settings.text, Toast.LENGTH_SHORT).show()
        }

    }
}