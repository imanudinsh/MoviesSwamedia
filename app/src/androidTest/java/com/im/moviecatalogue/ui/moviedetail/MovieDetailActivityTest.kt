package com.im.moviecatalogue.ui.moviedetail

import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.im.moviecatalogue.R
import com.im.moviecatalogue.utils.FakeDataDummy2
import com.im.moviecatalogue.utils.values.CategoryEnum
import com.im.moviecatalogue.utils.values.TestTools
import org.junit.Test


class MovieDetailActivityTest{

    private val dummyMovie = FakeDataDummy2().generateDummyMovies().get(0)

    @get:Rule
    var activityRule: ActivityTestRule<MovieDetailActivity> =
        object : ActivityTestRule<MovieDetailActivity>(MovieDetailActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext()
                val result = Intent(targetContext, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailActivity.EXTRA_DATA, dummyMovie)
                    putExtra(MovieDetailActivity.EXTRA_CATEGORY, CategoryEnum.MOVIE.api)
                }
                return result
            }
        }


    @Test
    fun loadMovie(){
        onView(withId(R.id.tv_tittle)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(dummyMovie.title)))
        }
        onView(withId(R.id.tv_year)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(dummyMovie.releaseDate)))
        }
        onView(withId(R.id.tv_score)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(String.format("%s%%",(dummyMovie.rate.toFloat()*10).toInt()))))
        }
        onView(withId(R.id.tv_synopsis)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(dummyMovie.synopsis)))
        }
        onView(withId(R.id.rb_score)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.vv_trailer)).check(matches(isDisplayed()))
        onView(isRoot()).perform(TestTools().waitFor(5000))
    }
}