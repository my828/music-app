package com.example.mylittleapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.mylittleapp.R
import com.example.mylittleapp.SongListAdapter
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    private var allSongs = SongDataProvider.getAllSongs().toMutableList()
    private var currentSong: Song? = null
    private lateinit var songAdapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        val allSongs = SongDataProvider.getAllSongs()
        songAdapter = SongListAdapter(allSongs)

        // initialize recycler view
        rvSongs.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            adapter = songAdapter
        }
        // handle shuffle button click
        btnShuffle.setOnClickListener {
            songAdapter.shuffle()
        }

        // invoke single song click listener from songAdapter
        songAdapter.onSongClickListener = {
            tvDisplaySong.text ="${it.title} -- ${it.artist}"
            this.currentSong = it
        }

        songAdapter.onLongClickListener = { song, pos ->
            songAdapter.removeItem(pos)
            Toast.makeText(this, "you have deleted ${song.title} by ${song.artist}", Toast.LENGTH_SHORT).show()
        }

        tvDisplaySong.setOnClickListener {
            val intent = Intent(this, SongDetailActivity::class.java)
            intent.putExtra("SONG_INFO", this.currentSong)
            startActivity(intent)
        }
    }
}
