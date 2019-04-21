package com.example.rossen.squareinclibs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
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