package com.example.myapplication.ui.landing.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.config.components.SvgImage
import com.example.myapplication.config.utils.AppCompositionLocals.LocalParentNavController
import com.example.myapplication.config.utils.Constants
import com.example.myapplication.navigation.Destinations
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val navController = LocalParentNavController.current

    LaunchedEffect(lifecycleOwner) { navController?.let { handleSplash(it) } }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.TopCenter)
                .padding(top = 150.dp)
        )

        SvgImage(
            asset = "splash_union",
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            color = Color.Blue
        )
    }
}

private suspend fun handleSplash(navController: NavController) {
//    val user = EncryptedSharedPref.getInstance(navController.context).getModel(object : TypeToken<User>() {})
//    val route = if (user == null) Destinations.AuthGraph.route else Destinations.DrawerGraph.route
    delay(Constants.SPLASH_DELAY)
    navController.navigate(Destinations.AuthGraph.route) {
        popUpTo(Destinations.Splash.route) { inclusive = true }
        launchSingleTop = true
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}