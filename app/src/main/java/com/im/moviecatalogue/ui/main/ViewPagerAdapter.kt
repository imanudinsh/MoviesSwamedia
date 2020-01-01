package com.im.moviecatalogue.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.im.moviecatalogue.R

class ViewPagerAdapter(ctx: Context, fm: androidx.fragment.app.FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var title: List<String> = listOf(ctx.resources.getString(R.string.menu_movie), ctx.resources.getString(R.string.menu_tv_show))

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return MovieFragment()
            }
            1 -> {
                return TVShowFragment()
            }
            else -> return MovieFragment()
        }
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}  