package com.example.smartzonetestcompose.ui.fragments.news.usecases

import com.example.smartzonetestcompose.network.model.Article
import com.example.smartzonetestcompose.network.model.NewsInfo
import com.example.smartzonetestcompose.useCases.NetworkUseCase
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteCallUseCases @Inject constructor(private val networkUseCase: NetworkUseCase) {

    var index = 0
    var LIMIT = listOf("2023-03-18", "2023-03-15", "2023-03-02")
    var currentPage = LIMIT[index]
    var q: String = "apple"
    var from: String = currentPage
    var to: String = currentPage
    var sortBy: String = "popularity"
    private var isLoadingData = false
    private var isLoadingFooter = false

    fun load(
        apiKey: String,
        listener: OnLoadListener
    ) {

        if (index > 1) {
            isLoadingData = false
            isLoadingFooter = false
            listener.doNothing()

        } else {
            if (isLoadingData) {
                listener.loading()
                return
            }
            if (isLoadingFooter) {
                listener.footerLoading()
            }

            currentPage = LIMIT[index]
            from = currentPage
            to = currentPage

            if (currentPage == LIMIT[0]) {
                isLoadingData = true
            } else {
                isLoadingFooter = true
            }

            networkUseCase.callBackNewsData(q, from, to, sortBy, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<NewsInfo> {
                    override fun onSubscribe(d: Disposable) {
                        if (isLoadingData) {
                            listener.loading()
                        }
                        if (isLoadingFooter) {
                            listener.footerLoading()
                        }
                    }

                    override fun onSuccess(response: NewsInfo) {
                        if (response.articles.isNotEmpty()) {
                            index++
                        }
                        isLoadingData = false
                        isLoadingFooter = false
                        listener.loaded(response.articles.toMutableList())
                    }

                    override fun onError(ex: Throwable) {
                        isLoadingData = false
                        isLoadingFooter = false
                        listener.error(ex)
                    }
                })
        }
    }

    /**
     * Reset current pagination to 0
     */
    fun refresh() {
        currentPage = LIMIT[0]
    }

    interface OnLoadListener {
        fun loading()
        fun footerLoading()
        fun loaded(data: MutableList<Article>)
        fun doNothing()
        fun error(ex: Throwable)
    }

}