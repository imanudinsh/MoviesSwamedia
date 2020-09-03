package com.im.moviecatalogue.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.im.moviecatalogue.R
import com.im.moviecatalogue.data.local.entity.GenreEntity
import kotlinx.android.synthetic.main.item_review.view.*

class GenreAdapter(private val mData: List<GenreEntity>, val selecItem: (Int, GenreEntity) -> Unit) : androidx.recyclerview.widget.RecyclerView.Adapter<GenreAdapter.CardViewViewHolder>() {


    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        context = view.context
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = mData[position]
        holder.itemView.tv_name.text = item.name
        if(item.selected) {
            holder.itemView.backgroundTintList = ContextCompat.getColorStateList(context, R.color.greenSoft)
            holder.itemView.tv_name.setTextColor(ContextCompat.getColor(context, R.color.green))
        }

        holder.itemView.setOnClickListener {
            selecItem(position, item)
        }





    }

    inner class CardViewViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {


    }
}