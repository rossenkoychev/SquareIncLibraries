package com.example.rossen.squareinclibs

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.rossen.squareinclibs.ui.SquareIncLibsActivity
import com.example.rossen.squareinclibs.ui.RepositoriesListFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.InstrumentationRegistry
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import com.example.rossen.squareinclibs.dataProvider.LiveDataStateIdlingResource
import org.hamcrest.Matchers.not
import    android.support.test.espresso.contrib.RecyclerViewActions
import android.support.v7.widget.RecyclerView
import org.junit.After
import junit.framework.AssertionFailedError
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed

/**
 * This class test bookmarking functionality
 *
 * @author rosen.koychev
 */
@RunWith(AndroidJUnit4::class)

class BookmarkButtonFunctionalityTest {

    private val MY_ACTIVITY_INTENT =
        Intent(InstrumentationRegistry.getTargetContext(), SquareIncLibsActivity::class.java)


    @get:Rule
    var mActivityTestRule: ActivityTestRule<SquareIncLibsActivity> = ActivityTestRule<SquareIncLibsActivity>(
        SquareIncLibsActivity::class
            .java
    )

    @Before
    fun registerIdlingResource() {
        // let espresso know to synchronize with background tasks
        IdlingRegistry.getInstance().register(LiveDataStateIdlingResource.getIdlingResource())
    }

    @Before
    fun setup() {
        mActivityTestRule.launchActivity(MY_ACTIVITY_INTENT)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(LiveDataStateIdlingResource.getIdlingResource())
    }

    @Before
    fun init() {
        val fragment = RepositoriesListFragment()
        mActivityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    /**
     * check if bookmark functionality works correct
     */
    @Test
    fun checkIfBookmarksButtonWorks() {
        //before making an assertion we need to check the bookmark state of this repository
        var isBookmarked: Boolean
        try {
            onView(
                withRecyclerView(R.id.reposListRecyclerView)
                    .atPositionOnView(1, R.id.bookmarkIcon)
            )
                .check(matches((isDisplayed())))
            isBookmarked = true
            // repo is bookmarked
        } catch (e: AssertionFailedError) {
            isBookmarked = false
            // repo is not bookmarked

        }

        onView(withId(R.id.reposListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView((withId(R.id.bookmarkButton))).perform(click())

       // if(mActivityTestRule.activity.)
        Espresso.pressBack()
       // Espresso.pressBack()
        //if it was bookmarked , should not be bookmarked by now.
        if(isBookmarked){
            onView(
                withRecyclerView(R.id.reposListRecyclerView)
                    .atPositionOnView(1, R.id.bookmarkIcon)
            )
                .check(matches(not(isDisplayed())))
        }else{
            onView(
                withRecyclerView(R.id.reposListRecyclerView)
                    .atPositionOnView(1, R.id.bookmarkIcon)
            )
                .check(matches(isDisplayed()))
        }


        //click it again to go back to initial state
        onView(withId(R.id.reposListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}