package com.example.rossen.squareinclibs



import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager

import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.activity_library_list.*
import kotlinx.android.synthetic.main.library_list.*

class LibraryListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private lateinit var viewModel: LibraryListViewModel
    private var twoPane: Boolean = false

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
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
       // libraryList.adapter = RepositoriesRecyclerViewAdapter(this, twoPane)

        val adapter = RepositoriesRecyclerViewAdapter(this,twoPane)
        libraryListRecyclerView.layoutManager = LinearLayoutManager(this)
        libraryListRecyclerView. adapter = adapter

        viewModel.repositories.observe(this, Observer {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()
        })
    }
}
