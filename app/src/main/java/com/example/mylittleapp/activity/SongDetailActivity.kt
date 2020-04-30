package com.example.mylittleapp.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.ericchee.songdataprovider.Song
import com.example.mylittleapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.song_detail.*
import kotlin.random.Random

class SongDetailActivity : AppCompatActivity() {
    companion object {
        const val SONG = "SONG_INFO"
    }
    private var randomNum: Int = Random.nextInt(100, 1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_detail)

        // back button
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        val song = intent.getParcelableExtra<Song>(SONG)

        ibImage.setImageResource(song.largeImageID)
        tvTitle.text = song.title
        tvArtist.text = song.artist
        tvPlaysCount.text = "${randomNum.toString()} plays"

        ibImage.setOnLongClickListener { view: View? ->
            var color = Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            for (i in 0..clParent.childCount) {
                var child = clParent.getChildAt(i)
                if (child is AppCompatTextView) {
                    Log.i("child", "$child")
                    child.setTextColor(color)
                }
            }
            return@setOnLongClickListener true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun playButtonClicked(view: View) {
        this.randomNum++;
        tvPlaysCount.text = "${randomNum.toString()} plays"
    }

    fun nextButtonClicked(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }

    fun prevButtonClicked(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

//    fun changeUserClicked(view: View) {
////        Log.i("visibility", View.INVISIBLE.toString())
//        if (tvUser.visibility == View.VISIBLE) {
//            tvUser.visibility = View.INVISIBLE
//            etUser.visibility = View.VISIBLE
//            btnChange.text = "Apply"
//        } else {
//            if (etUser.text.isEmpty()) {
//                Toast.makeText(this, "User name cannot be empty", Toast.LENGTH_SHORT).show()
//                return
//            }
//            tvUser.apply {
//                text = etUser.text
//                visibility = View.VISIBLE
//            }
//            etUser.visibility = View.INVISIBLE
//            btnChange.text = "Change User"
//        }
//    }

}
