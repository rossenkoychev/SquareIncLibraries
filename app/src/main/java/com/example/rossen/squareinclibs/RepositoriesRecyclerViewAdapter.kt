package com.example.rossen.squareinclibs

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rossen.squareinclibs.model.Repository
import kotlinx.android.synthetic.main.library_list_content.view.*

class RepositoriesRecyclerViewAdapter(
    private val parentActivity: LibraryListActivity,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<RepositoriesRecyclerViewAdapter.ViewHolder>() {

    private var repos: List<Repository> = listOf()
    private val onClickListener: View.OnClickListener

    fun loadItems(repositories: List<Repository>) {
        repos = repositories
    }


    //If using a tablet (twoPane==true) we reload the details fragment, else we change the activity
    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Repository
            if (twoPane) {
                val fragment = LibraryDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(LibraryDetailFragment.ARG_ITEM_ID, item.name)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.library_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, LibraryDetailActivity::class.java).apply {
                    putExtra(LibraryDetailFragment.ARG_ITEM_ID, item.name)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repos[position]
        holder.repoName.text = item.name
        holder.stargazerCount.text = item.stargazerCount.toString()
        holder.bookmarkIcon.visibility = if(item.isBookmarked)  View.VISIBLE else View.INVISIBLE

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = repos.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.repoName
        val bookmarkIcon: ImageView = view.bookmarkIcon
        val stargazerCount: TextView = view.stargazerCount
    }
}