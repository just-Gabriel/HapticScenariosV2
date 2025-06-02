package fr.maloof.hapticscenariosv2.utils

object TestProgressController {
    private var totalTests = 120
    private var completed = 0

    fun reset() {
        completed = 0
    }

    fun increment() {
        completed++
    }

    fun isFinished(): Boolean = completed >= totalTests

    fun remaining(): Int = totalTests - completed
}


