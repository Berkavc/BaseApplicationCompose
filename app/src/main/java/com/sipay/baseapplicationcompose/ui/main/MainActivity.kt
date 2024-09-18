package com.sipay.baseapplicationcompose.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.LifecycleOwner
import com.sipay.baseapplicationcompose.R
import com.sipay.baseapplicationcompose.base.BaseActivity
import com.sipay.baseapplicationcompose.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val layoutRes: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()
    override var viewLifeCycleOwner: LifecycleOwner = this

    override fun observeViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
    }
}