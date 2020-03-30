package com.example.myrealtripchallenge.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtripchallenge.dto.NewsItem
import com.example.myrealtripchallenge.dto.SplitItem
import com.example.myrealtripchallenge.repository.JsoupRepository
import com.example.myrealtripchallenge.repository.RssRepository
import com.example.myrealtripchallenge.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.TimeUnit

class MainViewModel (private val rssRepository: RssRepository, private val jsoupRepository: JsoupRepository) : ViewModel(){
    private val _isRssDataLoaded: SingleLiveEvent<Any> = SingleLiveEvent()
    val isRssDataLoaded: LiveData<Any>
        get() = _isRssDataLoaded

    private val _isItemClicked: SingleLiveEvent<String> = SingleLiveEvent()
    val isItemClicked: LiveData<String>
        get() = _isItemClicked

    private var newsSize : Int = 0
    private var howToKnowLastNews : Int = 0

    var newsList: ArrayList<NewsItem> = ArrayList()

    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun getNewsSize():Int{
        return newsList.size
    }

    fun getNewsByPosition(position:Int):NewsItem{
        return newsList.get(position)
    }

    fun itemClick(url:String){
        _isItemClicked.postValue(url)
    }

    fun loadRss(){
        apiCall(rssRepository.requestData("ko","KR","KR:ko"),
            Consumer {
                newsSize = it.info?.news!!.size
                Log.e("뉴스사이즈",newsSize.toString())
                for (item in it.info.news){
                    Log.e("링크", item.link!!)
                    Log.e("타이틀", item.title!!)
                    loadHtml(item.link)
                }
            },indicator = true)
    }

    fun loadHtml(url : String){
        apiCall(jsoupRepository.requestResponse(url),
        Consumer {
            var title:String? = null
            var desc:String? = null
            var image:String? = null
            var link:String? = null
            val doc:Document = Jsoup.parse(it)
            val elements:Elements = doc.getElementsByTag("meta")
            for(element in elements){
                when(element.attr("property")){
                    "og:title" -> {
                        Log.e("og:title", element.attr("content"))
                        title = element.attr("content")
                    }
                    "og:description" -> {
                        Log.e("og:description", element.attr("content"))
                        desc = element.attr("content")
                    }
                    "og:image" -> {
                        Log.e("og:image", element.attr("content"))
                        image = element.attr("content")
                    }
                    "og:url" -> {
                        link = element.attr("content")
                    }
                }
            }
            if(title != null && desc != null && image != null && link != null){
                val keyWordList = extractKeyWord(desc)
                if(keyWordList.size < 3){
                    when(keyWordList.size){
                        0 -> {
                            keyWordList.add(SplitItem(" "))
                            keyWordList.add(SplitItem(" "))
                            keyWordList.add(SplitItem(" "))
                        }
                        1 -> {
                            keyWordList.add(SplitItem(" "))
                            keyWordList.add(SplitItem(" "))
                        }
                        2 -> {
                            keyWordList.add(SplitItem(" "))
                        }
                    }
                }
                newsList.add(NewsItem(title, desc, image, link, keyWordList[0].letter, keyWordList[1].letter, keyWordList[2].letter))
            }
            howToKnowLastNews++
            Log.e("현재사이즈",howToKnowLastNews.toString())
            if(howToKnowLastNews == newsSize-2){
                _isRssDataLoaded.call()
            }
        },indicator = true)
    }

    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        Log.e("오류발생",it.message!!)
                    },
                    indicator : Boolean = false, timeout: Long = 5){
        addDisposable(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(timeout, TimeUnit.SECONDS)
//            .doOnSubscribe{ if(indicator)  }
//            .doAfterTerminate {  }
            .subscribe(onSuccess, onError))
    }

    fun clearNewsList(){
        newsList.clear()
        howToKnowLastNews = 0
        newsSize = 0
    }

    fun extractKeyWord(desc:String) :MutableList<SplitItem>{
        val splited = desc.split(" ","“","”",".",",","·","'","'","[","]","(",")","\"","'")
        var splited_array:MutableList<SplitItem> = mutableListOf()

        for(i in splited){
            splited_array.add(SplitItem(i))
        }
        splited_array = splited_array.filter { it.letter!!.length > 1 }.toMutableList()
        var i = 0
        var j = 1
        while(i <= (splited_array.size-2)){
            while(j <= (splited_array.size-1)){
                if(splited_array[i].letter==splited_array[j].letter){
                    splited_array[i].number++
                    splited_array.removeAt(j)
                }
                else j++
            }
            i++
            j = i+1
        }

        splited_array = sortByLetter(splited_array)
        splited_array = sortByValue(splited_array)

        return splited_array

    }

    private fun sortByValue(list:MutableList<SplitItem>):MutableList<SplitItem>{
        return list.sortedWith(Comparator{a, b ->
            when {
                a.number < b.number -> 1
                a.number > b.number -> -1
                else -> 0
            }
        }).toMutableList()
    }

    private fun sortByLetter(list:MutableList<SplitItem>):MutableList<SplitItem>{
        return list.sortedWith(Comparator{a, b ->
            when {
                a.letter!! > b.letter!! -> 1
                a.letter!! < b.letter!! -> -1
                else -> 0
            }
        }).toMutableList()
    }
}