package com.example.myrealtripchallenge.ui

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrealtripchallenge.R
import com.example.myrealtripchallenge.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(){
    private val viewModel by sharedViewModel<MainViewModel>()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news_list_pull.setProgressBackgroundColorSchemeColor(Color.WHITE)
        news_list_pull.setColorSchemeColors(Color.BLACK)
        news_list_pull.setOnRefreshListener {
            viewModel.clearNewsList()
            viewModel.loadRss()
            viewModel.isRssDataLoaded.observe(viewLifecycleOwner, Observer {
                news_list_pull.isRefreshing = false
                adapter.notifyDataSetChanged()
            })
        }
        viewModel.isItemClicked.observe(viewLifecycleOwner, Observer {
            val directions: NavDirections = MainFragmentDirections.actionMainFragmentToWebViewFragment().setUrl(it)
            findNavController().navigate(directions)
        })
        adapter = NewsListAdapter(viewModel)
        layoutManager = LinearLayoutManager(this.context)
        news_list_recycler.adapter = adapter
        news_list_recycler.layoutManager = layoutManager
    }
}