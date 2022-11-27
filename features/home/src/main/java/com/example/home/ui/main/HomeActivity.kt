package com.example.home.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.home.databinding.ActivityHomeBinding
import com.example.navigation.AppNavigationProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var appNavigationProvider: AppNavigationProvider

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.activityHomeChatExampleBtn.setOnClickListener {
            startActivity(appNavigationProvider.getChatExampleIntent(this))
        }
    }
}