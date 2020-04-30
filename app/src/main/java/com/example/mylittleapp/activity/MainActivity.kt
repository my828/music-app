package com.example.mylittleapp.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.mylittleapp.R
import com.example.mylittleapp.fragment.NowPlayingFragment
import com.example.mylittleapp.fragment.OnSongClickListener
import com.example.mylittleapp.fragment.SongListFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.security.acl.NotOwnerException

class MainActivity : AppCompatActivity(), OnSongClickListener {
    private val allSongs: ArrayList<Song> = ArrayList(SongDataProvider.getAllSongs())

    private var songListFragment: SongListFragment? = null

    private var currentSong: Song? = null

    private var nowPlayingFrag: NowPlayingFragment? = null

    companion object {
        const val ALL_SONGS = "All Songs"
        const val DOTIFY = "Dotify"
        const val CURRENT_SONG = "current_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            Log.i("main", "have instance state")
            with(savedInstanceState) {
                currentSong = getParcelable<Song>(CURRENT_SONG)
                currentSong?.let {
                    tvDisplaySong.text ="${currentSong?.title} -- ${currentSong?.artist}"

                }
            }
        }
        if (songListFragment == null) {
            Log.i("main", "songListFrag is null")
        }

        if (supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null ) {
            songListFragment = SongListFragment()

            // start/initialize song list fragment
            val bundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.ALL_SONGS, allSongs)
            }
            songListFragment?.let {
                Log.i("main", "add song list")
                it.arguments = bundle
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragContainer, it, SongListFragment.TAG)
                    .addToBackStack(SongListFragment.TAG)
                    .commit()
            }
        }
        if (songListFragment == null) {
            songListFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
        }
        if (supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) == null) {
            title = DOTIFY
            bottomBar.visibility = View.VISIBLE

        } else {
//            Log.i("main", "get savedInstanceState ${savedInstanceState.toString()}")
            title = DOTIFY
            bottomBar.visibility = View.INVISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        // handle shuffle button click
        btnShuffle.setOnClickListener {
            songListFragment?.shuffleList()
        }

        // handle mini player click
        tvDisplaySong.setOnClickListener {
            currentSong?.let {
                title = DOTIFY
                bottomBar.visibility = View.INVISIBLE
                showNowPlayingFragment(it)
            }
        }

        // listener for fragment manager back stack changes
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 1) {
                Log.i("main", "${supportFragmentManager.backStackEntryCount}")

                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                Log.i("main", "backstack has one ${supportFragmentManager.backStackEntryCount}")
                if (nowPlayingFrag == null) {
                    Log.i("main", "nowPlayingFrag is null")

                    bottomBar.visibility = View.VISIBLE
                    title = ALL_SONGS
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }

        Log.i("main", "created")
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    // handle back button
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("main", "saved")
        outState?.run {
            putParcelable(CURRENT_SONG, currentSong)
        }
        super.onSaveInstanceState(outState)
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

