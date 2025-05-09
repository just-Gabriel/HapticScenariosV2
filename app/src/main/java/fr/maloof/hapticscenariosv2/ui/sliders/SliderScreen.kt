package fr.maloof.hapticscenariosv2.ui.sliders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.utils.ScenarioController
import fr.maloof.hapticscenariosv2.utils.TestProgressController
import fr.maloof.hapticscenariosv2.utils.VibrationManager

@Composable
fun SliderScreen(navController: NavController, vibrationId: Int, nextScenario: String? = null) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    // Valeurs binaire
    var duration by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf(0) }
    var surprise by remember { mutableStateOf(0) }
    var natural by remember { mutableStateOf(0) }
    var emotion by remember { mutableStateOf(0) }

    val completedTests = TestProgressController.completedTests.value
    val totalTests = TestProgressController.totalTests
    val remainingTests = TestProgressController.remaining()


// Affichage dynamique du compteur
    Text(
        text = "Tests restants : ${TestProgressController.remaining()}",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF019AAF)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F9FC)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Évaluation de la vibration",
                    fontSize = 22.sp,
                    color = Color(0xFF019AAF)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Tests restants : $remainingTests",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF019AAF)
                )

                Spacer(modifier = Modifier.height(50.dp))


                // Les toggles avec valeurs 0f / 1f
                VibrationToggle("Lent", "Rapide", duration == 1) { duration = if (it) 1 else 0 }
                VibrationToggle("Échec", "Succès", result == 1) { result = if (it) 1 else 0 }
                VibrationToggle("Peu", "Beaucoup", surprise == 1) { surprise = if (it) 1 else 0 }
                VibrationToggle("Diminution", "Augmentation", natural == 1) { natural = if (it) 1 else 0 }
                VibrationToggle("Doux", "Tranchant", emotion == 1) { emotion = if (it) 1 else 0 }}
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        TestProgressController.increment()


                        println("=== Résumé des choix de l'utilisateur ===")
                        println("Lent/Rapide: $duration")
                        println("Échec/Succès: $result")
                        println("Peu/Beaucoup: $surprise")
                        println("Diminution/Augmentation: $natural")
                        println("Doux/Tranchant: $emotion")
                        println("Test validé $completedTests / $totalTests")
                        println("compteur de test  $remainingTests")

                        if (TestProgressController.isFinished()) {
                            navController.navigate("end")

                        } else {
                            val next = nextScenario ?: ScenarioController.getRandomScenario()
                            navController.navigate(next)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF019AAF),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(220.dp)
                        .height(56.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                ) {
                    Text("Suivant", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }


@Composable
fun VibrationToggle(leftLabel: String, rightLabel: String, value: Boolean, onToggle: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            leftLabel,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Switch(
            checked = value,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF019AAF),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            ),
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            rightLabel,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
