package com.example.mymovielist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovielist.model.Movie
import com.example.mymovielist.databinding.ItemRowMovieBinding

class MovieAdapter(
    private val context: Context, private var movieList: ArrayList<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(movie: Movie?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MovieViewHolder {
        val binding = ItemRowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder, position: Int
    ) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(private val binding: ItemRowMovieBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(movie: Movie?) {
            binding.tvMovieRelease.text = movie?.tahunRilis.toString()
            binding.tvMovieTitle.text = movie?.judul
            binding.tvMovieGenre.text = movie?.genre
            binding.tvMovieRate.text = movie?.rating.toString()
            Glide.with(context).load(movie?.poster).into(binding.imgMovie)
        }

        override fun onClick(v: View?) {
            mListener?.onItemClick(movieList[adapterPosition])
        }
    }

    fun setSearchedMovie(listMovie: ArrayList<Movie>) {
        this.movieList = listMovie
        notifyDataSetChanged()
    }
}
