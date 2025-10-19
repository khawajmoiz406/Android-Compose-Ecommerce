package com.example.myapplication.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import com.example.myapplication.models.helper.SearchController

object AppCompositionLocals {
    @OptIn(ExperimentalMaterial3Api::class)
    val LocalTopAppBarScrollBehavior = compositionLocalOf<TopAppBarScrollBehavior?> { null }
    val LocalParentNavController = compositionLocalOf<NavController?> { null }
    val LocalSearchController = compositionLocalOf<SearchController?> { null }
}