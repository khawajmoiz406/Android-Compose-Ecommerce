package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.config.navigation.NavGraph
import com.example.myapplication.config.theme.MyApplicationTheme
import com.example.myapplication.config.theme.ThemeMode
import com.example.myapplication.config.theme.ThemeState
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.SnackbarUtils
import com.example.myapplication.core.model.User
import com.example.myapplication.core.pref.EncryptedSharedPref
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MainScreen() }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val user = EncryptedSharedPref.getInstance(context).getModel(object : TypeToken<User>() {})
    ThemeState.darkTheme.value = user?.themeMode == ThemeMode.Dark.value

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
                    content = { innerPadding -> NavGraph(navController, innerPadding) },
                    modifier = Modifier.pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
                )
            }
        }
    }
}