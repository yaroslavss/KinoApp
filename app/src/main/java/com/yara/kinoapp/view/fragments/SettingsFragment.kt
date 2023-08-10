package com.yara.kinoapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yara.kinoapp.databinding.FragmentSettingsBinding
import com.yara.kinoapp.utils.AnimationHelper
import com.yara.kinoapp.viewmodel.SettingsFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.settingsFragmentRoot,
            requireActivity(),
            5
        )

        viewModel.searchStringLiveData.observe(viewLifecycleOwner) {
            binding.searchString.setText(it)
        }

        // proceed clicking on 'Clear DB' button
        binding.clearDbButton.setOnClickListener {
            scope.launch {
                viewModel.clearDB()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    override fun onPause() {
        super.onPause()
        viewModel.putSearchString(binding.searchString.text.toString())
    }
}