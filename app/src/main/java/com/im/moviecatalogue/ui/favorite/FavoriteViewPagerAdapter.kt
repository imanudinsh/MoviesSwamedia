package com.im.moviecatalogue.ui.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.im.moviecatalogue.R

class FavoriteViewPagerAdapter(private val ctx: Context, fm: androidx.fragment.app.FragmentManager) : FragmentPagerAdapter(fm) {
    private var title: List<String> = listOf(ctx.resources.getString(R.string.menu_movie), ctx.resources.getString(R.string.menu_tv_show))

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return FavoriteMovieFragment()
            }
            1 -> {
                return FavoriteTVShowFragment()
            }
            else -> return FavoriteMovieFragment()
        }
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}  