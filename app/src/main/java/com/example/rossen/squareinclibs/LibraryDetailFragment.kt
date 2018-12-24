package com.example.rossen.squareinclibs

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rossen.squareinclibs.adapter.StargazersRecyclerViewAdapter
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.library_detail.view.*

/**
 *  Fragment presenting stargazers' data for each repo and giving ability to bookmark repos
 */
class LibraryDetailFragment : Fragment() {


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
        val rootView = inflater.inflate(R.layout.library_detail, container, false)

        setupAdapter(rootView)
        loadData(rootView)
        return rootView
    }

    private fun loadData(rootView: View) {
        rootView.repoName.text = viewModel.selectedRepo.value?.name
    }

    private fun setupAdapter(rootView: View) {
        //since this happens after onAttach , we are safe to assume that context is not null
        val adapter = StargazersRecyclerViewAdapter(context!!)
        val layoutManager = LinearLayoutManager(activity)
        val recyclerView = rootView.libraryDetailsRecyclerView

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
          adapter.loadItems( (viewModel.stargazers.value) ?: emptyList())
         adapter.notifyDataSetChanged()

        viewModel.stargazers.observe(this, Observer {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()
        })
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
