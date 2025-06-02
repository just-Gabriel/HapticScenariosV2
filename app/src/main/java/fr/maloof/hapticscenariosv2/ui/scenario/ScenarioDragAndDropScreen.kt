package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.ui.draw.clip
import fr.maloof.hapticscenariosv2.utils.navigateToSlidersWithTest
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel

@Composable
fun ScenarioDragAndDropScreen(
    navController: NavController,
    viewModel: ScenarioViewModel = viewModel()
) {
    val test = viewModel.getCurrentTest()
    val user = viewModel.user.value
    val telephone = viewModel.telephone.value


    if (test == null || user == null || telephone == null) {
        println("❌ Données manquantes dans ScenarioDragAndDropScreen")
        return
    }

    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val marginRightPx = with(density) { 24.dp.toPx() }
    val marginLeftPx = with(density) { 24.dp.toPx() }
    val marginTopPx = with(density) { 24.dp.toPx() }
    val marginBottomPx = with(density) { 24.dp.toPx() }
    val fileWidthPx = with(density) { 100.dp.toPx() }
    val fileHeightPx = with(density) { 100.dp.toPx() }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var initialOffsetX by remember { mutableStateOf(0f) }
    var initialOffsetY by remember { mutableStateOf(0f) }

    var dropZonePosition by remember { mutableStateOf(Offset.Zero) }
    var dropZoneSize by remember { mutableStateOf(IntSize.Zero) }
    var fileSize by remember { mutableStateOf(IntSize.Zero) }
    var isDragEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Zone de dépôt
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFEEEEEE))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .onGloballyPositioned { coords ->
                    dropZonePosition = coords.positionInRoot()
                    dropZoneSize = coords.size
                },
            contentAlignment = Alignment.Center
        ) {}

        // Fichier à glisser
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF019AAF))
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                .onGloballyPositioned { coords ->
                    fileSize = coords.size
                    if (initialOffsetX == 0f && initialOffsetY == 0f) {
                        val corner = Random.nextInt(4)
                        initialOffsetX = when (corner) {
                            0, 2 -> 0f
                            else -> screenWidthPx - fileWidthPx - marginRightPx - marginLeftPx
                        }
                        initialOffsetY = when (corner) {
                            0, 1 -> marginTopPx
                            else -> screenHeightPx - fileHeightPx - marginBottomPx - marginLeftPx
                        }
                        offsetX = initialOffsetX
                        offsetY = initialOffsetY
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            if (isDragEnabled) {
                                change.consume()
                                offsetX = (offsetX + dragAmount.x).coerceIn(
                                    0f, screenWidthPx - fileSize.width - marginRightPx
                                )
                                offsetY = (offsetY + dragAmount.y).coerceIn(
                                    marginTopPx, screenHeightPx - fileSize.height - marginBottomPx
                                )
                            }
                        },
                        onDragEnd = {
                            if (!isDragEnabled) return@detectDragGestures

                            val fileCenter = Offset(offsetX + fileSize.width / 2, offsetY + fileSize.height / 2)
                            val droppedInZone =
                                fileCenter.x in dropZonePosition.x..(dropZonePosition.x + dropZoneSize.width) &&
                                        fileCenter.y in dropZonePosition.y..(dropZonePosition.y + dropZoneSize.height)

                            if (droppedInZone) {
                                isDragEnabled = false
                                test.callback.invoke()
                                scope.launch {
                                    delay(1000L)
                                    navigateToSlidersWithTest(navController, viewModel, test)
                                }
                            } else {
                                offsetX = initialOffsetX
                                offsetY = initialOffsetY
                            }
                        }
                    )
                }
        )
    }
}
