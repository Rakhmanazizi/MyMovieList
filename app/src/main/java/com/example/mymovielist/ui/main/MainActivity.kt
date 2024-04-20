package com.example.mymovielist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovielist.ui.detail.DetailMovieActivity
import com.example.mymovielist.model.Movie
import com.example.mymovielist.R
import com.example.mymovielist.adapter.MovieAdapter
import com.example.mymovielist.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var dbReference: DatabaseReference
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: ArrayList<Movie>
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val MOVIE_CHILD = "Film"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        binding.rvMovie.setHasFixedSize(true)

        movieList = ArrayList()
        movieAdapter = MovieAdapter(this, movieList)
        movieAdapter.setOnItemClickListener(this)
        binding.rvMovie.adapter = movieAdapter

        val db = FirebaseDatabase.getInstance()
        dbReference = db.reference.child(MOVIE_CHILD)
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                movieList.clear()
                for (movieSnap in snapshot.children) {
                    movieSnap.getValue(Movie::class.java)
                        ?.let { movieModel ->
                            movieModel.judul = movieSnap.key.toString()
                            movieList.add(movieModel)
                        }
                }
                movieAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("${error.details} ${error.message}")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Cari Film"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchMovie(newText)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchMovie(newText: String?) {
        if (newText != null) {
            val searchedMovie = movieList.filter {
                it.judul?.contains(newText, true) == true || it.genre?.contains(
                    newText,
                    true
                ) == true
            }
            if (searchedMovie.isEmpty()) {
                showToast(getString(R.string.movie_empty))
            } else {
                movieAdapter.setSearchedMovie(searchedMovie as ArrayList<Movie>)
            }
        }
    }

    override fun onItemClick(movie: Movie?) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("movieTitle", movie?.judul)
        intent.putExtra("moviePoster", movie?.poster)
        intent.putExtra("movieGenre", movie?.genre)
        intent.putExtra("movieRate", movie?.rating.toString())
        intent.putExtra("movieDirector", movie?.director)
        intent.putExtra("movieTahunRilis", movie?.tahunRilis.toString())
        intent.putExtra("movieDesc", movie?.desc)
        startActivity(intent)
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
