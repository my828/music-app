package com.example.mylittleapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.mylittleapp.R
import com.example.mylittleapp.fragment.NowPlayingFragment
import com.example.mylittleapp.fragment.OnSongClickListener
import com.example.mylittleapp.fragment.SongListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSongClickListener {
    private val allSongs: ArrayList<Song> = ArrayList(SongDataProvider.getAllSongs())

    private lateinit var songListFragment: SongListFragment

    private var currentSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "All Songs"

        // start/initialize song list fragment
        songListFragment = SongListFragment()
        val bundle = Bundle().apply {
            putParcelableArrayList(SongListFragment.ALL_SONGS, allSongs)
        }
        songListFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        // handle shuffle button click
        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
        }

        tvDisplaySong.setOnClickListener {

        }
    }

    fun showNowPlayingFragment(song: Song) {
        val nowPlayingFrament = NowPlayingFragment()
        val bundle = Bundle().apply {
            nowPlayingFrament.updateSong(song)
        }
        nowPlayingFrament.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, nowPlayingFrament)
            .commit()
    }
    override fun onSongClicked(song: Song) {
        tvDisplaySong.text ="${song.title} -- ${song.artist}"
        currentSong = song
    }
}
