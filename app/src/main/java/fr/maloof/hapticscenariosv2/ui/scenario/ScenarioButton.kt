package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.drawBehind

@Composable
fun ScenarioButton(navController: NavController) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.03f else 1f,
        animationSpec = tween(150),
        label = "scale"
    )

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(150)
            isPressed = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .width(220.dp)
                .height(56.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp)) // ➔ arrondi plus petit
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF019AAF))
                .clickable {
                    isPressed = true
                    vibrationManager.playNextVibration()
                    val vibrationId = vibrationManager.currentVibrationId
                    val nextScenario = ScenarioController.getRandomScenario()
                    navController.navigate("sliders/$vibrationId/$nextScenario")
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Bouton", // ➔ uniforme avec iOS
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
