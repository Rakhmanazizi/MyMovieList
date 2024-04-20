package com.example.mymovielist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovielist.database.entity.MovieModel
import com.example.mymovielist.databinding.ItemFavoriteMovieBinding
import com.example.mymovielist.ui.detail.DetailFavoriteActivity

class FavoriteAdapter() : RecyclerView
    .Adapter<FavoriteAdapter
    .ListViewHolder>() {
    private lateinit var binding: ItemFavoriteMovieBinding
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(movieModel: MovieModel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickListener) {
        this.mListener = onItemClickCallback
    }

    inner class ListViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieModel: MovieModel) {
            binding = ItemFavoriteMovieBinding.bind(itemView)
            with(binding) {
                Glide.with(itemView.context)
                    .load(movieModel.poster)
                    .into(imgMovie)
                tvMovieRelease.text = movieModel.tahunRilis.toString()
                tvMovieTitle.text = movieModel.judul
                tvMovieGenre.text = movieModel.genre
                tvMovieRate.text = movieModel.rating.toString()
            }
            itemView.setOnClickListener {
                mListener.onItemClicked(movieModel)
            }
//            binding.apply {
//                Glide.with(itemView)
//                    .load(movieModel.poster)
//                    .into(imgMovie)
//                tvMovieRelease.text = movieModel.tahunRilis.toString()
//                tvMovieTitle.text = movieModel.judul
//                tvMovieGenre.text = movieModel.genre
//                tvMovieRate.text = movieModel.rating.toString()
//            }
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailFavoriteActivity::class.java)
//                intent.putExtra("movie", movieModel)
//                itemView.context.startActivity(intent)
//            }
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