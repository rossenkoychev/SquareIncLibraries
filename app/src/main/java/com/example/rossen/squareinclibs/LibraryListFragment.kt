package com.example.rossen.squareinclibs

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rossen.squareinclibs.adapter.RepositoriesRecyclerViewAdapter
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.library_list.*
import kotlinx.android.synthetic.main.library_list.view.*

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

        // Show the dummy content as text in a TextView.
        var list = rootView.findViewById<RecyclerView>(R.id.libraryListRecyclerView)
            setupAdapter(rootView)
        return rootView
    }

    fun setupAdapter(rootView:View) {
        val adapter = RepositoriesRecyclerViewAdapter()

        rootView.libraryListRecyclerView.layoutManager = LinearLayoutManager(activity)
        rootView.libraryListRecyclerView.adapter = adapter

        viewModel.repositories.observe(this, Observer {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()
        })

        adapter.selectedItemSubject.observe(this, Observer {
            //showDetails()
            viewModel.selectedRepo.value = it
        })
    }
}