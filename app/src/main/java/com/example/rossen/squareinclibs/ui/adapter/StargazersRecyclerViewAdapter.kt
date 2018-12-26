package com.example.rossen.squareinclibs.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.rossen.squareinclibs.R
import com.example.rossen.squareinclibs.model.Stargazer
import kotlinx.android.synthetic.main.library_detail_content.view.*


class StargazersRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<StargazersRecyclerViewAdapter.ViewHolder>() {

    private var stargazers: List<Stargazer> = listOf()

    fun loadItems(stargazersList: List<Stargazer>) {
        stargazers = stargazersList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_detail_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = stargazers[position]
        holder.userName.text = item.userName

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(
            CenterCrop(),
            RoundedCorners(context.resources.getDimension(R.dimen.avatar_corner_rounding).toInt())
        )
        Glide.with(context)
            .load(item.avatar)
            .apply(requestOptions)
            .into(holder.avatar)
    }

    override fun getItemCount() = stargazers.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.stargazerName
        val avatar: ImageView = view.avatar

    }
}