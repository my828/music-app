package com.example.mylittleapp.fragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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
import kotlin.random.Random

class SongListFragment: Fragment() {
    private var songAdapter: SongListAdapter? = null

    private var allSongs: List<Song>? = null

    private var onSongClickListener: OnSongClickListener? = null

    companion object {
        const val ALL_SONGS = "all_songs"
        const val SHUFFLE = "shuffle"
        const val REMOVE = "remove"
        val TAG = SongListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            Log.i("now", "have instance state")
            with(savedInstanceState) {
                allSongs = getParcelableArrayList(ALL_SONGS)
                updateList()
            }
        } else {
            Log.i("now", "no instance state")

        }
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
        songAdapter?.onSongClickListener = {
            onSongClickListener?.onSongClicked(it)
        }

        songAdapter?.onLongClickListener = { song, pos ->
            updateList(REMOVE, pos)
            Toast.makeText(context, "you have deleted ${song.title} by ${song.artist}", Toast.LENGTH_SHORT).show()
        }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
////        Log.i("now", "activity created")
//        if (savedInstanceState != null) {
//            Log.i("list", "activity created")
//            with(savedInstanceState) {
//                allSongs = getParcelableArrayList(ALL_SONGS)
//            }
//        }
//    }

    fun shuffleList() {
        updateList(SHUFFLE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("list", "saved")
        outState?.run {
            putParcelableArrayList(ALL_SONGS, allSongs as java.util.ArrayList<out Parcelable>)
        }
        super.onSaveInstanceState(outState)

    }
    private fun updateList(type: String = "", pos: Int = -1) {
        var newSongs = createNewSongs()
        newSongs?.let {
            if (type == SHUFFLE) {
                it.shuffle()
            } else if (type == REMOVE) {
                it.removeAt(pos)
            }
            songAdapter?.updateSongList(it)
        }
        allSongs = newSongs
    }

    private fun createNewSongs(): MutableList<Song>? {
        var newSongs: MutableList<Song>? = null
        allSongs?.let {
            newSongs = it.toMutableList()
        }
        newSongs?.let {
            return it
        }
        return  newSongs
    }
}


interface OnSongClickListener {
    fun onSongClicked(song: Song)
}