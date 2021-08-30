package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidFilter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
/*
        viewModel.asteroidsTest = listOf(
            Asteroid(100, "codename1", "date1", 0.1, 0.1, 0.1, 0.1, true),
            Asteroid(200, "codename2", "date2", 0.1, 0.1, 0.1, 0.1, false),
            Asteroid(300, "codename3", "date3", 0.1, 0.1, 0.1, 0.1, true),
            Asteroid(400, "codename4", "date4", 0.1, 0.1, 0.1, 0.1, true),
            Asteroid(500, "codename5", "date5", 0.1, 0.1, 0.1, 0.1, false),
            Asteroid(600, "codename6", "date6", 0.1, 0.1, 0.1, 0.1, true)

        )*/


        binding.asteroidRecycler.adapter = AsteroidGridAdapter { asteroid ->
            Log.i("Im here", "im here im asteroid ${asteroid.id}")
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_week_menu -> AsteroidFilter.WEEK
                R.id.show_today_menu -> AsteroidFilter.DAY
                else -> AsteroidFilter.ALL
            }
        )
        return true
    }
}
