package com.yara.kinoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yara.kinoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding

    private val filmsDataBase = listOf(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get RV
        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        filmsAdapter.addItems(filmsDataBase)
    }
}