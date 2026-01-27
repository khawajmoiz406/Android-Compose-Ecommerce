package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.config.theme.MyApplicationTheme
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.SnackbarUtils
import com.example.myapplication.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MainScreen() }
    }
}

@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    SnackbarUtils.init(snackbarHostState, scope)

    MyApplicationTheme {
        CompositionLocalProvider(value = LocalParentNavController provides navController) {
            ProvideTextStyle(
                TextStyle(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                )
            ) {
                Scaffold(
                    snackbarHost = { SnackbarUtils.CustomSnackbarHost(snackbarHostState) },
                    content = { innerPadding -> NavGraph(navController, innerPadding) }
                )
            }
        }
    }
}