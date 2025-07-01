package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import fr.maloof.hapticscenariosv2.utils.navigateToSlidersWithTest
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ScenarioButton(
    navController: NavController,
    viewModel: ScenarioViewModel = viewModel()
) {
    val test = viewModel.getCurrentTest()
    val user = viewModel.user.value
    val telephone = viewModel.telephone.value


    if (test == null || user == null || telephone == null) {
        println("❌ Données manquantes dans ScenarioButton")
        return
    }

    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(true) }

    val callback = vibrationManager.getCallbackForId(test.vibrationId)

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.03f else 1f,
        animationSpec = tween(150), label = "scale"
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
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF019AAF))
                .clickable(
                    enabled = isClickable,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    if (test.scenario == "button") {
                        isPressed = true
                        isClickable = false
                        callback.invoke()

                        scope.launch {
                            delay(1000L)
                            navigateToSlidersWithTest(navController, viewModel, test)
                        }
                    } else {
                        navController.navigate("test_termine")
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Button",
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
