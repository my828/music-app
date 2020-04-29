package com.example.mylittleapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(listOfSongs: List<Song>):
    RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var listOfSongs: List<Song> = listOfSongs
    var onSongClickListener: ((song: Song) -> Unit)? = null
    var onLongClickListener: ((song: Song, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(itemView)
    }

    override fun getItemCount(): Int = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val personName = listOfSongs[position]
        holder.bindView(personName, position)
    }
    fun removeItem(newSongs: List<Song>) {
//        val newSongs = listOfSongs.toMutableList()
//        newSongs.removeAt(pos)
//
//        checkUpdate(newSongs)
        listOfSongs = newSongs

    }
    fun shuffle(newSongs: List<Song>) {
//        val newSongs = listOfSongs.toMutableList().apply {
//            shuffle()
//        }
//        checkUpdate(newSongs)
        listOfSongs = newSongs
    }

    fun checkUpdate(newSongs: List<Song>) {
        val callback = SongDiffCallBack(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)
        listOfSongs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val ivSongImage by lazy { itemView.findViewById<ImageView>(R.id.ivSongImage)}
        private val tvSongTitle by  lazy { itemView.findViewById<TextView>(R.id.tvSongTitle)}
        private val tvArtistName by lazy { itemView.findViewById<TextView>(R.id.tvArtistName)}


        fun bindView(song: Song, position: Int) {
            ivSongImage.setImageResource(song.smallImageID)
            tvSongTitle.text = song.title
            tvArtistName.text = song.artist

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
            itemView.setOnLongClickListener {
//                listOfSongs.toMutableList().removeAt(layoutPosition)
                onLongClickListener?.invoke(song, adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }
}