package com.yara.kinoapp.view.fragments

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yara.kinoapp.R
import com.yara.kinoapp.databinding.FragmentDetailsBinding
import com.yara.kinoapp.domain.Film
import com.yara.kinoapp.viewmodel.DetailsFragmentViewModel
import kotlinx.coroutines.*

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var film: Film
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DetailsFragmentViewModel::class.java)
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()

        // proceed clicking on 'Add to favorites' button
        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                film.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
            }
        }

        // proceed clicking on 'Share' button
        binding.detailsFabShare.setOnClickListener {
            // create intent
            val intent = Intent()
            // action to start intent
            intent.action = Intent.ACTION_SEND
            // put film's data into intent
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            // MIME type to run application with it
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share To:"))
        }

        // proceed clicking on 'Save to gallery' button
        binding.detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadOfPoster()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun setFilmsDetails() {
        // get film from Bundle
        film = arguments?.get("film") as Film

        binding.detailsToolbar.title = film.title
        // set poster
        Glide.with(this)
            .load(film.poster)
            .centerCrop()
            .into(binding.detailsPoster)
        binding.detailsDescription.text = film.description

        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_baseline_favorite_24
            else R.drawable.ic_baseline_favorite_border_24
        )
    }

    private fun performAsyncLoadOfPoster() {
        // create parent scope in Main Thread to proceed with UI
        MainScope().launch {
            // set progress bar on
            binding.progressBar.isVisible = true
            // create new job to get bitmap
            val job = scope.async {
                viewModel.loadWallpaper(film.poster)
            }
            // save to gallery after file was downloaded
            saveToGallery(job.await())
            // show snack bar with button
            Snackbar.make(
                binding.root,
                R.string.downloaded_to_gallery,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.open) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .show()

            // set progress bar off
            binding.progressBar.isVisible = false
        }
    }

    private fun saveToGallery(bitmap: Bitmap) {
        // check Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // create object to pass
            val contentValues = ContentValues().apply {
                // fill in file attributes
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    film.title.handleSingleQuote()
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.Images.Media.DATE_ADDED,
                    System.currentTimeMillis() / 1000
                )
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, RELATIVE_PATH)
            }
            // get ContentResolver to deal with persistent storage
            val contentResolver = requireActivity().contentResolver
            val uri = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            // open stream to write on disk
            val outputStream = contentResolver.openOutputStream(uri!!)
            // put poster with compression
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            // close stream
            outputStream?.close()
        } else {
            // for older Android versions
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver,
                bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    companion object {
        const val RELATIVE_PATH = "Pictures/KinoApp"
    }
}