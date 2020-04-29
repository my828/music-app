package com.example.mylittleapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.mylittleapp.R
import kotlinx.android.synthetic.main.song_detail.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {
    private var song: Song? = null
    private var randomNum: Int = Random.nextInt(100, 1000)

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG = "song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {args ->
            song = args.getParcelable(SONG)
        }
        Log.i("now", "playing")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPlaysCount.text = "${randomNum.toString()} plays"
        updateSongViews()
    }

    fun updateSong(song: Song) {
        this.song = song
        updateSongViews()
    }

    private fun updateSongViews() {
        song?.let {
            ibImage.setImageResource(it.largeImageID)
            tvTitle.text = it.title
            tvArtist.text = it.artist
        }
    }
}