package com.im.moviecatalogue.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.im.moviecatalogue.R
import com.im.moviecatalogue.ui.main.MainActivity
import com.im.moviecatalogue.utils.EspressoIdlingResource
import com.im.moviecatalogue.utils.values.TestTools
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class MovieTest{

    @get:Rule
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun toDetailActivityTest() {
        onView(withId(R.id.rv_movie)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        }
        onView(withId(R.id.toolbar_title)).apply {
            check(matches(isDisplayed()))
            check(matches(withText("Detail Movie")))
        }
        onView(withId(R.id.tv_tittle)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_year)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_synopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        onView(withId(R.id.rb_score)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.vv_trailer)).apply {
            check(matches(isDisplayed()))
        }
        onView(isRoot()).perform(TestTools().waitFor(2000))
        onView(withId(R.id.vv_trailer)).perform(click())
        onView(isRoot()).perform(TestTools().waitFor(5000))
        pressBack()

        onView(withId(R.id.action_favorite)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.rv_movie)).apply {
            check(matches(isDisplayed()))
            perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
        onView(isRoot()).perform(TestTools().waitFor(2000))
        onView(withId(R.id.btn_favorite)).perform(click())

    }



}