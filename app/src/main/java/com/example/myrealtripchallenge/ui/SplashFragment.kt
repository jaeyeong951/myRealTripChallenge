package com.example.myrealtripchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myrealtripchallenge.R
import com.example.myrealtripchallenge.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment(){
    private val viewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        version.append(context!!.packageManager.getPackageInfo(context!!.packageName, 0).versionName)
        viewModel.loadRss()
        viewModel.isRssDataLoaded.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
        })
    }
}