package com.example.mylittleapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
            currentSong?.let {
                title = "Dotify"
                bottomBar.visibility = View.INVISIBLE
                showNowPlayingFragment(it)
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    // handle back button
    override fun onNavigateUp(): Boolean {
        val emailFrag = getNowPlayingFragment()
        if (emailFrag != null) {
            Log.i("back", "back clicked!")
            supportFragmentManager
                .beginTransaction()
                .remove(emailFrag)
                .commit()
            return true
        }
//        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }
    private fun showNowPlayingFragment(song: Song) {
        var nowPlayingFrament = getNowPlayingFragment()

        if (nowPlayingFrament == null) {
            nowPlayingFrament = NowPlayingFragment()
            val bundle = Bundle().apply {
                putParcelable(NowPlayingFragment.SONG, song)
            }
            nowPlayingFrament.arguments = bundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFrament, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
                .commit()
        } else {
            nowPlayingFrament.updateSong(song)
        }
    }

    override fun onSongClicked(song: Song) {
        tvDisplaySong.text ="${song.title} -- ${song.artist}"
        currentSong = song
    }
}
