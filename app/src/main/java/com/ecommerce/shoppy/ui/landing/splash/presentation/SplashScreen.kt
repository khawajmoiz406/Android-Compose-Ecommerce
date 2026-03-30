import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ecommerce.shoppy.R
import com.ecommerce.shoppy.config.components.image.SvgImage
import com.ecommerce.shoppy.config.navigation.Destination
import com.ecommerce.shoppy.config.utils.AppCompositionLocals.LocalParentNavController
import com.ecommerce.shoppy.config.utils.Constants
import com.ecommerce.shoppy.core.pref.SharedPrefUtils
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen() {
    val roundedCorners = RoundedCornerShape(16.sdp)
    val navController = LocalParentNavController.current

    val iconScale = remember { Animatable(0f) }
    val textScale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(150)

        launch {
            iconScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        }

        snapshotFlow { iconScale.value }.first { it > 0.99f }

        textScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )

        navController?.let { handleSplash(it) }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(90.sdp)
                    .scale(iconScale.value)
                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f), roundedCorners)
                    .border(1.sdp, MaterialTheme.colorScheme.onPrimary, roundedCorners)
            ) {
                SvgImage(
                    asset = "shoppy_bag",
                    modifier = Modifier.size(45.sdp)
                )
            }

            Spacer(Modifier.height(15.sdp))

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 28.ssp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.scale(textScale.value),
                color = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(Modifier.height(3.sdp))

            Text(
                text = stringResource(R.string.app_name_caption),
                color = MaterialTheme.colorScheme.outlineVariant,
                fontSize = 13.ssp,
                textAlign = TextAlign.Center,
                modifier = Modifier.scale(textScale.value)
            )
        }
    }
}

private suspend fun handleSplash(navController: NavController) {
    val user = SharedPrefUtils.getCurrentUser(navController.context)

    delay(Constants.SPLASH_DELAY)

    val route = if (user == null) Destination.AuthGraph else Destination.DrawerGraph
    navController.navigate(route) {
        popUpTo(Destination.Splash) { inclusive = true }
        launchSingleTop = true
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen()
}