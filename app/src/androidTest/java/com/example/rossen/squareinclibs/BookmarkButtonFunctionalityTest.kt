package com.example.rossen.squareinclibs

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.rossen.squareinclibs.dataProvider.LiveDataStateIdlingResource
import com.example.rossen.squareinclibs.ui.RepositoriesListFragment
import com.example.rossen.squareinclibs.ui.SquareIncLibsActivity
import junit.framework.AssertionFailedError
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This class test bookmarking functionality
 *
 * @author rosen.koychev
 */
@RunWith(AndroidJUnit4::class)

class BookmarkButtonFunctionalityTest {

    private val myActivityIntent =
        Intent(InstrumentationRegistry.getInstrumentation().context, SquareIncLibsActivity::class.java)


    @get:Rule
    var mActivityTestRule: ActivityTestRule<SquareIncLibsActivity> = ActivityTestRule<SquareIncLibsActivity>(
        SquareIncLibsActivity::class.java
    )

    @Before
    fun registerIdlingResource() {
        // let espresso know to synchronize with background tasks
        IdlingRegistry.getInstance().register(LiveDataStateIdlingResource.getIdlingResource())
    }

    @Before
    fun setup() {
        mActivityTestRule.launchActivity(myActivityIntent)
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
        var isBookmarked=
        try {
            onView(
                withRecyclerView(R.id.reposListRecyclerView)
                    .atPositionOnView(1, R.id.bookmarkIcon)
            )
                .check(matches((isDisplayed())))
             true
            // repo is bookmarked
        } catch (e: AssertionFailedError) {
             false
            // repo is not bookmarked

        }

        onView(withId(R.id.reposListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView((withId(R.id.bookmarkButton))).perform(click())

        // if(mActivityTestRule.activity.)
        Espresso.pressBack()
        // Espresso.pressBack()
        //if it was bookmarked , should not be bookmarked by now.
        if (isBookmarked) {
            onView(
                withRecyclerView(R.id.reposListRecyclerView)
                    .atPositionOnView(1, R.id.bookmarkIcon)
            )
                .check(matches(not(isDisplayed())))
        } else {
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