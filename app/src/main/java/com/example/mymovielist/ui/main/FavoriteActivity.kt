package com.example.mymovielist.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudlocaldb.data.MovieRoomDatabase
import com.example.mymovielist.adapter.FavoriteAdapter
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.databinding.ActivityFavoriteBinding
import com.example.mymovielist.helper.FavoriteViewModelFactory
import com.example.mymovielist.repository.MovieRepository
import com.example.mymovielist.ui.detail.DetailFavoriteActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var ViewModel: FavoriteViewModel
    private lateinit var movieList: List<MovieModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMovieFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvMovieFavorite.setHasFixedSize(true)

        ViewModel = obtainViewModel(this@FavoriteActivity)
        ViewModel.getAll().observe(this) {movieList ->
            if(movieList != null) {
                showData(movieList)
            }
        }
    }

    private fun showData(movieList: List<MovieModel>) {
        binding.rvMovieFavorite.adapter = FavoriteAdapter().apply {
            setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MovieModel) {
                    val intent = Intent(this@FavoriteActivity, DetailFavoriteActivity::class.java)
                    intent.putExtra("extra_member", data)
                    startActivity(intent)
                }
            })
            differ.submitList(movieList.toMutableList())
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}
