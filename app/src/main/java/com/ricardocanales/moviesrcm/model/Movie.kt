package com.ricardocanales.moviesrcm.model

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table_movies")
data class Movie(

    @ColumnInfo(name= "movie_name")
    val nameMovie: String,

    @ColumnInfo(name= "movie_type")
    val typeMovie: String,

    @ColumnInfo(name= "movie_resume")
    val resumeMovie: String,

    @ColumnInfo(name= "movie_url")
    val urlMovie: String,

    @ColumnInfo(name= "movie_image")
    val imageMovie: Int,

    @PrimaryKey(autoGenerate = true)
    val movieId: Long = 0L): Serializable