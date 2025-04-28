package fr.maloof.hapticscenariosv2.ui.sliders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import fr.maloof.hapticscenariosv2.utils.VibrationManager

@Composable
fun SliderScreen(navController: NavController, vibrationId: Int, nextScenario: String? = null) {
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var duration by remember { mutableStateOf(0.5f) }
    var result by remember { mutableStateOf(0.5f) }
    var surprise by remember { mutableStateOf(0.5f) }
    var natural by remember { mutableStateOf(0.5f) }
    var emotion by remember { mutableStateOf(0.5f) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF4F9FC)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            // ⬆️ Contenu principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Évaluation de la vibration",
                    fontSize = 22.sp, color = Color(0xFF019AAF))
                Spacer(modifier = Modifier.height(4.dp))
                //Text("ID : $vibrationId", color = Color.Gray)

                Spacer(modifier = Modifier.height(28.dp))
                VibrationSlider("Lent", "Rapide", duration) { duration = it }
                VibrationSlider("Succès", "Échec", result) { result = it }
                VibrationSlider("Beaucoup", "Peu", surprise) { surprise = it }
                VibrationSlider("Diminution", "Augmentation", natural) { natural = it }
                VibrationSlider("Stressant", "Rassurant", emotion) { emotion = it }
            }

            // ⬇️ Bouton en bas de page
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        if (vibrationManager.isFinished()) {
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
                    Text(
                        "Suivant",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// ✅ Bien déclaré en dehors du SliderScreen
@Composable
fun VibrationSlider(leftLabel: String, rightLabel: String, value: Float, onChange: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(leftLabel, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Text(rightLabel, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        }

        Slider(
            value = value,
            onValueChange = onChange,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                activeTrackColor = Color(0xFF019AAF), // 🟦 ➔ même couleur que tes boutons
                thumbColor = Color(0xFFFFFFFF)         // 🟦 ➔ même couleur pour le bouton du slider
            )
        )
    }
}
