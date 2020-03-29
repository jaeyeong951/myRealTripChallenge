package com.example.myrealtripchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myrealtripchallenge.R
import kotlinx.android.synthetic.main.fragment_web.*

class WebViewFragment : Fragment(){
    //private val viewModel by sharedViewModel<MainViewModel>()
    val safeArgs:WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_web,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = safeArgs.url
        Log.e("최종",url)
        webView.loadUrl(url)
        webView.webViewClient = WebViewClient()
    }

}