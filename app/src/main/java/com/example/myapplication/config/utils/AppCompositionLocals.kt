package com.example.myapplication.config.utils

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import com.example.myapplication.models.helper.SearchController

object AppCompositionLocals {
    @OptIn(ExperimentalMaterial3Api::class)
    val LocalParentNavController = compositionLocalOf<NavController?> { null }
    val LocalDrawerStateController = compositionLocalOf<DrawerState?> { null }
}