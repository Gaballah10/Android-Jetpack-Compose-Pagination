package com.example.smartzonetestcompose.ui.fragments.news.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.smartzonetestcompose.BuildConfig
import com.example.smartzonetestcompose.local.ArticleDatabse
import com.example.smartzonetestcompose.network.model.Article
import com.example.smartzonetestcompose.ui.fragments.news.NewsAction
import com.example.smartzonetestcompose.ui.fragments.news.usecases.RemoteCallUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsVm @Inject constructor(private val remoteCallUseCases: RemoteCallUseCases) :
    ViewModel() {

    var articleMutableList = MutableStateFlow(emptyList<Article>())
    var isMainLoading = MutableLiveData<Boolean>()
    var isFooterLoading = MutableLiveData<Boolean>()
    val liveDataAction: MutableLiveData<NewsAction> =
        MutableLiveData<NewsAction>()
    var livedataLoadNextPage = MutableLiveData<Boolean>().apply {
        false
    }

    init {
        refresh()
    }

    private fun refresh() {
        remoteCallUseCases.refresh()
        loadMore(BuildConfig.NEWS_API_KEY)
    }

    fun loadMore(apiKey: String) {
        remoteCallUseCases.load(apiKey, object : RemoteCallUseCases.OnLoadListener {

            override fun loading() {
                liveDataAction.value = NewsAction.Loading
                isMainLoading.value = true
            }

            override fun footerLoading() {
                liveDataAction.value = NewsAction.FooterLoading
                isFooterLoading.value = true
            }

            override fun loaded(data: MutableList<Article>) {
                liveDataAction.value = NewsAction.Result
                livedataLoadNextPage.value = true
                isMainLoading.value = false
                isFooterLoading.value = false
                articleMutableList.value = articleMutableList.value + data
            }

            override fun doNothing() {
                liveDataAction.value = NewsAction.DoNothing
            }

            override fun error(ex: Throwable) {
                liveDataAction.value = NewsAction.Error(ex)
                Timber.e(ex)
            }
        })
    }

    fun insertArticle(context: Context, article: Article) {
        ArticleDatabse.getInstance(context).articlesDao().insertArticle(article)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}

