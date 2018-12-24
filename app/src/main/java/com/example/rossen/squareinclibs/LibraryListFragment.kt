package com.example.rossen.squareinclibs

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rossen.squareinclibs.adapter.RepositoriesRecyclerViewAdapter
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.library_list.view.*
import android.support.v7.widget.DividerItemDecoration


class LibraryListFragment : Fragment() {

    private lateinit var viewModel: LibraryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(LibraryListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        val rootView = inflater.inflate(R.layout.library_list, container, false)
        setupAdapter(rootView)
        return rootView
    }

    private fun setupAdapter(rootView: View) {
        val adapter = RepositoriesRecyclerViewAdapter()
        val recyclerView = rootView.libraryListRecyclerView
        val layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        loadData(adapter)


    }

    private fun loadData(adapter: RepositoriesRecyclerViewAdapter) {

        adapter.loadItems((viewModel.repositories.value) ?: emptyList())
        adapter.notifyDataSetChanged()

        viewModel.repositories.observe(this, Observer {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()
        })

        adapter.selectedItemSubject.observe(this, Observer {
            viewModel.setSelectedRepoValue(it)
        })
    }
}