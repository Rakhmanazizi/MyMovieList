package com.example.mymovielist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovielist.ui.detail.DetailMovieActivity
import com.example.mymovielist.model.MovieModel
import com.example.mymovielist.R
import com.example.mymovielist.ui.detail.FavoriteActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var rvMovie: RecyclerView
    private lateinit var tvMovieRelease: TextView
    private lateinit var tvMovieTitle: TextView
    private lateinit var tvMovieGenre: TextView
    private lateinit var tvMovieRate: TextView
    private lateinit var dbReference: DatabaseReference
    private lateinit var db: FirebaseDatabase
    private lateinit var mToolbar: MaterialToolbar
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: ArrayList<MovieModel>

    companion object {
        const val MOVIE_CHILD = "Film"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)

        // Inisialisasi rvMovie
        rvMovie = findViewById(R.id.rv_movie)
        rvMovie.layoutManager = LinearLayoutManager(this)
        rvMovie.setHasFixedSize(true)

        movieList = ArrayList()
        movieAdapter = MovieAdapter(movieList)
        rvMovie.adapter = movieAdapter

        getDataMovie()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)

        mToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    val menuItemSearch = menu?.findItem(R.id.action_search)
                    val searchView: SearchView = menuItemSearch?.actionView
                            as SearchView
                    searchView.queryHint = "Cari Film"
                    searchView.setOnQueryTextListener(object : SearchView
                    .OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            searchMovie(newText)
                            return true
                        }
                    })
                    true
                }

                R.id.action_favorite -> {
                    val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
        return true
//        return super.onCreateOptionsMenu(menu)
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
                movieAdapter.setSearchedMovie(searchedMovie as ArrayList<MovieModel>)
            }
        }
    }

    private fun getDataMovie() {
        dbReference = FirebaseDatabase.getInstance().getReference(MOVIE_CHILD)
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                movieList = ArrayList()
                if (snapshot.exists()) {
                    for (movieSnap in snapshot.children) {
                        val movieData =
                            movieSnap.getValue(MovieModel::class.java)
                        movieData?.let { movieList.add(it) }
                    }
                    val mAdapter = MovieAdapter(movieList)
                    rvMovie.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MovieAdapter
                    .onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(
                                this@MainActivity,
                                DetailMovieActivity::class.java
                            )

//                            put extras untuk mengirim data ke activity
                            //                            DetailMovieActivity
                            intent.putExtra(
                                "moviePoster",
                                movieList[position].poster
                            )
                            intent.putExtra(
                                "movieTitle", movieList[position]
                                    .judul
                            )
                            intent.putExtra(
                                "movieGenre", movieList[position]
                                    .genre
                            )
                            intent.putExtra(
                                "movieRate",
                                movieList[position].rating.toString()
                            )
                            intent.putExtra(
                                "movieTahunRilis",
                                movieList[position].tahunRilis.toString()
                            )
                            intent.putExtra(
                                "movieDirector",
                                movieList[position].director
                            )
                            intent.putExtra(
                                "movieDesc",
                                movieList[position].desc
                            )
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("${error.details} ${error.message}")
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }
}