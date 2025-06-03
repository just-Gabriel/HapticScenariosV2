package fr.maloof.hapticscenariosv2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.utils.VibrationManager

class ScenarioViewModel : ViewModel() {

    private val scenarioList = listOf("bouton", "scenario_drag", "scenario_popup")

    private val allTests = mutableListOf<DataModel.ScenarioVibration>()
    private var currentIndex = 0
    private var currentTest: DataModel.ScenarioVibration? = null

    fun prepareNextTest(): Boolean {
        if (currentIndex >= allTests.size) return false
        currentTest = allTests[currentIndex++]
        return true
    }

    fun getCurrentTest(): DataModel.ScenarioVibration? = currentTest


    var user = mutableStateOf<DataModel.User?>(null)
    var telephone = mutableStateOf<DataModel.Telephone?>(null)

    fun initialize(vibrationManager: VibrationManager) {
        val baseVibrations = vibrationManager.getBaseList()

        if (baseVibrations.size != 20) {
            error("❌ Il faut exactement 20 vibrations de base. Actuellement : ${baseVibrations.size}")
        }

        val trainingTests = baseVibrations.shuffled().map { (id, cb) ->
            val scenario = scenarioList.random()
            DataModel.ScenarioVibration(scenario, id, cb, isTraining = true)
        }

        val tempTests = mutableListOf<DataModel.ScenarioVibration>()

        for (scenario in scenarioList) {
            val doubleList = (baseVibrations + baseVibrations).shuffled()
            if (doubleList.size != 40) error("❌ Mauvais total dans la liste pour $scenario")

            tempTests += doubleList.map { (id, cb) ->
                DataModel.ScenarioVibration(scenario, id, cb, isTraining = false)
            }
        }

        allTests.clear()
        allTests.addAll(trainingTests + tempTests.shuffled()) // 🧠 Training d'abord, puis vrais tests mélangés
        currentIndex = 0

        println("🎯 20 tests d'entraînement + 120 vrais tests générés")
        println("✅ Entraînement : ${trainingTests.size} tests")
        println("✅ Réels : ${tempTests.size} tests")

        scenarioList.forEach { scenario ->
            val ids = tempTests.filter { it.scenario == scenario }.map { it.vibrationId }
            val valid = ids.groupingBy { it }.eachCount().all { it.value == 2 }
            println("🔹 $scenario → ${ids.size} tests (IDs: $ids)")
            println(if (valid) "✅ Répartition correcte" else "❌ Répartition incorrecte")
        }
    }



    fun getNextTest(): DataModel.ScenarioVibration? {
        return if (currentIndex < allTests.size) allTests[currentIndex++] else null
    }

    fun findTest(vibrationId: Int, scenario: String): DataModel.ScenarioVibration? {
        return allTests.find { it.vibrationId == vibrationId && it.scenario == scenario }
    }

    fun reset() {
        currentIndex = 0
        allTests.clear()
    }

    fun isFinished(): Boolean = currentIndex >= allTests.size

    fun getRemainingCount(): Int = allTests.size - currentIndex

    fun findCurrentTest(): DataModel.ScenarioVibration? {
        return if (currentIndex > 0) allTests[currentIndex - 1] else null
    }

}
