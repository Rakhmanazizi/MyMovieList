package com.example.mymovielist.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.crudlocaldb.data.MovieRoomDatabase
import com.example.mymovielist.R
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.databinding.ActivityDetailFavoriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailFavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFavoriteBinding
    private lateinit var movieModel: MovieModel
    private var isFavorite: Boolean = false
    private lateinit var database: MovieRoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        val movieModelExtra = intent.getParcelableExtra<MovieModel>("movie")
        if (movieModelExtra is MovieModel) {
            movieModel = movieModelExtra
            setValueToViews()
        } else {
            finish()
        }

        database = MovieRoomDatabase.getInstance(applicationContext)

        binding.fabDelete.setOnClickListener {
            showConfirmationDeleteDialog()
        }
    }

    private fun showConfirmationDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus ${movieModel.judul?.capitalize()}")
        builder.setMessage("Apakah anda ingin mengahapus ${movieModel.judul}?")
        builder.setPositiveButton("Ya") { _, _ ->
            deleteItem()
        }
        builder.setNegativeButton("Tidak") { _, _ ->

        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteItem() {
        GlobalScope.launch(Dispatchers.IO) {
            database.movieDao().delete(movieModel)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setValueToViews() {
        Glide.with(this)
            .load(movieModel.poster)
            .into(binding.imgPosterMovieDetail)
        binding.tvMovieTitleDetail.text = movieModel.judul
        binding.tvMovieGenreDetail.text = movieModel.genre
        binding.tvRate.text = movieModel.rating.toString()
        binding.tvYear.text = movieModel.tahunRilis.toString()
        binding.tvDirector.text = movieModel.director
        binding.tvDesc.text = movieModel.desc
    }

    private fun showToast(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }, 200)
    }
}