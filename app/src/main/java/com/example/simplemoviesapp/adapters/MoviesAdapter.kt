package com.example.simplemoviesapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviesapp.databinding.ItemMovieBinding
import com.example.simplemoviesapp.details.MovieDetailsActivity
import com.example.simplemoviesapp.models.RemoteMovieDetail


class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(movie: RemoteMovieDetail, container: View)
    }
    private var onItemClickListener: OnItemClickListener? = null
    private var mMovies: MutableList<RemoteMovieDetail> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mMovies.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.mItemMovieBinding.movie = mMovies[position]
        val movie = mMovies[position]
        val id = movie.id.toString()
        holder.movieContainer.setOnClickListener {
            onItemClickListener?.onItemClick(mMovies[position], holder.ivMoviePoster)
            Log.d("MoviesAdapter", "id: ${id}")

                val intent = Intent(it.getContext(), MovieDetailsActivity::class.java)
                intent.putExtra("movieId", id)
                it.getContext().startActivity(intent)
        }
    }

    fun appendMovies(movies: List<RemoteMovieDetail>) {
        val oldSize = this.mMovies.size
        this.mMovies.addAll(movies)
        notifyItemRangeInserted(
            oldSize,
            movies.size
        )
    }

    inner class MovieViewHolder(binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var mItemMovieBinding: ItemMovieBinding = binding
        val movieContainer: CardView = binding.movieContainer
        val ivMoviePoster: ImageView = binding.itemMoviePoster
    }

    fun setOnMovieClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}