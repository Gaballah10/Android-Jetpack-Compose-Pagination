package com.example.smartzonetestcompose.di

import com.example.smartzonetestcompose.BuildConfig
import com.example.smartzonetestcompose.network.NewsNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideNewsApiBaseUrl() = NewsNetwork.getRadioApi(BuildConfig.BASE_URL)
}

