package com.example.smartzonetestcompose.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.smartzonetestcompose.useCases.NetworkConnectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val networkConnectionUseCase: NetworkConnectionUseCase) : ViewModel() {


    val networkStateLiveData: LiveData<NetworkConnectionUseCase.NetworkStates> =
        MutableLiveData<NetworkConnectionUseCase.NetworkStates>().apply {
            value = NetworkConnectionUseCase.NetworkStates.NoNetwork
        }

    /**
     * Network Connection States
     */
    fun updateNetworkState(state: NetworkConnectionUseCase.NetworkStates) {
        (networkStateLiveData as MutableLiveData<NetworkConnectionUseCase.NetworkStates>).value =
            state
    }

    fun registerNetworkConnectionCallback(context: Context) {
        networkConnectionUseCase.onStart(context)
    }

    fun unregisterNetworkConnectionCallback(context: Context) {
        networkConnectionUseCase.onDestroy(context)
    }

    fun observeNetworkConnection(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<NetworkConnectionUseCase.NetworkStates>
    ) {
        networkConnectionUseCase.observe(
            lifecycleOwner,
            observer
        )
    }

    fun observeNetworkState(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<NetworkConnectionUseCase.NetworkStates>
    ) {
        networkStateLiveData.observe(
            lifecycleOwner,
            observer
        )
    }
}