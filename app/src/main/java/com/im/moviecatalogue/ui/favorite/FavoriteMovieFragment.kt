package com.im.moviecatalogue.ui.favorite

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
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
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper




class FavoriteMovieFragment : androidx.fragment.app.Fragment(){


    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var adapter: MovieViewAdapter


    private fun obtainViewModel(activity: FragmentActivity): MoviesViewModel {
        val factory = ViewModelFactory.getInstance(activity.getApplication())
        return ViewModelProviders.of(activity, factory).get(MoviesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_movie, container, false)

        adapter = MovieViewAdapter(activity!!, favListener = {movie, ivHeart, isFavorite ->
            val fav = FavoriteEntity(id= movie.id.toString(), title = movie.title, date = movie.releaseDate, rate = movie.rate, synopsis = movie.synopsis, poster = movie.poster, category = CategoryEnum.MOVIE.value)
            if(isFavorite){
                moviesViewModel.deleteFavorite(fav)
                ivHeart.setImageDrawable(context?.getDrawable(R.drawable.ic_heart))
                ivHeart.imageTintList = context?.getColorStateList(R.color.grey)
                adapter.removeFavorite(fav)
            }else{
                moviesViewModel.insertFavorite(fav)
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
                    moviesViewModel= obtainViewModel(activity = it)
                }
            }
        }
        moviesViewModel.allFavorite.observe(this, getFavorite)
        tv_no_data.text = resources.getString(R.string.no_movie_favorite)

        itemTouchHelper.attachToRecyclerView(rv_movie)
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
                adapter.setData(listMovie.sortedBy { it.title })

            }
            view_no_data.visibility = if(adapter.itemCount>0) View.GONE else View.VISIBLE
        }
    }

    private fun showRecyclerCardView() {
        rv_movie.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rv_movie.adapter = adapter
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return ItemTouchHelper.Callback.makeMovementFlags(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val favoriteEntity = adapter.listFavorites.get(swipedPosition)
                Log.d("Fav","$swipedPosition $favoriteEntity ")
                moviesViewModel.deleteFavorite(favoriteEntity)
                val snackbar = Snackbar.make(view!!, R.string.msg_favoirte_removed, Snackbar.LENGTH_LONG)
                snackbar.setAction(
                    R.string.btn_undo,
                    {  moviesViewModel.insertFavorite(favoriteEntity) })
                snackbar.show()
            }
        }
    })




}
