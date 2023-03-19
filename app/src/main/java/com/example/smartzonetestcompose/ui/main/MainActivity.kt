package com.example.smartzonetestcompose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import com.example.smartzonetestcompose.navigation.SmartNavigation
import com.example.smartzonetestcompose.ui.main.observers.NetworkObserver
import com.example.smartzonetestcompose.ui.main.viewmodel.MainVM
import com.example.smartzonetestcompose.ui.theme.SmartZoneTestComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainVM: MainVM by viewModels()
    @Inject
    lateinit var views: MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartZoneTestComposeTheme {
                SmartZoneApp()
            }
        }
        mainVM.registerNetworkConnectionCallback(applicationContext)
        bindObservers()
    }

    private fun bindObservers() {
        mainVM.observeNetworkConnection(
            this@MainActivity,
            Observer {
                mainVM.updateNetworkState(it)
            }
        )
        mainVM.observeNetworkState(
            this@MainActivity,
            NetworkObserver(views)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mainVM.unregisterNetworkConnectionCallback(applicationContext)
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SmartZoneApp() {
    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize(), content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SmartNavigation()
            }
        })
}

