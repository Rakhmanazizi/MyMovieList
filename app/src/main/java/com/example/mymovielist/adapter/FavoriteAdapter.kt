package com.example.mymovielist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.mymovielist.R
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.databinding.ItemFavoriteMovieBinding
import com.example.mymovielist.databinding.ItemRowMovieBinding
import com.example.mymovielist.ui.detail.DetailFavoriteActivity
import com.example.mymovielist.ui.main.FavoriteActivity
import kotlinx.coroutines.withContext
import kotlin.math.sign

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    private lateinit var binding: ItemFavoriteMovieBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieModel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(movie.poster)
                    .into(imgMovie)
                tvMovieRelease.text = movie.tahunRilis.toString()
                tvMovieTitle.text = movie.judul
                tvMovieGenre.text = movie.genre
                tvMovieRate.text = movie.rating.toString()
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailFavoriteActivity::class.java)
                intent.putExtra("movie", movie)
                itemView.context.startActivity(intent)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }
}