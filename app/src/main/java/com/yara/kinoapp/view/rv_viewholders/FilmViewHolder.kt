package com.yara.kinoapp.view.rv_viewholders

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.yara.kinoapp.databinding.FilmItemBinding
import com.yara.kinoapp.domain.Film


class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmItemBinding = FilmItemBinding.bind(itemView)

    private val title = filmItemBinding.title
    private val poster = filmItemBinding.poster
    private val description = filmItemBinding.description
    private val ratingDonut = filmItemBinding.ratingDonut

    fun bind(film: Film) {
        title.text = film.title
        poster.setImageResource(film.poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())

        // animating ratingDonut
        val animation = ObjectAnimator.ofInt(
            ratingDonut,
            "progress",
            0,
            (film.rating * 10).toInt()
        ) // see this max value coming back here, we animate towards that value

        animation.duration = 5000 // in milliseconds

        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }}