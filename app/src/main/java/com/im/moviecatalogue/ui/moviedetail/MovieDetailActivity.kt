package com.im.moviecatalogue.ui.moviedetail

import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.im.moviecatalogue.BuildConfig
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.utils.EspressoIdlingResource
import com.im.moviecatalogue.utils.values.CategoryEnum
import com.im.moviecatalogue.viewmodel.ViewModelFactory
import com.im.moviecatalogue.vo.Status


class MovieDetailActivity : AppCompatActivity(){


    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment
    private lateinit var youTubePlayer: YouTubePlayer
    private lateinit var category: String
    private var isFavorite: Boolean = false


    companion object{
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
        const val EXTRA_DATA = "EXTRA_DATA"
    }


    private fun obtainViewModel(activity: AppCompatActivity): MovieDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.getApplication())
        return ViewModelProviders.of(activity, factory).get(MovieDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val item = intent.getParcelableExtra<MovieEntity>(EXTRA_DATA)
        val itemCategory = intent.getStringExtra(EXTRA_CATEGORY)

        category = if(itemCategory!=null && itemCategory == CategoryEnum.TV.value) CategoryEnum.TV.value else CategoryEnum.MOVIE.value
        toolbar_title.text= String.format("Detail %s", category)
        iv_poster.z = 5f

        setData(item)
        viewModel = obtainViewModel(this)
        viewModel.movieId.value = item.id
        viewModel.category.value = if(itemCategory!=null && itemCategory == CategoryEnum.TV.value) CategoryEnum.TV.api else CategoryEnum.MOVIE.api


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

        viewModel.setFavoriite(item.id, category)

        viewModel.isFavorite.observe(this, Observer<List<FavoriteEntity>> {
            if (it != null && it.isNotEmpty()) {
                isFavorite = true
                val ivAnimation = AnimatedVectorDrawableCompat.create(iv_heart.context, R.drawable.ic_heart_anim)
                iv_heart.setImageDrawable(ivAnimation)
                ivAnimation?.start()
                iv_heart.imageTintList = getColorStateList(R.color.red)
            }else{
                iv_heart.setImageDrawable(getDrawable(R.drawable.ic_heart))
                iv_heart.imageTintList = getColorStateList(R.color.grey)
                isFavorite = false
            }
            Log.d("moviedetailActivity","datanya diobserv $it")
        })


    }

    private fun setData(movie: MovieEntity){

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
            .into(iv_poster)


        btn_favorite.setOnClickListener {
            val fav = FavoriteEntity(id= movie.id.toString(), title = movie.title, date = movie.releaseDate, rate = movie.rate, synopsis = movie.synopsis, poster = movie.poster, category = category)
            if(isFavorite){
                viewModel.deleteFavorite(fav)
            }else{
                viewModel.insertFavorite(fav)
            }
        }
//        initializeYoutubePlayer(movie.trailer)
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


        EspressoIdlingResource.increment()
        youTubePlayerFragment = supportFragmentManager.findFragmentById(R.id.vv_trailer) as YouTubePlayerSupportFragment
        youTubePlayerFragment.initialize(BuildConfig.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

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
                            iv_poster.z = 0f
                        }

                        override fun onPaused() {
                            iv_poster.z = 5f
                        }

                        override fun onStopped() {
                            iv_poster.z = 5f
                        }

                        override fun onBuffering(b: Boolean) {
                        }

                        override fun onSeekTo(i: Int) {
                        }
                    })
                }
                Log.e("DetailActivity", "Youtube Player View initialization success")
                if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow()) {
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onInitializationFailure(arg0: YouTubePlayer.Provider, arg1: YouTubeInitializationResult) {
                Log.e("DetailActivity", "Youtube Player View initialization failed")
                if (!EspressoIdlingResource.espressoIdlingResource.isIdleNow()) {
                    EspressoIdlingResource.decrement()
                }
            }
        })
    }


}
