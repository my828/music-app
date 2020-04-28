package com.example.mylittleapp.fragment

import android.os.Bundle
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
        const val SONG = "song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {args ->
            song = args.getParcelable<Song>(SONG)
        }
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
        song?.let {
            ibImage.setImageResource(it.largeImageID)
            tvTitle.text = it.title
            tvArtist.text = it.artist
            tvPlaysCount.text = "${randomNum.toString()} plays"
        }
    }

    fun updateSong(song: Song) {
        this.song = song
    }
}