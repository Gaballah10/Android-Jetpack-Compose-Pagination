package com.example.smartzonetestcompose.useCases.impl

import com.example.smartzonetestcompose.network.api.NewsApiService
import com.example.smartzonetestcompose.network.model.NewsInfo
import com.example.smartzonetestcompose.useCases.NetworkUseCase
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class NetworkUseCaseImpl @Inject constructor(private val newsApi: NewsApiService) : NetworkUseCase {

    override  fun callBackNewsData(
        q: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): Single<NewsInfo> {
        return newsApi.callBackNewsData(q, from, to, sortBy, apiKey)
    }
}