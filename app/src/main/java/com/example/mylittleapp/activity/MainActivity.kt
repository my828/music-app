package com.example.mylittleapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mylittleapp.R
import com.example.mylittleapp.fragment.SongListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val songListFragment = SongListFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()
    }
}
