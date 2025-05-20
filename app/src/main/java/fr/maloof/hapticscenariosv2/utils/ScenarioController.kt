package fr.maloof.hapticscenariosv2.utils

object ScenarioController {

    private val scenarios = listOf("bouton", "scenario_drag", "scenario_popup")

    fun getRandomScenario(): String {
        return scenarios.random()
    }
}