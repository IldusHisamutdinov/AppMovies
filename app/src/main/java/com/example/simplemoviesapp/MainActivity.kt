package com.example.simplemoviesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemoviesapp.adapters.MoviesAdapter
import com.example.simplemoviesapp.databinding.ActivityMainBinding
import com.example.simplemoviesapp.details.MovieDetailsActivity
import com.example.simplemoviesapp.models.RemoteMovieDetail
import com.example.simplemoviesapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), MoviesAdapter.OnItemClickListener {
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: GridLayoutManager

    private lateinit var mMainViewModel: MainViewModel

    private lateinit var mDataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mMainViewModel.getUpcomingMovies(::onError)

        mMainViewModel.movies.observe(this, Observer {
            upcomingMoviesAdapter.appendMovies(it)
            attachUpcomingMoviesOnScrollListener()
        })

        mDataBinding.apply {
            this.lifecycleOwner = this@MainActivity
        }

        initRecyclerView()
    }
    fun onClick() {
         startActivity(intent)
        val intent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(intent)
    }
    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initRecyclerView() {
        upcomingMoviesLayoutMgr = GridLayoutManager(
            this,2
        )
        mDataBinding.upcomingMovies.layoutManager = upcomingMoviesLayoutMgr

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
        mDataBinding.upcomingMovies.addItemDecoration(itemDecoration)
        upcomingMoviesAdapter = MoviesAdapter()
        mDataBinding.upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun attachUpcomingMoviesOnScrollListener() {
        mDataBinding.upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upcomingMoviesLayoutMgr.itemCount
                val visibleItemCount = upcomingMoviesLayoutMgr.childCount
                val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    mDataBinding.upcomingMovies.removeOnScrollListener(this)
                    mMainViewModel.getUpcomingMovies(::onError)
                }
            }
        })
    }

    override fun onItemClick(movie: RemoteMovieDetail, container: View) {
        onClick()
    }
}