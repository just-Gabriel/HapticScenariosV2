package fr.maloof.hapticscenariosv2.ui.scenario

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.maloof.hapticscenariosv2.viewmodel.ScenarioViewModel

@Composable
fun EndScreen(
    navController: NavController,
    viewModel: ScenarioViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🎉 Test terminé ! Merci pour votre participation.",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.reset()
                    navController.navigate("userForm") {
                        popUpTo(0) // ⚠️ vide toute la backstack
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF019AAF),
                    contentColor =  Color.White,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text("Recommencer")
            }
        }
    }
}
