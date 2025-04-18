package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import kotlinx.coroutines.delay
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun ScenarioPopupScreen(navController: NavController) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var showPopup by remember { mutableStateOf(false) }
    val nextScenario = ScenarioController.getRandomScenario()

    // Lancer la vibration et le popup après délai
    LaunchedEffect(Unit) {
        delay(1000)
        showPopup = true
        vibrationManager.playNextVibration()
    }

    // Page de fond statique
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(32.dp))

            AnimatedVisibility(
                visible = showPopup,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(500))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(200.dp), // Tu peux ajuster la hauteur
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center // ✅ Centrage vertical
                    ) {
                        Text("💬 Alerte", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                showPopup = false
                                navController.navigate("sliders/${vibrationManager.currentVibrationId}/$nextScenario")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00C6FF),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .width(200.dp)
                                .height(50.dp)
                                .border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(50))
                                .shadow(8.dp, shape = RoundedCornerShape(50)) // ✅ ombre douce
                        ) {
                            Text(
                                "Continuer",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}









