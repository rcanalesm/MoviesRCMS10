package com.ricardocanales.moviesrcm

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ricardocanales.moviesrcm.database.LocalDataBase
import com.ricardocanales.moviesrcm.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MovieSelectionListener {

    companion object{
        private var moviesList : ArrayList<Movie> = arrayListOf()
    }

    private var customAdapter: CustomAdapter? = null
    private var i: Int = 0

    var recentlyDeletedMovie : Movie? = null
    var recentlyDeletedMovieIndex : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        populateList()

        my_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        customAdapter = CustomAdapter(moviesList,this)

        getAllMovies()

        //assign adapter
        my_recycler_view.adapter = customAdapter

    }

    private fun populateList(){
        add_movie_button.setOnClickListener(){

            showDialog(getString(R.string.msg_add),DialogInterface.OnClickListener{_,_ ->
                i += 1
                when (i){
                    //Movies
                    1 -> insertMovie(Movie(nameMovie = getString(R.string.movie_bohemian),typeMovie = getString(R.string.movie_movie),resumeMovie = getString(R.string.det_bohemian),urlMovie = "https://www.imdb.com/title/tt1727824/",imageMovie = R.drawable.bohemian))
                    2 -> insertMovie(Movie(nameMovie = getString(R.string.movie_godzilla),typeMovie = getString(R.string.movie_movie),resumeMovie = getString(R.string.det_godzilla),urlMovie = "https://www.imdb.com/title/tt3741700/", imageMovie = R.drawable.godzilla))
                    3 -> insertMovie(Movie(nameMovie = getString(R.string.movie_it),typeMovie = getString(R.string.movie_movie),resumeMovie = getString(R.string.det_it),urlMovie = "https://www.imdb.com/title/tt7349950/",imageMovie = R.drawable.it))
                    4 -> insertMovie(Movie(nameMovie = getString(R.string.movie_fast),typeMovie = getString(R.string.movie_movie),resumeMovie = getString(R.string.det_fast),urlMovie = "https://www.imdb.com/title/tt6806448/",imageMovie = R.drawable.rapidos))
                    5 -> insertMovie(Movie(nameMovie = getString(R.string.movie_twoMeters),typeMovie = getString(R.string.movie_movie),resumeMovie = getString(R.string.det_twoMeters),urlMovie = "https://www.imdb.com/title/tt6472976/",imageMovie = R.drawable.adosmetros))
                    //Series
                    6 -> insertMovie(Movie(nameMovie = getString(R.string.movie_doctor), typeMovie = getString(R.string.movie_serie), resumeMovie = getString(R.string.det_doctor),urlMovie = "https://www.imdb.com/title/tt6470478/",imageMovie = R.drawable.doctor))
                    7 -> insertMovie(Movie(nameMovie = getString(R.string.movie_vikings), typeMovie = getString(R.string.movie_serie), resumeMovie = getString(R.string.det_vikings),urlMovie = "https://www.imdb.com/title/tt2306299/",imageMovie = R.drawable.vikingos))
                    8 -> insertMovie(Movie(nameMovie = getString(R.string.movie_3), typeMovie = getString(R.string.movie_serie), resumeMovie = getString(R.string.det_3),urlMovie = "https://www.imdb.com/title/tt4922804/",imageMovie = R.drawable.tres))
                    9 -> insertMovie(Movie(nameMovie = getString(R.string.movie_jurassic), typeMovie = getString(R.string.movie_serie), resumeMovie = getString(R.string.det_jurassic),urlMovie = "https://www.imdb.com/title/tt10436228/",imageMovie = R.drawable.jurasic))
                   10 -> insertMovie(Movie(nameMovie = getString(R.string.movie_elite), typeMovie = getString(R.string.movie_serie), resumeMovie = getString(R.string.det_elite),urlMovie = "https://www.imdb.com/title/tt7134908/",imageMovie = R.drawable.elite))
                }
                customAdapter!!.notifyDataSetChanged()
        })
        }
    }

    override fun onMovieSelected(movieIndex: Int) {
        val movieSelected = moviesList[movieIndex]
        val detailIntent = Intent(this,DetailActivity::class.java)

        detailIntent.putExtra("movieSelected",movieSelected)
        startActivity(detailIntent)
    }

    override fun onMovieSelectedForDelete(movieIndex: Int) {
        recentlyDeletedMovie = moviesList[movieIndex]
        recentlyDeletedMovieIndex = movieIndex
        this.showDialog(getString(R.string.msg_delete) + recentlyDeletedMovie!!.nameMovie.toString()+"?",DialogInterface.OnClickListener{_,_ ->
            deleteMovie(moviesList[movieIndex])
        })
    }

    fun showDialog(message : String, listener: DialogInterface.OnClickListener){
        val dialogFragment = CustomDialog(message, listener)
        dialogFragment.show(supportFragmentManager,"dialog")
    }

    private fun insertMovie(movie: Movie){
        GlobalScope.launch {
            LocalDataBase.getInstance(applicationContext).movieDao.insertMovie(movie)
            launch(Dispatchers.Main) {
                getAllMovies()
            }
        }
    }

    private fun deleteMovie(movie : Movie){
        GlobalScope.launch {
            LocalDataBase.getInstance(applicationContext).movieDao.deleteMovie(movie)
            launch(Dispatchers.Main) {
                getAllMovies()
            }
        }
    }

    private fun getAllMovies(){
        GlobalScope.launch {
            moviesList = LocalDataBase.getInstance(applicationContext).movieDao.getAllMovies().toMutableList() as ArrayList<Movie>
            launch(Dispatchers.Main) {
                customAdapter?.updateMovies(moviesList)
            }
        }
    }
}