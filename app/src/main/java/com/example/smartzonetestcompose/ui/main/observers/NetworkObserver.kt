package com.example.smartzonetestcompose.ui.main.observers

import androidx.lifecycle.Observer
import com.example.smartzonetestcompose.ui.main.MainView
import com.example.smartzonetestcompose.useCases.NetworkConnectionUseCase

class NetworkObserver(
    private val rootView: MainView
) : Observer<NetworkConnectionUseCase.NetworkStates> {


    override fun onChanged(action : NetworkConnectionUseCase.NetworkStates?) {
        when (action) {
            is NetworkConnectionUseCase.NetworkStates.Connected -> {
                val state = MainView.State.Connected
                rootView.changeNetworkState(state)
            }
            is NetworkConnectionUseCase.NetworkStates.NoNetwork -> {
                val state = MainView.State.NoNetwork
                rootView.changeNetworkState(state)
            }
        }
    }
}