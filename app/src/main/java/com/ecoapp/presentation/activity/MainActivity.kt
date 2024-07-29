package com.ecoapp.presentation.activity


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.ecoapp.presentation.base.BaseActivity
import com.ecoapp.presentation.screens.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : BaseActivity() {

    @Composable
    override fun Content(innerPadding: PaddingValues) {
        MainScreen(innerPadding)
    }
}