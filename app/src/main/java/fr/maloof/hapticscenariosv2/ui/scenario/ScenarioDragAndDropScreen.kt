package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import kotlin.random.Random
import androidx.compose.ui.draw.clip


@Composable
fun ScenarioDragAndDropScreen(navController: NavController) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    var initialOffsetX by remember { mutableStateOf(0f) }
    var initialOffsetY by remember { mutableStateOf(0f) }

    var dropZonePosition by remember { mutableStateOf(Offset.Zero) }
    var dropZoneSize by remember { mutableStateOf(IntSize.Zero) }

    var fileSize by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // ✅ Zone de dépôt
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFEEEEEE))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .onGloballyPositioned { coords ->
                    val localOffset = coords.positionInRoot()
                    dropZonePosition = Offset(localOffset.x, localOffset.y)
                    dropZoneSize = coords.size
                },
            contentAlignment = Alignment.Center
        ) {}

        // ✅ Fichier à glisser
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF019AAF)) // 🟦 Ta couleur
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .onGloballyPositioned { coords ->
                    fileSize = coords.size
                    if (initialOffsetX == 0f && initialOffsetY == 0f) {
                        val corner = Random.nextInt(4)
                        val marginPx = with(density) { 16.dp.toPx() }
                        val fileWidthPx = with(density) { 100.dp.toPx() }
                        val fileHeightPx = with(density) { 100.dp.toPx() }

                        when (corner) {
                            0 -> { // Haut gauche
                                initialOffsetX = marginPx
                                initialOffsetY = marginPx
                            }
                            1 -> { // Haut droit
                                initialOffsetX = (screenWidthPx - fileWidthPx - marginPx).coerceAtLeast(marginPx)
                                initialOffsetY = marginPx
                            }
                            2 -> { // Bas gauche
                                initialOffsetX = marginPx
                                initialOffsetY = (screenHeightPx - fileHeightPx - marginPx).coerceAtLeast(marginPx)
                            }
                            3 -> { // Bas droit
                                initialOffsetX = (screenWidthPx - fileWidthPx - marginPx).coerceAtLeast(marginPx)
                                initialOffsetY = (screenHeightPx - fileHeightPx - marginPx).coerceAtLeast(marginPx)
                            }
                        }
                        offsetX = initialOffsetX
                        offsetY = initialOffsetY


                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        },
                        onDragEnd = {
                            val fileCenter = Offset(
                                offsetX + fileSize.width / 2,
                                offsetY + fileSize.height / 2
                            )
                            val droppedInZone =
                                fileCenter.x in dropZonePosition.x..(dropZonePosition.x + dropZoneSize.width) &&
                                        fileCenter.y in dropZonePosition.y..(dropZonePosition.y + dropZoneSize.height)

                            if (droppedInZone) {
                                vibrationManager.playNextVibration()
                                val vibrationId = vibrationManager.currentVibrationId
                                val nextScenario = ScenarioController.getRandomScenario()
                                navController.navigate("sliders/$vibrationId/$nextScenario")
                            } else {
                                // Retourner à la position initiale
                                offsetX = initialOffsetX
                                offsetY = initialOffsetY
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {}
    }
}
