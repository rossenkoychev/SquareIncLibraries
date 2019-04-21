package com.example.rossen.squareinclibs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rossen.squareinclibs.R
import com.example.rossen.squareinclibs.model.Repository
import kotlinx.android.synthetic.main.repo_element_content.view.*

/**
 * Adapter that handles putting repositories in the recycler view.
 * Also listens for click events
 */
class RepositoriesRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<RepositoriesRecyclerViewAdapter.ViewHolder>() {

    private var repos: List<Repository> = listOf()
    var selectedItemSubject: MutableLiveData<Repository> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_element_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repos[position]
        holder.repoName.text = item.name
        holder.stargazerCount.text = context.getString(R.string.stars_count, item.stargazerCount)
        holder.bookmarkIcon.visibility = if (item.isBookmarked) View.VISIBLE else View.INVISIBLE

    }

    override fun getItemCount() = repos.size

    fun loadItems(repositories: List<Repository>) {
        repos = repositories
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.repositoryName
        val bookmarkIcon: ImageView = view.bookmarkIcon
        val stargazerCount: TextView = view.stargazerCount

        init {
            itemView.setOnClickListener {
                selectedItemSubject.value = repos[layoutPosition]
            }
        }
    }
}