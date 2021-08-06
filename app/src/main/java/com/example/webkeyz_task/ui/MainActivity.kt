package com.example.webkeyz_task.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.example.webkeyz_task.R
import com.example.webkeyz_task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListenerToNavGraph()

    }

    // make toolbar fit to the fragment on nav_graph
    private fun addListenerToNavGraph(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.f_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.listingFragment -> {
                    binding.toolBar.visibility = View.VISIBLE
                    binding.tvTitle.text = getString(R.string.news)
                    binding.ivBack.visibility = View.GONE
                    binding.ivShare.visibility = View.GONE
                }
                R.id.detailsFragment -> {
                    binding.toolBar.visibility = View.VISIBLE
                    binding.ivBack.visibility = View.VISIBLE
                    binding.ivShare.visibility = View.VISIBLE
                }
                R.id.imageFragment -> {
                    binding.toolBar.visibility = View.GONE
                }
            }
        }
    }


}