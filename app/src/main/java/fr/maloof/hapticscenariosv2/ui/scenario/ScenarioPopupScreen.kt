package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import kotlinx.coroutines.delay
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext

@Composable
fun ScenarioPopupScreen(navController: NavController) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var showPopup by remember { mutableStateOf(false) }
    var navigateToNext by remember { mutableStateOf(false) }
    val nextScenario = ScenarioController.getRandomScenario()

    LaunchedEffect(Unit) {
        delay(1200) // Délai pour afficher le popup
        showPopup = true
        vibrationManager.playNextVibration()

        delay(2500) // Délai avant de naviguer vers les sliders
        navigateToNext = true
    }

    if (navigateToNext) {
        LaunchedEffect(Unit) {
            navController.navigate("sliders/${vibrationManager.currentVibrationId}/$nextScenario")
        }
    }

    // Fond d'écran
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showPopup,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 500)
            ) + fadeIn(animationSpec = tween(500))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(200.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)) // L'ombre doit être avant le clip
                    .clip(RoundedCornerShape(16.dp)) // Coupe les bords arrondis
                    .background(Color.White), // Fond blanc

                contentAlignment = Alignment.Center
            ) {
                // Notification "vide" façon iOS
                Row(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF800080)) // Violet
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Gray.copy(alpha = 0.4f))
                        )
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Gray.copy(alpha = 0.3f))
                        )
                    }
                }
            }
        }
    }
}
