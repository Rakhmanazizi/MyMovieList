package com.example.mymovielist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovielist.model.MovieModel
import com.example.mymovielist.R

class MovieAdapter(
    private var movieList: ArrayList<MovieModel>
) : RecyclerView
.Adapter<MovieAdapter.MovieViewHolder>() {

//    deklarasikan variable mListener dengan menggunakan interface
    //    onItemClickListener
    private var mListener : onItemClickListener? = null

//    membuat interface onItemClickListener
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener : onItemClickListener) {
        mListener = clickListener
    }

//    menambahkan metode setSearchedMovie untuk melakukan fitur pencarian
    fun setSearchedMovie(listMovie : ArrayList<MovieModel>) {
////        movieList.clear()
//        movieList.addAll(listMovie)
        this.movieList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return MovieViewHolder(itemView, mListener!!)
    }

    class MovieViewHolder(itemView: View, clickListener : onItemClickListener) : RecyclerView
        .ViewHolder(itemView) {
        val tvMovieRelease : TextView = itemView.findViewById(R.id.tv_movie_release)
        val tvMovieTitle : TextView = itemView.findViewById(R.id.tv_movie_title)
        val tvMovieGenre : TextView = itemView.findViewById(R.id.tv_movie_genre)
        val tvMovieRate : TextView = itemView.findViewById(R.id.tv_movie_rate)
        val imgMoviePoster : ImageView = itemView.findViewById(R.id.img_movie)

        init {
            clickListener?.let {
                itemView.setOnClickListener {
                    clickListener.onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val currentMovie = movieList[position]
        holder.tvMovieRelease.text = currentMovie.tahunRilis.toString()
        holder.tvMovieTitle.text = currentMovie.judul
        holder.tvMovieGenre.text = currentMovie.genre
        holder.tvMovieRate.text = currentMovie.rating.toString()
        Glide.with(holder.itemView.context)
            .load(currentMovie.poster)
            .into(holder.imgMoviePoster)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}
