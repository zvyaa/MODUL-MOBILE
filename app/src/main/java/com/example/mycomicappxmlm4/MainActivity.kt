package com.example.mycomicappxmlm4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycomicappxmlm4.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}