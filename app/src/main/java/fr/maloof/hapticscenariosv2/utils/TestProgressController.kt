package fr.maloof.hapticscenariosv2.utils

object TestProgressController {
    private var completedTraining = 0
    private var completedReal = 0

    private const val TOTAL_TRAINING = 20
    private const val TOTAL_REAL = 120

    private var isTraining = true

    fun reset() {
        completedTraining = 0
        completedReal = 0
        isTraining = true
    }

    fun startRealTests() {
        isTraining = false
    }

    fun increment(isTrainingTest: Boolean) {
        if (isTrainingTest) {
            completedTraining++
        } else {
            completedReal++
        }
    }

    fun remaining(): Int {
        return if (isTraining) TOTAL_TRAINING - completedTraining else TOTAL_REAL - completedReal
    }

    fun isInTraining(): Boolean = isTraining

    fun finishTrainingIfNeeded() {
        if (completedTraining >= TOTAL_TRAINING) {
            isTraining = false
        }
    }

    fun isTrainingFinished(): Boolean = completedTraining >= TOTAL_TRAINING
    fun isRealFinished(): Boolean = completedReal >= TOTAL_REAL
}
