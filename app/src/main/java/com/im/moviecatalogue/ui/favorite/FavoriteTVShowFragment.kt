package com.im.moviecatalogue.ui.favorite

import android.appwidget.AppWidgetManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.FavoriteEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.ui.main.MovieViewAdapter
import com.im.moviecatalogue.ui.main.MoviesViewModel
import com.im.moviecatalogue.utils.values.CategoryEnum
import com.im.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class FavoriteTVShowFragment : androidx.fragment.app.Fragment(){


    private lateinit  var tvShowsViewModel: MoviesViewModel
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

        adapter = MovieViewAdapter(activity!!, favListener = {movie, ivHeart, isFavorite ->
            val fav = FavoriteEntity(id= movie.id.toString(), title = movie.title, date = movie.releaseDate, rate = movie.rate, synopsis = movie.synopsis, poster = movie.poster, category = CategoryEnum.TV.value)
            if(isFavorite){
                tvShowsViewModel.deleteFavorite(fav)
                ivHeart.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                ivHeart.imageTintList = context?.getColorStateList(R.color.grey)
                adapter.removeFavorite(fav)
            }else{
                tvShowsViewModel.insertFavorite(fav)
                adapter.addFavorite(fav)
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

        runBlocking {
            launch {
                activity?.let {
                    tvShowsViewModel= obtainViewModel(activity = it)
                }
            }
        }
        tvShowsViewModel.allFavoriteTvShow.observe(this, getFavorite)

        tv_no_data.text = resources.getString(R.string.no_tv_show_favorite)
    }


    private val getFavorite = object : Observer<List<FavoriteEntity>?> {
        override fun onChanged(listFav: List<FavoriteEntity>?) {
            if (listFav != null) {
                adapter.listFavorites.clear()
                adapter.listFavorites.addAll(listFav)
                adapter.notifyDataSetChanged()
                val listMovie: MutableList<MovieEntity> = mutableListOf()
                listFav.forEach {
                    listMovie.add(MovieEntity(id= it.id, title = it.title, releaseDate = it.date, rate = it.rate, synopsis = it.synopsis, poster = it.poster))
                }
                adapter.setData(listMovie)
            }
            view_no_data.visibility = if(adapter.itemCount>0) View.GONE else View.VISIBLE


        }
    }


    private fun showRecyclerCardView() {
        rv_tvshow.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rv_tvshow.adapter = adapter
    }


}
