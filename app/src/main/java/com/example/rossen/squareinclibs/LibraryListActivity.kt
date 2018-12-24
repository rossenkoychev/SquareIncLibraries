package com.example.rossen.squareinclibs

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.activity_library_list.*
import kotlinx.android.synthetic.main.library_list.*

class LibraryListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var viewModel: LibraryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_list)
        viewModel = ViewModelProviders.of(this).get(LibraryListViewModel::class.java)
        setSupportActionBar(toolbar)

        toolbar.title = title

        if (library_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
            showDetails()
        }
        showMainFragment()
        setupListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupListeners() {
        viewModel.selectedRepo.observe(this, Observer {
            //fragment listens for data so no need to pass it, just change the visible fragment for single pane mode
            if (!twoPane) {
                if (it != null) {
                    showDetails()
                }
            }
        })
    }

    private fun showMainFragment() {
        val fragment = LibraryListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    private fun showDetails() {
        val fragment = LibraryDetailFragment()
        if (twoPane) {
            //TODO select where to load data
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit()
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}
