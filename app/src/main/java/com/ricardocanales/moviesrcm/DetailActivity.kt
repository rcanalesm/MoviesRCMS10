package com.ricardocanales.moviesrcm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.ricardocanales.moviesrcm.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movie = intent.getSerializableExtra("movieSelected") as Movie

        name_movie_detail_textView.text = movie.nameMovie
        type_movie_detail_textView.text = movie.typeMovie
        resume_movie_detail_textView.text = movie.resumeMovie
        image_movie_detail.background = getDrawable(movie.imageMovie)
        url_button_movie.setOnClickListener {
            openURL(movie.urlMovie)
        }
    }


    fun openURL(urlString: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(urlString)
        }
        //if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
       // }
    }
}

