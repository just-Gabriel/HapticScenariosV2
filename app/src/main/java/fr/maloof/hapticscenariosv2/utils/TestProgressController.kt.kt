package fr.maloof.hapticscenariosv2.utils

import androidx.compose.runtime.mutableStateOf

object TestProgressController {
    var completedTests = mutableStateOf(0)
    val totalTests = 60

    fun reset() {
        completedTests.value = 0
    }

    fun increment() {
        completedTests.value++
    }

    fun isFinished(): Boolean {
        return completedTests.value >= totalTests
    }

    fun remaining(): Int {
        return totalTests - completedTests.value
    }
}

