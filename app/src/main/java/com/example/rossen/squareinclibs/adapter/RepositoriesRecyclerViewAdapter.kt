package com.example.rossen.squareinclibs.adapter

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.rossen.squareinclibs.R
import com.example.rossen.squareinclibs.model.Repository
import kotlinx.android.synthetic.main.library_list_content.view.*

class RepositoriesRecyclerViewAdapter : RecyclerView.Adapter<RepositoriesRecyclerViewAdapter.ViewHolder>() {

    private var repos: List<Repository> = listOf()

    var selectedItemSubject: MutableLiveData<Repository> = MutableLiveData()

    fun loadItems(repositories: List<Repository>) {
        repos = repositories
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
        holder.bookmarkIcon.visibility = if (item.isBookmarked) View.VISIBLE else View.INVISIBLE


//        //TODO remove this
//        with(holder.itemView) {
//            tag = item
//        }
    }

    override fun getItemCount() = repos.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.repoName
        val bookmarkIcon: ImageView = view.bookmarkIcon
        val stargazerCount: TextView = view.stargazerCount

        init {
            itemView.setOnClickListener {
                selectedItemSubject.value = repos[layoutPosition]
            }
        }
    }
}