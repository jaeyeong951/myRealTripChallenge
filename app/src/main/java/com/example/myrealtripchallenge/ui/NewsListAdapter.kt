package com.example.myrealtripchallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrealtripchallenge.R
import com.example.myrealtripchallenge.viewModel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item.view.*

class NewsListAdapter(val viewModel: MainViewModel) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int {
        return viewModel.getNewsSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ViewHolder){
            viewModel.getNewsByPosition(position).let{
                val url = it.link
                holder.view.news_title.text = it.title

                holder.view.news_description.text = it.description
                holder.view.news_keyword_1.text = it.keyWord1
                holder.view.news_keyword_2.text = it.keyWord2
                holder.view.news_keyword_3.text = it.keyWord3
                Picasso.get().load(it.imageSrc).into(holder.view.news_image)
                holder.view.setOnClickListener {
                    viewModel.itemClick(url!!)
                }
            }

        }
    }


}