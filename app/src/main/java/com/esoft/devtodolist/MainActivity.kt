package com.esoft.devtodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.esoft.devtodolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.searchBar)
        supportActionBar!!.title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}