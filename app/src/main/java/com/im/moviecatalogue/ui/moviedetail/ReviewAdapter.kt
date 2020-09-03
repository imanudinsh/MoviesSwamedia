package com.im.moviecatalogue.ui.moviedetail

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.remote.response.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter(private val mData: List<Review>) : androidx.recyclerview.widget.RecyclerView.Adapter<ReviewAdapter.CardViewViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = mData[position]
        holder.itemView.tv_name.text = item.author
        holder.itemView.tv_content.text = item.content



    }

    inner class CardViewViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    }
}