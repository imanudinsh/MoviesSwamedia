package com.im.moviecatalogue.ui.main


import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_main.*
import com.im.moviecatalogue.R
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.im.moviecatalogue.ui.favorite.FavoriteActivity
import kotlinx.android.synthetic.main.activity_main.toolbar


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        vp_main.adapter = ViewPagerAdapter(this, supportFragmentManager)
        vp_main.addOnPageChangeListener(viewPagerListener)
        tabs.setupWithViewPager(vp_main)
    }

    private val viewPagerListener = object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            if(position==0){
                val ivLogoAnimation = AnimatedVectorDrawableCompat.create(this@MainActivity, R.drawable.ic_tv_anim)
                iv_logo.setImageDrawable(ivLogoAnimation)
                ivLogoAnimation?.start()
            }else{
                val ivLogoAnimation = AnimatedVectorDrawableCompat.create(this@MainActivity, R.drawable.ic_roll_anim)
                iv_logo.setImageDrawable(ivLogoAnimation)
                ivLogoAnimation?.start()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pairTransition = Pair.create<View, String>(toolbar, "container")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairTransition)
        if(item.getItemId() == R.id.action_favorite){
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent, options.toBundle())
        }
        return super.onOptionsItemSelected(item)
    }


}
