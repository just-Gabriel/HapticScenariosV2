package fr.maloof.hapticscenariosv2.utils

/*import fr.maloof.hapticscenariosv2.network.DataModel
import fr.maloof.hapticscenariosv2.network.DataModel.ScenarioVibration

object ScenarioTestManager {

    private val scenarioList = listOf("bouton", "scenario_drag", "scenario_popup")
    private var testList: List<ScenarioVibration> = emptyList()
    private var currentIndex = 0

    fun initialize(vibrationManager: VibrationManager) {
        val baseVibrations = vibrationManager.getBaseList()

        // Vérification : il faut exactement 20 vibrations de base
        if (baseVibrations.size != 20) {
            error("❌ Il faut exactement 20 vibrations de base. Actuellement : ${baseVibrations.size}")
        }
        var user: DataModel.User? = null
        var telephone: DataModel.Telephone? = null

        fun findTest(vibrationId: Int, scenario: String): DataModel.ScenarioVibration? {
            return testList.find { it.vibrationId == vibrationId && it.scenario == scenario }
        }

        val allTests = mutableListOf<ScenarioVibration>()

        for (scenario in scenarioList) {
            // Chaque scénario a 20 vibrations × 2
            val doubleList = (baseVibrations + baseVibrations).shuffled()
            if (doubleList.size != 40) error("❌ Mauvais total dans la liste pour $scenario")

            allTests += doubleList.map { (id, cb) -> ScenarioVibration(scenario, id, cb) }
        }

        // Mélange final de tous les tests
        testList = allTests.shuffled()
        currentIndex = 0

        println("🎲 Liste finale mélangée :")
        scenarioList.forEach { scenario ->
            val subset = allTests.filter { it.scenario == scenario }.map { it.vibrationId }
            println("🔹 $scenario → ${subset.size} tests (IDs: $subset)")
            val grouped = subset.groupingBy { it }.eachCount()
            val valid = grouped.all { it.value == 2 }
            println(if (valid) "✅ Répartition correcte" else "❌ Répartition incorrecte")
        }
        println("✅ ScenarioTestManager initialisé avec ${testList.size} tests")
    }

    fun getNextTest(): ScenarioVibration? {
        val test = if (currentIndex < testList.size) testList[currentIndex++] else null
        println("🔁 Prochain test = $test")
        return test
    }


    fun isFinished(): Boolean = currentIndex >= testList.size

    fun reset() {
        currentIndex = 0
    }

    fun getRemainingCount(): Int = testList.size - currentIndex



    fun getNextScenarioTest(): ScenarioVibration? {
        return if (currentIndex < testList.size) testList[currentIndex++] else null
    }

}*/

