package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.maloof.hapticscenariosv2.utils.navigateToSlidersWithTest
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel

@Composable
fun ScenarioPopupScreen(
    navController: NavController,
    viewModel: ScenarioViewModel = viewModel()
) {
    val test = viewModel.getCurrentTest()
    val user = viewModel.user.value
    val telephone = viewModel.telephone.value


    var showPopup by remember { mutableStateOf(false) }
    var navigateToNext by remember { mutableStateOf(false) }

    if (test == null || user == null || telephone == null) {
        println("⚠️ Données manquantes dans ScenarioPopupScreen")
        return
    }

    LaunchedEffect(Unit) {
        println("✅ ScenarioPopup lancé pour vibration ${test.vibrationId}")

        delay(1500)
        showPopup = true
        test.callback.invoke()

        delay(1500)
        navigateToNext = true
    }

    if (navigateToNext) {
        LaunchedEffect(Unit) {
            navigateToSlidersWithTest(navController, viewModel, test)
        }
    }

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
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xFF019AAF))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Gray.copy(alpha = 0.4f))
                        )
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Gray.copy(alpha = 0.3f))
                        )
                    }
                }
            }
        }
    }
}
