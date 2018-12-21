package com.example.rossen.squareinclibs


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView


import com.example.rossen.squareinclibs.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_library_list.*
import kotlinx.android.synthetic.main.library_list.*

class LibraryListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private lateinit var viewModel:LibrariyListViewModel
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_list)
        viewModel = ViewModelProviders.of(this).get(LibrariyListViewModel::class.java)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (library_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        setupRecyclerView(library_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
    }


}
