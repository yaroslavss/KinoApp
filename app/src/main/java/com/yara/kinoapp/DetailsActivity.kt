package com.yara.kinoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yara.kinoapp.databinding.ActivityDetailsBinding
import com.yara.kinoapp.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFilmsDetails()
    }

    private fun setFilmsDetails() {
        // get film from Bundle
        val film = intent.extras?.get("film") as Film

        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description
    }
}