package com.example.smartzonetestcompose.useCases

import com.example.smartzonetestcompose.network.model.NewsInfo
import io.reactivex.Single

interface NetworkUseCase {

     fun callBackNewsData(
        q: String, from: String, to: String, sortBy: String, apiKey: String
    ): Single<NewsInfo>

}