package com.example.rossen.squareinclibs

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.rossen.squareinclibs.adapter.StargazersRecyclerViewAdapter
import com.example.rossen.squareinclibs.model.Repository
import com.example.rossen.squareinclibs.model.Stargazer
import com.example.rossen.squareinclibs.model.StargazersState
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.library_detail.view.*

/**
 *  Fragment presenting stargazers' data for each repo and giving ability to bookmark repos
 */
class LibraryDetailFragment : Fragment() {


    private lateinit var viewModel: LibraryListViewModel
    private var adapter: StargazersRecyclerViewAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var noStargazersTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var bookmarksButton: Button? = null
    private var repoName: TextView? = null
    private var repository: Repository? = null


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
        initVariables(rootView)
        setupAdapter()
        return rootView
    }

    private fun initVariables(rootView: View) {
        //since this happens after onAttach , we are safe to assume that context is not null
        adapter = StargazersRecyclerViewAdapter(context!!)
        recyclerView = rootView.libraryDetailsRecyclerView
        noStargazersTextView = rootView.noDataView
        progressBar = rootView.detailsProgressBar
        bookmarksButton = rootView.bookmarkButton
        repoName = rootView.repoName
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

        viewModel.stargazersState.observe(this, Observer { state ->
            when (state) {
                is StargazersState.Loading -> showProgress()
                is StargazersState.StargazersError -> showError(state.message)
                is StargazersState.Stargazers -> updateStargazers(state.stargazers)
            }
        })

        viewModel.selectedRepo.observe(this, Observer { repo ->
            if (repo == null) {
                repository = null
                showNoRepoChosen()
            } else {
                repository = repo
                setRepoName(repo.name)
            }
        })
        setupListeners()
        //initial state
        showNoRepoChosen()
    }

    private fun setupListeners() {
        bookmarksButton?.setOnClickListener {
            repository?.let { repo ->
                if (repo.isBookmarked) {
                    viewModel.deleteBookMark(repo)
                    bookmarksButton?.text = context!!.getString(R.string.add_bookmark)

                } else {
                    viewModel.addToBookmarks(repo)
                    bookmarksButton?.text = context!!.getString(R.string.remove_bookmark)
                }
            }
        }
    }

    private fun setRepoName(name: String) {
        repoName?.visibility = View.VISIBLE
        bookmarksButton?.visibility = View.VISIBLE
        bookmarksButton?.text= if(repository!!.isBookmarked) context!!.getString(R.string.remove_bookmark) else context!!.getString(R.string.add_bookmark)
        repoName?.text = name
    }

    private fun showNoRepoChosen() {
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        repoName?.visibility = View.GONE
        bookmarksButton?.visibility = View.GONE
        noStargazersTextView?.visibility = View.VISIBLE
        noStargazersTextView?.text = context!!.resources.getString(R.string.select_repository)
    }

    private fun showProgress() {
        progressBar?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE
        noStargazersTextView?.visibility = View.GONE
    }

    private fun showError(message: String? = context!!.resources.getString(R.string.error_loading_data)) {
        progressBar?.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        noStargazersTextView?.visibility = View.VISIBLE
        noStargazersTextView?.text = message
    }

    private fun updateStargazers(stargazers: List<Stargazer>) {

        progressBar?.visibility = View.GONE
        if (stargazers.isEmpty()) {
            noStargazersTextView?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
            noStargazersTextView?.text = context!!.resources.getString(R.string.no_stargazers_text)
        } else {
            noStargazersTextView?.visibility = View.GONE

            recyclerView?.visibility = View.VISIBLE
            adapter?.loadItems(stargazers)
            adapter?.notifyDataSetChanged()
        }
    }
}

