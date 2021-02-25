package com.ricardocanales.moviesrcm

interface MovieSelectionListener {
    fun onMovieSelected(movieIndex : Int)
    fun onMovieSelectedForDelete(studentIndex : Int)
}