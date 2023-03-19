package com.example.smartzonetestcompose.ui.fragments.favourites.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartzonetestcompose.local.ArticleDatabse
import com.example.smartzonetestcompose.network.model.Article
import com.example.smartzonetestcompose.ui.fragments.favourites.FavLocalAction
import com.example.smartzonetestcompose.ui.fragments.news.viewmodel.NewsVm
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouritesVM @Inject constructor() : ViewModel() {

    val liveFavouriteDataAction: MutableLiveData<FavLocalAction> =
        MutableLiveData<FavLocalAction>().apply { FavLocalAction.Loading }


    fun getLocalData(context: Context) {

        ArticleDatabse.getInstance(context).articlesDao().getAllArticles()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : SingleObserver<List<Article>> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onSuccess(list: List<Article>) {
                        liveFavouriteDataAction.value = FavLocalAction.Result(list)
                    }

                    override fun onError(ex: Throwable) {
                        liveFavouriteDataAction.value = FavLocalAction.Error(exception = ex)
                    }
                }
            )
    }

    fun removeArticleFromLocalDatabase(context: Context, data: Article) {
        ArticleDatabse.getInstance(context).articlesDao()
            .deleteArticleItem(data.title)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onComplete() {
                        getLocalData(context)
                    }

                    override fun onError(e: Throwable) {
                    }
                }
            )
    }
}