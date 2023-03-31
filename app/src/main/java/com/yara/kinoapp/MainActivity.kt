package com.yara.kinoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yara.kinoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    val filmsDataBase = listOf(
        Film(
            "Title 1",
            R.drawable.p1,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        ),
        Film(
            "Title 2",
            R.drawable.p2,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        ),
        Film(
            "Title 3",
            R.drawable.p3,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        ),
        Film(
            "Title 4",
            R.drawable.p4,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        ),
        Film(
            "Title 5",
            R.drawable.p5,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        ),
        Film(
            "Title 6",
            R.drawable.p6,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        ),
        Film(
            "Title 7",
            R.drawable.p7,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nisl felis, maximus vel tincidunt id, iaculis in massa. Pellentesque habitant."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        // get RV
        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    val bundle = Bundle()
                    bundle.putParcelable("film", film)
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)
    }

    private fun initNavigation() {
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}