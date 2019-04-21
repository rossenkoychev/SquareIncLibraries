package com.example.rossen.squareinclibs.ui

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rossen.squareinclibs.R
import com.example.rossen.squareinclibs.ui.adapter.RepositoriesRecyclerViewAdapter
import com.example.rossen.squareinclibs.model.RepositoriesState
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.viewmodel.SquareIncLibsViewModel
import kotlinx.android.synthetic.main.library_list.view.*

/**
 *  Fragment showing all repositories with the number of stargazers and indication if they are bookmarked
 */
class RepositoriesListFragment : Fragment() {

    private lateinit var viewModel: SquareIncLibsViewModel
    private var adapter: RepositoriesRecyclerViewAdapter? = null
    private var progressBar: ProgressBar? = null
    private var errorTextView: TextView? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SquareIncLibsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.library_list, container, false)
        initVariables(rootView)
        setupAdapter()
        return rootView
    }

    private fun initVariables(rootView: View) {
        //since this happens after onAttach , we are safe to assume that context is not null
        adapter = RepositoriesRecyclerViewAdapter(context!!)
        errorTextView = rootView.errorTextView
        progressBar = rootView.progressBar
        recyclerView = rootView.reposListRecyclerView
    }

    /**
     * setup adapter properties
     */
    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView?.context,
            layoutManager.orientation
        )
        recyclerView?.addItemDecoration(dividerItemDecoration)

        loadData()
    }

    /**
     * Depending on the incoming data from viewmodel, decide which view to show
     */
    private fun loadData() {
        viewModel.repoState.observe(this, Observer { state ->
            when (state) {
                is RepositoriesState.Loading -> showProgress()
                is RepositoriesState.ReposError -> showError(state.message)
                is RepositoriesState.Repositories -> updateRepositories(state.repositories)
            }
        })
        adapter?.selectedItemSubject?.observe(this, Observer {
            viewModel.setSelectedRepoValue(it)
        })
    }

    private fun updateRepositories(repositories: List<Repository>) {
        errorTextView?.visibility = View.GONE
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
        adapter?.loadItems(repositories)
        adapter?.notifyDataSetChanged()
    }

    private fun showError(message: String? = context!!.resources.getString(R.string.error_loading_data)) {
        errorTextView?.visibility = View.VISIBLE
        errorTextView?.text = message
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
    }

    private fun showProgress() {
        progressBar?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
        errorTextView?.visibility = View.GONE
    }
}