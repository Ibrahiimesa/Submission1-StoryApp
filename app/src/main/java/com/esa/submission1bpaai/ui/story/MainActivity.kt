package com.esa.submission1bpaai.ui.story

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.esa.submission1bpaai.databinding.ActivityMainBinding
import com.esa.submission1bpaai.ui.adapter.LoadingStateAdapter
import com.esa.submission1bpaai.ui.user.UserViewModel
import com.esa.submission1bpaai.ui.adapter.StoryAdapter
import com.esa.submission1bpaai.ui.maps.MapsActivity
import com.esa.submission1bpaai.ui.user.LoginActivity
import com.esa.submission1bpaai.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        setupView()
        onClick()
    }

    private fun onClick() {
        val menu = binding.fabMenu
        binding.fabLogOut.setOnClickListener {
            userViewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.fabAddStory.setOnClickListener{
            startActivity(Intent(this, AddStoryActivity::class.java))
            menu.close(false)
        }
        binding.fabSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            menu.close(false)
        }
        binding.fabMaps.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
            menu.close(false)
        }
    }

    private fun setupView() {
        storyAdapter = StoryAdapter()

        mainViewModel.getUser().observe(this@MainActivity){ user ->
            if (user.isLogin){
                setStory()
            }
            else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        with(binding.rvStory) {
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    storyAdapter.retry()
                })
        }
    }

    private fun setStory() {
        mainViewModel.getStory().observe(this@MainActivity) {
            storyAdapter.submitData(lifecycle, it)
            showLoad(false)
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)

        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        userViewModel= ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun showLoad(isLoad: Boolean) {
        if (isLoad){
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }
}