package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape




@Composable
fun ScenarioDragAndDropScreen(navController: NavController) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

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
                .background(Color(0xFFEEEEEE)) // gris clair
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .onGloballyPositioned { coords ->
                    val localOffset = coords.positionInRoot()
                    dropZonePosition = Offset(localOffset.x, localOffset.y)
                    dropZoneSize = coords.size
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.CloudUpload,
                    contentDescription = "Déposer ici",
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Déposez ici", color = Color.Gray)
            }
        }



        // ✅ Fichier à glisser
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF019AAF))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .onGloballyPositioned { coords -> fileSize = coords.size }
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
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = "Fichier",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }}}



