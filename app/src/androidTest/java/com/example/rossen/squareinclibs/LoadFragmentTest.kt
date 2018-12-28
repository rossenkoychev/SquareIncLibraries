package com.example.rossen.squareinclibs

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.rossen.squareinclibs.ui.SquareIncLibsActivity
import com.example.rossen.squareinclibs.ui.RepositoriesListFragment
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Class that tests if RepositoriesListFragment loads and is in the correct initial
 *
 * @author rosen.koychev
 */
@RunWith(AndroidJUnit4::class)

class LoadFragmentTest {

    @Before
    fun init() {
        val fragment = RepositoriesListFragment()
        mActivityTestRule.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    @get:Rule
    var mActivityTestRule: ActivityTestRule<SquareIncLibsActivity> = ActivityTestRule<SquareIncLibsActivity>(
        SquareIncLibsActivity::class
            .java
    )

    /**
     * Initial state of the Fragment must show a progressbar only.
     */
    @Test
    fun checkIfLibraryFragmentLoads() {

        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.errorTextView))
            .check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.reposListRecyclerView))
            .check(matches(Matchers.not(isDisplayed())))
    }
}