package com.im.moviecatalogue.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.utils.values.CategoryEnum
import com.im.moviecatalogue.viewmodel.ViewModelFactory
import com.im.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class TVShowFragment : androidx.fragment.app.Fragment(){


    private lateinit  var viewModel: MoviesViewModel
    private lateinit var adapter: MovieViewAdapter


    private fun obtainViewModel(activity: FragmentActivity): MoviesViewModel {
        val factory = ViewModelFactory.getInstance(activity.getApplication())
        return ViewModelProviders.of(activity, factory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_tv_show, container, false)

        adapter = MovieViewAdapter(activity!!, CategoryEnum.TV.value, favListener = {movie, ivHeart, isFavorite ->
            val fav = FavoriteEntity(id= movie.id.toString(), title = movie.title, date = movie.releaseDate, rate = movie.rate, synopsis = movie.synopsis, poster = movie.poster, category = CategoryEnum.TV.value)
            if(isFavorite){
                viewModel.deleteFavorite(fav)
                ivHeart.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                ivHeart.imageTintList = context?.getColorStateList(R.color.grey)
                adapter.listFavorites.remove(fav)
            }else{
                viewModel.insertFavorite(fav)
                adapter.listFavorites.add(fav)
                context?.let {
                    val ivAnimation = AnimatedVectorDrawableCompat.create(it, R.drawable.ic_heart_anim)
                    ivHeart.setImageDrawable(ivAnimation)
                    ivAnimation?.start()
                }

            }
        })
        adapter.notifyDataSetChanged()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRecyclerCardView()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        runBlocking {
            launch {
                activity?.let {
                    viewModel= obtainViewModel(activity = it)
                }
            }
        }

        viewModel.movieId.value = "1"
        viewModel.tvShows.observe(this, Observer {
            if(it != null){
                when (it.status){
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        it.data?.let{ tvShows ->
                            val movies = mutableListOf<MovieEntity>()
                            tvShows.forEach {
                                movies.add(MovieEntity(id = it.id, rate = it.rate, poster = it.poster, synopsis = it.synopsis, title = it.title, releaseDate = it.releaseDate))

                            }
                            adapter.setData(movies)
                            adapter.notifyDataSetChanged()
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, "Connection failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        viewModel.allFavoriteTvShow.observe(this, Observer<List<FavoriteEntity>?> {
            if (it != null) {
                adapter.listFavorites.clear()
                adapter.listFavorites.addAll(it)
                adapter.notifyDataSetChanged()
            }

        })
    }


    private fun showRecyclerCardView() {
        rv_tvshow.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rv_tvshow.adapter = adapter
    }


}
