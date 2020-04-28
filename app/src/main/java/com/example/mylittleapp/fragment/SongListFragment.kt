package com.example.mylittleapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.mylittleapp.R
import com.example.mylittleapp.SongDiffCallBack
import com.example.mylittleapp.SongListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment: Fragment() {
    private lateinit var songAdapter: SongListAdapter


    private var allSongs: List<Song>? = null

    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        const val ALL_SONGS = "all_songs"
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {args ->
            allSongs = args.getParcelableArrayList<Song>(ALL_SONGS) as ArrayList<Song>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allSongs?.let {
            songAdapter = SongListAdapter(it)
        }

        rvSongs.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            adapter = songAdapter
        }

        // invoke single song click listener from songAdapter
        songAdapter.onSongClickListener = {
//            tvDisplaySong.text ="${it.title} -- ${it.artist}"
//            this.currentSong = it
            onSongClickListener?.onSongClicked(it)
        }

        songAdapter.onLongClickListener = { song, pos ->
            songAdapter.removeItem(pos)
            Toast.makeText(context, "you have deleted ${song.title} by ${song.artist}", Toast.LENGTH_SHORT).show()
        }


//        tvDisplaySong.setOnClickListener {
//            val intent = Intent(this, SongDetailActivity::class.java)
//            intent.putExtra("SONG_INFO", this.currentSong)
//            startActivity(intent)
//        }
    }

    fun shuffleList() {
        val newSongs = allSongs!!.toMutableList().apply {
            shuffle()
        }
        checkUpdate(newSongs)
        Log.i("shuffle", "shuffled!!")
        songAdapter.shuffle(newSongs)
    }

    private fun checkUpdate(newSongs: List<Song>) {
        val callback = SongDiffCallBack(allSongs!!, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(songAdapter)
    }
}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}