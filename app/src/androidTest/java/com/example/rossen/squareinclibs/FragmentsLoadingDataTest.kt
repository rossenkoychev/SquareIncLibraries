package com.example.rossen.squareinclibs

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.rossen.squareinclibs.dataProvider.LiveDataStateIdlingResource
import com.example.rossen.squareinclibs.ui.RepositoriesListFragment
import com.example.rossen.squareinclibs.ui.SquareIncLibsActivity
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * This class tests if data is laoded and displayed correctly
 * WARNING: this class works with real data and assumes data has not changed since the writing of this class.
 * TODO  implement with mocked data to ensure correct data loads
 *
 * @author rosen.koychev
 */
@RunWith(AndroidJUnit4::class)

class FragmentsLoadingDataTest {

    val repoName = "html5"
    val stargazerName = "donigian"

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
     * Checks if RepositoriesListFragment is in the correct state after data is loaded
     */
    @Test
    fun checkFragmentStateAfterLoadedData() {
        onView(withId(R.id.reposListRecyclerView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.progressBar))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.errorTextView))
            .check(matches(not(isDisplayed())))

    }

    @Test
    fun checkIfStargazersFragmentLoads() {
        onView(withId(R.id.reposListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView((withId(R.id.repoName))).check(matches(withText(repoName)))
    }

    @Test
    fun checkIfStargazersDataLoads() {
        onView(withId(R.id.reposListRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.stargazersRecyclerView)).check(matches(isDisplayed()))

        onView(
            withRecyclerView(R.id.stargazersRecyclerView)
                .atPositionOnView(1, R.id.stargazerName)
        )
            .check(matches(withText(stargazerName)))
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}