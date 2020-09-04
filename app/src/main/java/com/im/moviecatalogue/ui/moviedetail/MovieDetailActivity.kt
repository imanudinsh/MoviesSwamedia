package com.im.moviecatalogue.ui.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.im.moviecatalogue.BuildConfig
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.data.remote.response.Review
import com.im.moviecatalogue.viewmodel.ViewModelFactory
import com.im.moviecatalogue.vo.Status
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.activity_detail.*


class MovieDetailActivity : AppCompatActivity(){


    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment
    private lateinit var youTubePlayer: YouTubePlayer
    private lateinit var adapter: ReviewAdapter
    private lateinit var movie: MovieEntity
    private var reviewList = mutableListOf<Review>()
    private lateinit var ivPoster: RoundedImageView


    companion object{
        const val EXTRA_DATA = "EXTRA_DATA"
    }


    private fun obtainViewModel(activity: AppCompatActivity): MovieDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(MovieDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text= "Detail Movie"

        movie = intent.getParcelableExtra<MovieEntity>(EXTRA_DATA)
        ivPoster = findViewById(R.id.iv_poster)
        ivPoster.z = 5f

        setData()
        initRecyclerview()
        initViewModel()

    }

    private fun setData(){
        tv_tittle.text = movie.title
        tv_year.text = movie.releaseDate
        tv_synopsis.text = movie.synopsis
        val rate = (movie.rate.toFloat()*10).toInt()
        tv_score.text = String.format("%s%%", rate)
        val score = rate/20f
        rb_score.rating = score
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w185${movie.poster}")
            .placeholder(R.drawable.img_placeholder)
            .into(ivPoster)

//        initializeYoutubePlayer(movie.trailer)
    }

    private fun initViewModel(){
        viewModel = obtainViewModel(this)
        viewModel.movieId.value = movie.id
        viewModel.page.value = 1
        viewModel.trailerMovie.observe(this, Observer{ resorce ->
            if (resorce != null) {
                Log.d("MovieDetailActivity",resorce.toString())

                when (resorce.status) {
                    Status.SUCCESS -> if (resorce.data != null) {
                        initializeYoutubePlayer(resorce.data)
                    }
                    Status.ERROR -> {
                        Toast.makeText(applicationContext, "Failed get trailer", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        })
        viewModel.reviewMovie.observe(this, Observer{ resource ->
            if (resource != null) {
                Log.d("MovieDetailActivity",resource.toString())

                when (resource.status) {
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        resource.data?.let{ review ->
                            reviewList.addAll(
                                review.filter { !reviewList.contains(it) }
                            )
                            adapter.notifyDataSetChanged()
                        }
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Failed get trailer", Toast.LENGTH_SHORT)
                            .show()
                    }
                    Status.LOADING ->{
                        progress_bar.visibility = View.VISIBLE
                    }
                }

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home ->{
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun initializeYoutubePlayer(trailerKey: String) {


        youTubePlayerFragment = supportFragmentManager.findFragmentById(R.id.vv_trailer) as YouTubePlayerSupportFragment
        with(youTubePlayerFragment) {
            initialize(BuildConfig.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider, player: YouTubePlayer,
                    wasRestored: Boolean
                ) {
                    if (!wasRestored) {
                        youTubePlayer = player

                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                        youTubePlayer.cueVideo(trailerKey)
                        youTubePlayer.setPlaybackEventListener(object : YouTubePlayer.PlaybackEventListener {
                            override fun onPlaying() {
                                ivPoster.z = 0f
                            }

                            override fun onPaused() {
                                ivPoster.z = 5f
                            }

                            override fun onStopped() {
                                ivPoster.z = 5f
                            }

                            override fun onBuffering(b: Boolean) {
                            }

                            override fun onSeekTo(i: Int) {
                            }
                        })
                    }
                    Log.e("DetailActivity", "Youtube Player View initialization success")

                }

                override fun onInitializationFailure(arg0: YouTubePlayer.Provider, arg1: YouTubeInitializationResult) {
                    Log.e("DetailActivity", "Youtube Player View initialization failed")

                }
            })
        }
    }

    private fun initRecyclerview(){
        adapter = ReviewAdapter(reviewList)
        rv_review.layoutManager = LinearLayoutManager(this)
        rv_review.adapter = adapter
        rv_review.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (progress_bar.visibility != View.VISIBLE) {
                    if ((rv_review.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == reviewList.size - 1) {
                        val page = viewModel.page.value?.plus(1)
                        Log.d("MainActivity", "datanya page ke $page ${reviewList.size} ${reviewList.size/20}")
                        viewModel.page.value = page
                    }
                }
            }
        })
    }


}
