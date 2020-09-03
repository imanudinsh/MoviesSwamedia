package com.im.moviecatalogue.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.MovieEntity
import com.im.moviecatalogue.ui.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.item_row.view.*
import androidx.core.util.Pair as UtilPair

class MovieViewAdapter(private val activity: Activity, private val mData: List<MovieEntity>) : androidx.recyclerview.widget.RecyclerView.Adapter<MovieViewAdapter.CardViewViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = mData[position]
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w185${item.poster}")
            .placeholder(R.drawable.img_placeholder)
            .into(holder.itemView.img_illustartion)
        holder.itemView.tv_tittle.text = item.title
        val rate = (item.rate.toFloat()*10).toInt()
        holder.itemView.tv_score.text = String.format("%s%%", rate)
        holder.itemView.rb_score.rating = rate/20f

        holder.itemView.cardView.setOnClickListener {

            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_DATA, item)
            it.context.startActivity(intent)
        }



    }

    inner class CardViewViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    }
}