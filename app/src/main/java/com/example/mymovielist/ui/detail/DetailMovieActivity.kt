package com.example.mymovielist.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.crudlocaldb.data.MovieRoomDatabase
import com.example.mymovielist.R
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.databinding.ActivityDetailMovieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    private var isFavorite: Boolean = false
    private lateinit var database: MovieRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        database = MovieRoomDatabase.getInstance(applicationContext)

        val movieTitle = intent.getStringExtra("movieTitle")
        val movieGenre = intent.getStringExtra("movieGenre")
        val movieRate = intent.getStringExtra("movieRate")
        val movieTahunRilis = intent.getStringExtra("movieTahunRilis")
        val movieDesc = intent.getStringExtra("movieDesc")
        val movieDirector = intent.getStringExtra("movieDirector")
        val moviePoster = intent.getStringExtra("moviePoster")

        // cek apakah item sudah disimpan ke favorite database
        GlobalScope.launch(Dispatchers.IO) {
            val existingMovie = database.movieDao().getMovieByTitle(movieTitle.toString())
            isFavorite = existingMovie != null
            runOnUiThread {
                updateFavoriteButtonState()
            }
        }

        binding.fabFavorite.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                if (isFavorite) {
                    // movie sudah di dalam favorite, hapus
                    database.movieDao().deleteByTitle(movieTitle.toString())
                    isFavorite = false
                    runOnUiThread {
                        updateFavoriteButtonState()
                        showToast("Hapus dari favorite")
                    }
                } else {
                    database.movieDao().insertAll(
                        MovieModel(
                            null,
                            movieDesc.toString(),
                            movieDirector.toString(),
                            movieGenre.toString(),
                            movieTitle.toString(),
                            moviePoster.toString(),
                            movieRate.toString(),
                            movieTahunRilis.toString()
                        )
                    )
                    isFavorite = true
                    runOnUiThread {
                        updateFavoriteButtonState()
                        showToast("Tambah ke favorite")
                    }
                }
            }
        }
        setValueToViews()
    }

    private fun updateFavoriteButtonState() {
        if (isFavorite) {
            binding.fabFavorite.setIconResource(R.drawable.ic_favorite_full)
        } else {
            binding.fabFavorite.setIconResource(R.drawable.ic_favorite_outline)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setValueToViews() {
        val moviePosterUrl = intent.getStringExtra("moviePoster")
        moviePosterUrl?.let {
            Glide.with(this).load(it).into(binding.imgPosterMovieDetail)
        }

        binding.tvMovieTitleDetail.text = intent.getStringExtra("movieTitle")
        binding.tvMovieGenreDetail.text = intent.getStringExtra("movieGenre")
        binding.tvRate.text = intent.getStringExtra("movieRate")
        binding.tvYear.text = intent.getStringExtra("movieTahunRilis")
        binding.tvDirector.text = intent.getStringExtra("movieDirector")
        binding.tvDesc.text = intent.getStringExtra("movieDesc")
    }

    private fun showToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }, 200)
    }
}