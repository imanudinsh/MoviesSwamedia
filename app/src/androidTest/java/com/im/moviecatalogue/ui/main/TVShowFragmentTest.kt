package com.im.moviecatalogue.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.im.moviecatalogue.R
import com.im.moviecatalogue.testing.SingleFragmentActivity
import com.im.moviecatalogue.utils.EspressoIdlingResource
import com.im.moviecatalogue.utils.RecyclerViewItemCountAssertion
import com.im.moviecatalogue.utils.values.TestTools
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TVShowFragmentTest{

    @get:Rule
    val activityRule: ActivityTestRule<SingleFragmentActivity> = ActivityTestRule(SingleFragmentActivity::class.java)
    private val tvShowFragment = TVShowFragment()

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
        activityRule.activity.setFragment(tvShowFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun loadMovies(){
        onView(withId(R.id.rv_tvshow)).apply {
            check(ViewAssertions.matches(isDisplayed()))
            check(RecyclerViewItemCountAssertion(20))
        }
        onView(isRoot()).perform(TestTools().waitFor(5000))

    }
}