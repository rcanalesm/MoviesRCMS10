package com.ricardocanales.moviesrcm.dao

import androidx.room.*
import com.ricardocanales.moviesrcm.model.Movie

@Dao
interface MovieDataBaseDao {
    @Insert
    fun insertMovie(newMovie : Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM table_movies")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM table_movies WHERE movieId= :key")
    fun getMovie(key: Long): Movie?
}