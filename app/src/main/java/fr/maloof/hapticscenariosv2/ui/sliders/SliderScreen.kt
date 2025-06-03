package fr.maloof.hapticscenariosv2.ui.sliders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.network.ServiceLocator
import fr.maloof.hapticscenariosv2.utils.TestProgressController
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ... imports inchangés ...

// ... imports inchangés ...

@Composable
fun SliderScreen(
    navController: NavController,
    viewModel: ScenarioViewModel = viewModel()
) {
    val user = viewModel.user.value
    val telephone = viewModel.telephone.value
    val test = viewModel.getCurrentTest()

    if (user == null || telephone == null || test == null) {
        println("❌ Données manquantes dans SliderScreen")
        return
    }

    val context = LocalContext.current

    var lentRapide by remember { mutableStateOf(2) }
    var echecSucces by remember { mutableStateOf(2) }
    var peuBeaucoup by remember { mutableStateOf(2) }
    var diminutionAugmentation by remember { mutableStateOf(2) }
    var douxTranchant by remember { mutableStateOf(2) }

    var isButtonEnabled by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF4F9FC)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Évaluation de la vibration", fontSize = 22.sp, color = Color(0xFF019AAF))
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (test.isTraining) {
                        "Tests d'entraînement restants : ${TestProgressController.remaining()}"
                    } else {
                        "Tests restants : ${TestProgressController.remaining()}"
                    },
                    fontSize = 18.sp,
                    color = Color(0xFF019AAF)
                )
                Spacer(modifier = Modifier.height(32.dp))

                ThreeStateToggle("Lent", "Rapide", lentRapide) { lentRapide = it }
                ThreeStateToggle("Échec", "Succès", echecSucces) { echecSucces = it }
                ThreeStateToggle("Peu", "Beaucoup", peuBeaucoup) { peuBeaucoup = it }
                ThreeStateToggle("Diminution", "Augmentation", diminutionAugmentation) { diminutionAugmentation = it }
                ThreeStateToggle("Doux", "Tranchant", douxTranchant) { douxTranchant = it }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        if (!isButtonEnabled) return@Button
                        isButtonEnabled = false
                        isLoading = true

                        TestProgressController.increment(test.isTraining)

                        if (test.isTraining) {
                            println("🧪 Test d'entraînement, pas d'envoi en BDD")
                            TestProgressController.finishTrainingIfNeeded()
                            navController.navigate("start")
                        } else {
                            val experience = DataModel.EmotionalExperience(
                                user = "/api/users/${user.id}",
                                telephone = "/api/telephones/${telephone.id}",
                                slider1 = lentRapide,
                                slider2 = echecSucces,
                                slider3 = peuBeaucoup,
                                slider4 = diminutionAugmentation,
                                slider5 = douxTranchant,
                                scenario = test.scenario,
                                vibrationId = test.vibrationId,
                                mobile = 0
                            )

                            ServiceLocator.apiService.postEmotionalExperience(experience)
                                .enqueue(object : Callback<DataModel.EmotionalExperience> {
                                    override fun onResponse(
                                        call: Call<DataModel.EmotionalExperience>,
                                        response: Response<DataModel.EmotionalExperience>
                                    ) {
                                        if (response.isSuccessful) {
                                            navController.navigate("start")
                                        } else {
                                            isButtonEnabled = true
                                            isLoading = false
                                        }
                                    }

                                    override fun onFailure(call: Call<DataModel.EmotionalExperience>, t: Throwable) {
                                        isButtonEnabled = true
                                        isLoading = false
                                    }
                                })
                        }
                    },
                    enabled = isButtonEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF019AAF),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(220.dp)
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Suivant", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}




@Composable
fun ThreeStateToggle(
    leftLabel: String,
    rightLabel: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..3).forEach { position ->
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (value == position) Color(0xFF019AAF) else Color.White)
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .clickable { onValueChange(position) },
                    contentAlignment = Alignment.Center
                ) {}
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(leftLabel, color = Color.Gray, fontSize = 12.sp)
            Text("Sans avis", color = Color.Gray, fontSize = 12.sp)
            Text(rightLabel, color = Color.Gray, fontSize = 12.sp)
        }
    }
}


