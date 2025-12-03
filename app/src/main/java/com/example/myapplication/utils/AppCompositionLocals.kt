package com.example.myapplication.utils

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

object AppCompositionLocals {
    val LocalParentNavController = compositionLocalOf<NavController?> { null }
    val LocalDrawerStateController = compositionLocalOf<DrawerState?> { null }
}