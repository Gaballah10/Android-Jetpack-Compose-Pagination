package com.example.smartzonetestcompose.di

import com.example.smartzonetestcompose.ui.main.MainView
import com.example.smartzonetestcompose.ui.main.MainViewImpl
import com.example.smartzonetestcompose.useCases.NetworkConnectionUseCase
import com.example.smartzonetestcompose.useCases.NetworkUseCase
import com.example.smartzonetestcompose.useCases.impl.NetworkConnectionUseCaseImpl
import com.example.smartzonetestcompose.useCases.impl.NetworkUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Singleton
    @Binds
    abstract fun bindsNetworkUseCase(
        networkUseCaseImpl: NetworkUseCaseImpl
    ): NetworkUseCase

    @Singleton
    @Binds
    abstract fun bindsNetworkConnectionUseCase(
        networkConnectionUseCaseImpl: NetworkConnectionUseCaseImpl
    ): NetworkConnectionUseCase

    @Singleton
    @Binds
    abstract fun bindsMainView(
        MainViewImpl: MainViewImpl
    ): MainView
}
