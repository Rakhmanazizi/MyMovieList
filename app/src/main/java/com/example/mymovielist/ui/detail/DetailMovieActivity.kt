package com.example.mymovielist.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.bumptech.glide.Glide
import com.example.mymovielist.R
import com.example.mymovielist.databinding.ActivityDetailMovieBinding

class DetailMovieActivity : AppCompatActivity() {
    //    deklarasikan variable binding untuk menggunakan binding
    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        var isFavorite = false
        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            binding.fabFavorite.setIconResource(
                if (isFavorite) {
                    R.drawable.ic_favorite_full
                } else {
                    R.drawable.ic_favorite_outline
                }
            )
        }
        setValueToViews()
    }

    private fun removeFromFavorite(): Int {
        return 0
    }

    private fun addToFavorite(): Int {
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setValueToViews() {
        val moviePosterUrl = intent.getStringExtra("moviePoster")
        Glide.with(this)
            .load(moviePosterUrl)
            .into(binding.imgPosterMovieDetail)

        binding.tvMovieTitleDetail.text = intent.getStringExtra("movieTitle")
        binding.tvMovieGenreDetail.text = intent.getStringExtra("movieGenre")
        binding.tvRate.text = intent.getStringExtra("movieRate")
        binding.tvYear.text = intent.getStringExtra("movieTahunRilis")
        binding.tvDirector.text = intent.getStringExtra("movieDirector")
        binding.tvDesc.text = intent.getStringExtra("movieDesc")
    }
}