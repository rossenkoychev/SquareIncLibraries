package com.example.rossen.squareinclibs

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rossen.squareinclibs.viewmodel.LibraryListViewModel
import kotlinx.android.synthetic.main.library_detail.view.*

/**
 * A fragment representing a single Library detail screen.
 * This fragment is either contained in a [LibraryListActivity]
 * in two-pane mode (on tablets) or a [LibraryDetailActivity]
 * on handsets.
 */
class LibraryDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
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

        // Show the dummy content as text in a TextView.
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
