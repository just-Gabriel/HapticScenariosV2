package fr.maloof.hapticscenariosv2.utils

object ScenarioManager {

    private val scenarios = listOf("bouton", "scenario_drag", "scenario_popup").shuffled()
    private var currentIndex = 0

    fun getNextScenario(): String? {
        return if (currentIndex < scenarios.size) {
            scenarios[currentIndex++]
        } else null
    }

    fun reset() {
        currentIndex = 0
    }
}
