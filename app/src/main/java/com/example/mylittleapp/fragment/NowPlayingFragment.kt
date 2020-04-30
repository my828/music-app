package com.example.mylittleapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ericchee.songdataprovider.Song
import com.example.mylittleapp.R
import kotlinx.android.synthetic.main.song_detail.*
import kotlin.random.Random

class NowPlayingFragment: Fragment() {
    private var song: Song? = null
    private var playCount: Int = Random.nextInt(100, 1000)

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val SONG = "song"
        const val PLAY_COUNT = "play_count"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
//            Log.i("now", "have instance state")
            with(savedInstanceState) {
                playCount = getInt(PLAY_COUNT)
            }
        } else {
            Log.i("now", "no  instance state")
            playCount = Random.nextInt(100, 1000)
        }
//        tvPlaysCount.text = "${playCount.toString()} plays"

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
        tvPlaysCount.text = "${playCount.toString()} plays"
        ibPlay.setOnClickListener {
            playCount++;
            tvPlaysCount.text = "${playCount.toString()} plays"
        }
        ibNext.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
        ibPrev.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
        updateSongViews()
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        Log.i("now", "activity created")
//        if (savedInstanceState != null) {
//            Log.i("now", "activity created")
//            with(savedInstanceState) {
//                playCount = getInt(PLAY_COUNT)
//            }
//        }
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("now", "saved")
        outState?.run {
            putInt(PLAY_COUNT, playCount)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        Log.i("now", "destroy")

        super.onDestroy()
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