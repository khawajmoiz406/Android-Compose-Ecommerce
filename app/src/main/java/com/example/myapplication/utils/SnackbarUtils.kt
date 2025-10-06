package com.example.myapplication.utils

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object SnackbarUtils {
    private var snackbarHostState: SnackbarHostState? = null
    private var scope: CoroutineScope? = null

    fun init(hostState: SnackbarHostState, coroutineScope: CoroutineScope) {
        snackbarHostState = hostState
        scope = coroutineScope
    }

    fun show(message: String, actionLabel: String? = null) {
        scope?.launch {
            snackbarHostState?.showSnackbar(message, actionLabel)
        }
    }
}