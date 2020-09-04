package com.im.moviecatalogue.ui.main


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.GenreEntity
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.ui.login.LoginActivity
import com.im.moviecatalogue.viewmodel.ViewModelFactory
import com.im.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MovieViewAdapter
    private var moviesList = mutableListOf<MovieEntity>()
    private var genreList = mutableListOf<GenreEntity>()
    private lateinit var sharedPref: SharedPreferences

    companion object{
        private const val PRIVATE_MODE = 0
        private const val PREF_NAME = "MovieCatalogue"
        private const val IS_LOGIN = "IS_LOGIN"
    }

    private fun obtainViewModel(activity: FragmentActivity): MoviesViewModel {
        val factory = ViewModelFactory.getInstance(activity.getApplication())
        return ViewModelProviders.of(activity, factory).get(MoviesViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        if (!sharedPref.getBoolean(IS_LOGIN, false)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initRecyclerview()
        initViewModel()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_signout){
            sharedPref.edit().putBoolean(IS_LOGIN, false).apply()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerview(){
        adapter = MovieViewAdapter(this, moviesList)
        rv_movie.layoutManager = GridLayoutManager(this, 3)
        rv_movie.adapter = adapter
        rv_movie.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (progress_bar.visibility != View.VISIBLE) {
                    if ((rv_movie.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition() == moviesList.size - 1) {
                        val page = viewModel.page.value?.plus(1)
                        Log.d("MainActivity", "datanya page ke $page ${moviesList.size} ${moviesList.size/20}")
                        viewModel.page.value = page
                    }
                }
            }
        })

        rv_genre.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_genre.adapter = GenreAdapter(genreList, selecItem = { position, item ->
            genreList[position].selected =  !item.selected
            if(position==0){
                genreList[position].selected = true
                for(i in 1 .. genreList.size-1){
                    genreList[i].selected = false
                }
            }
            else if(genreList.any{ it.selected && it.id!=0 }){
                genreList[0].selected = false
            }
            rv_genre.adapter?.notifyDataSetChanged()

            var genre = ""
            genreList.filter { it.id!=0 && it.selected }.forEach {
                if(genre.isEmpty()) genre += it.id.toString()
                else genre += ",${it.id}"
            }
            Log.d("MainActivity","genre nich $genre")
            viewModel.genres.value = genre
            viewModel.page.value = 1

        })
    }
    private fun initViewModel(){
        viewModel= obtainViewModel(activity = this)
        viewModel.page.value = 1
        viewModel.genres.value = ""
        viewModel.movies.observe(this, Observer { it ->
            Log.d("MainActivity", "message ${it}")
            if(it != null){
                when (it.status){
                    Status.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        it.data?.let{ movies ->
                            if(viewModel.page.value == 1) moviesList.clear()
                            moviesList.addAll(
                                movies.filter { movie -> !moviesList.contains(movie) }
                            )
                            adapter.notifyDataSetChanged()
                        }
                        view_no_data.visibility = if(moviesList.isEmpty()) View.VISIBLE else View.GONE
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.allGenres.observe(this, Observer { it ->
            Log.d("MainActivity", "message ${it}")
            if(it != null){
                when (it.status){
                    Status.SUCCESS -> {
                        it.data?.let{genres ->
                            genreList.add(
                                GenreEntity(
                                    id = 0,
                                    name = "All",
                                    selected = true
                                )
                            )
                            genreList.addAll(genres)
                            rv_genre.adapter?.notifyDataSetChanged()
                        }

                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


}
