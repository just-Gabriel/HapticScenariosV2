package fr.maloof.hapticscenariosv2.ui.form

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.maloof.hapticscenariosv2.network.ServiceLocator
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.utils.TestProgressController
import fr.maloof.hapticscenariosv2.utils.VibrationManager
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel


@Composable
fun UserFormScreen(
    vibrationManager: VibrationManager,
    viewModel: ScenarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onFormSubmit: () -> Unit
) {


    // Champs utilisateur
    var age by remember { mutableStateOf("25") }
    var sexe by remember { mutableStateOf("Homme") }
    var mainDominante by remember { mutableStateOf("Droite") }
    var password by remember { mutableStateOf("gab Noel") }
    var paysResidence by remember { mutableStateOf("France") }
    var profession by remember { mutableStateOf("Développeur") }
    var isVibrationTelActive by remember { mutableStateOf(false) }
    var isVibrationClavierActive by remember { mutableStateOf(false) }
    var isCoqueTelActive by remember { mutableStateOf(false) }
    var niveauInformatique by remember { mutableStateOf(2f) }

    // Champs téléphone
    var phoneBrand by remember { mutableStateOf("Android") }
    var phoneModel by remember { mutableStateOf("Samsung s22") }
    var phoneVersion by remember { mutableStateOf(" 13") }
    var phoneModelNumber by remember { mutableStateOf("SM-S901B") }

    val scrollState = rememberScrollState()
    var isButtonEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Formulaire utilisateur",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Âge") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = sexe,
            onValueChange = { sexe = it },
            label = { Text("Genre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Nom du superviseur") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = paysResidence,
            onValueChange = { paysResidence = it },
            label = { Text("Pays de résidence") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = profession,
            onValueChange = { profession = it },
            label = { Text("Profession") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Main dominante :", fontWeight = FontWeight.Medium)

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = mainDominante == "Droite",
                onClick = { mainDominante = "Droite" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF029AAF) // Ton bleu à toi
                )
            )
            Text("Droite", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = mainDominante == "Gauche",
                onClick = { mainDominante = "Gauche" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF029AAF)
                )
            )
            Text("Gauche")
        }


        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isVibrationTelActive,
                onCheckedChange = { isVibrationTelActive = it })
            Text("Vibration téléphone activée")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isVibrationClavierActive,
                onCheckedChange = { isVibrationClavierActive = it })
            Text("Vibration clavier activée")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCoqueTelActive, onCheckedChange = { isCoqueTelActive = it })
            Text("Coque de téléphone")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Niveau informatique : ${niveauInformatique.toInt()}")
        Slider(
            value = niveauInformatique,
            onValueChange = { niveauInformatique = it },
            valueRange = 0f..5f,
            steps = 4,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF029AAF),
                activeTrackColor = Color(0xFF029AAF)

            )

        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Informations sur le téléphone personnel",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = phoneBrand,
            onValueChange = { phoneBrand = it },
            label = { Text("Système d’exploitation du téléphone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneModel,
            onValueChange = { phoneModel = it },
            label = { Text("Modèle téléphone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneVersion,
            onValueChange = { phoneVersion = it },
            label = { Text("Version logicielle") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phoneModelNumber,
            onValueChange = { phoneModelNumber = it },
            label = { Text("Numéro de modèle") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                isButtonEnabled = false

                val user = DataModel.User(
                    age = age.toIntOrNull() ?: 0,
                    sexe = sexe,
                    mainDominante = mainDominante,
                    password = password,
                    paysResidence = paysResidence,
                    profession = profession,
                    vibrationTelActive = isVibrationTelActive,
                    vibrationClavierActive = isVibrationClavierActive,
                    coqueTel = isCoqueTelActive,
                    niveauInformatique = niveauInformatique.toInt()
                )

                val telephone = DataModel.Telephone(
                    marque = phoneBrand,
                    modele = phoneModel,
                    versionLogiciel = phoneVersion,
                    numeroModele = phoneModelNumber
                )

                // ✅ Envoie avec gestion correcte des objets retournés
                ServiceLocator.apiService.createUser(user)
                    .enqueue(object : retrofit2.Callback<DataModel.User> {
                        override fun onResponse(
                            call: retrofit2.Call<DataModel.User>,
                            response: retrofit2.Response<DataModel.User>
                        ) {
                            val savedUser = response.body()
                            if (savedUser != null) {
                                Log.d("RETROFIT", "✅ Utilisateur envoyé - ID : ${savedUser.id}")

                                ServiceLocator.apiService.createTelephone(telephone)
                                    .enqueue(object : retrofit2.Callback<DataModel.Telephone> {
                                        override fun onResponse(
                                            call: retrofit2.Call<DataModel.Telephone>,
                                            response: retrofit2.Response<DataModel.Telephone>
                                        ) {
                                            val savedTelephone = response.body()
                                            if (savedTelephone != null) {
                                                Log.d("RETROFIT", "✅ Téléphone envoyé - ID : ${savedTelephone.id}")
                                                Log.d("INIT", "🧠 Initialisation des scénarios en cours...")

                                                // Initialisation de la liste des tests scénarios + vibrations
                                                viewModel.user.value = savedUser
                                                viewModel.telephone.value = savedTelephone


                                                // ✅ Réinitialise les compteurs et les tests
                                                TestProgressController.reset()
                                                viewModel.reset()
                                                viewModel.initialize(vibrationManager)
                                                Log.d("INIT_TESTS", "✅ ViewModel initialisé : ${viewModel.getRemainingCount()} tests prêts")

                                                onFormSubmit()


                                            }
                                            else {
                                                Log.e("RETROFIT", "❌ Téléphone null après POST")
                                                isButtonEnabled = true
                                            }
                                        }

                                        override fun onFailure(
                                            call: retrofit2.Call<DataModel.Telephone>,
                                            t: Throwable
                                        ) {
                                            Log.e("RETROFIT", "❌ Téléphone échoué : ${t.message}")
                                            isButtonEnabled = true
                                        }
                                    })
                            } else {
                                Log.e("RETROFIT", "❌ Utilisateur null après POST")
                                isButtonEnabled = true
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<DataModel.User>, t: Throwable) {
                            Log.e("RETROFIT", "❌ Utilisateur échoué : ${t.message}")
                            isButtonEnabled = true
                        }
                    })
            },
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF019AAF),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuer")
        }
    }}



