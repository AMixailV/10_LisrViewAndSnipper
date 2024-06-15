package ru.mixail_akulov.a10_lisrviewandsnipper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mixail_akulov.a10_lisrviewandsnipper.baseadapter.BaseAdapterActivity
import ru.mixail_akulov.a10_lisrviewandsnipper.baseadapter.SpinnerAdapterActivity
import ru.mixail_akulov.a10_lisrviewandsnipper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.simpleAdapterActivity.setOnClickListener {
            val intent = Intent(this, SimpleAdapterActivity::class.java)
            startActivity(intent)
        }

        binding.arrayAdapterActivity.setOnClickListener {
            val intent = Intent(this, ArrayAdapterActivity::class.java)
            startActivity(intent)
        }

        binding.baseAdapterActivity.setOnClickListener {
            val intent = Intent(this, BaseAdapterActivity::class.java)
            startActivity(intent)
        }

        binding.spinnerAdapterActivity.setOnClickListener {
            val intent = Intent(this, SpinnerAdapterActivity::class.java)
            startActivity(intent)
        }
    }

}