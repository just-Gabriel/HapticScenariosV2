package fr.maloof.hapticscenariosv2.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

class VibrationManager(private val context: Context) {

    // Gestion du service Vibrator (selon la version Android)
    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    // Liste de vibrations unique partagée
    companion object {
        private var cachedVibrationList: List<Pair<Int, () -> Unit>>? = null
        private var globalIndex = 0  }

    private var vibrationListWithId: List<Pair<Int, () -> Unit>> = emptyList()
    private var currentIndex: Int
        get() = globalIndex
        set(value) { globalIndex = value }

    var currentVibrationId: Int = 0
        private set

    private var currentVibrationCallback: (() -> Unit)? = null

    init {
        reshuffleVibrationList()
        println("✅ VibrationManager initialisé avec la liste : ${getCurrentVibrationList()}")
    }

    private fun reshuffleVibrationList() {
        if (cachedVibrationList != null) {
            vibrationListWithId = cachedVibrationList!!
            Log.d("HAPTIC_UTIL", "♻️ Liste réutilisée")
            return
        }

        val baseList = listOf(
            1 to { keyboardReleaseFeedback() },
            2 to { virtualKeyReleaseFeedback() },
            3 to { clockTickFeedback() },
            4 to { textHandleMoveFeedback() },
            5 to { gestureEndFeedback() },
            6 to { keyboardPressFeedback() },
            7 to { virtualKeyFeedback() },
            8 to { keyboardTapFeedback() },
            9 to { contextClickFeedback() },
            10 to { gestureStartFeedback() },
            11 to { confirmFeedback() },
            12 to { longPressFeedback() },
            13 to { rejectFeedback() },
            14 to { toggleOnFeedback() },
            15 to { toggleOffFeedback() },
            16 to { gestureThresholdActivateFeedback() },
            17 to { gestureThresholdDeactivateFeedback() },
            18 to { dragStartFeedback() },
            19 to { segmentTickFeedback() },
            20 to { segmentFrequentTickFeedback() }
        )

        val fullList = baseList.flatMap { listOf(it, it, it) }.toMutableList()

        var shuffled: List<Pair<Int, () -> Unit>>
        do {
            shuffled = fullList.shuffled()
        } while (hasConsecutiveDuplicates(shuffled))

        vibrationListWithId = shuffled
        cachedVibrationList = shuffled
        Log.d("HAPTIC_UTIL", "✅ Liste mélangée générée")
    }

    private fun hasConsecutiveDuplicates(list: List<Pair<Int, () -> Unit>>): Boolean {
        for (i in 1 until list.size) {
            if (list[i].first == list[i - 1].first) return true
        }
        return false
    }

    fun playNextVibration() {
        if (currentIndex >= vibrationListWithId.size) return

        val (id, vibration) = vibrationListWithId[currentIndex]
        currentVibrationId = id
        currentVibrationCallback = vibration

        Log.d("HAPTIC_UTIL", "▶️ Playing vibration ID: $currentVibrationId (index $currentIndex)")
        vibration.invoke()
        currentIndex++
    }

    fun replayCurrentVibration() {
        currentVibrationCallback?.invoke()
            ?: Log.w("HAPTIC_UTIL", "⚠️ No vibration to replay")
    }

    fun vibrateByType(type: Int) {
        Log.d("VibrationManager", "🎯 vibrateByType($type)")
        when (type) {
            1 -> keyboardReleaseFeedback()
            2 -> virtualKeyReleaseFeedback()
            3 -> clockTickFeedback()
            4 -> textHandleMoveFeedback()
            5 -> gestureEndFeedback()
            6 -> keyboardPressFeedback()
            7 -> virtualKeyFeedback()
            8 -> keyboardTapFeedback()
            9 -> contextClickFeedback()
            10 -> gestureStartFeedback()
            11 -> confirmFeedback()
            12 -> longPressFeedback()
            13 -> rejectFeedback()
            14 -> toggleOnFeedback()
            15 -> toggleOffFeedback()
            16 -> gestureThresholdActivateFeedback()
            17 -> gestureThresholdDeactivateFeedback()
            18 -> dragStartFeedback()
            19 -> segmentTickFeedback()
            20 -> segmentFrequentTickFeedback()
            else -> defaultFeedback()
        }
    }

    fun defaultFeedback() {
        vibrateOneShot(100)
    }

    fun vibratePattern(pattern: LongArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(pattern, -1)
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }

    fun vibrateOneShot(milliseconds: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(longArrayOf(0, milliseconds), -1)
        }
    }

    fun getCurrentVibrationList(): List<Int> {
        return vibrationListWithId.map { it.first }
    }

    fun isFinished(): Boolean {
        return currentIndex >= vibrationListWithId.size
    }

    // === Feedback personnalisés ===

    fun keyboardReleaseFeedback() = vibratePattern(longArrayOf(0, 50, 50, 100))
    fun virtualKeyReleaseFeedback() = vibratePattern(longArrayOf(0, 30, 30, 70))
    fun clockTickFeedback() = vibratePattern(longArrayOf(0, 10, 20, 10))
    fun textHandleMoveFeedback() = vibratePattern(longArrayOf(0, 40, 40, 80))
    fun gestureEndFeedback() = vibratePattern(longArrayOf(0, 60, 60, 120))
    fun keyboardPressFeedback() = vibrateOneShot(50)
    fun virtualKeyFeedback() = vibrateOneShot(30)
    fun keyboardTapFeedback() = vibrateOneShot(20)
    fun contextClickFeedback() = vibrateOneShot(100)
    fun gestureStartFeedback() = vibratePattern(longArrayOf(0, 70, 70, 140))
    fun confirmFeedback() = vibrateOneShot(200)
    fun longPressFeedback() = vibrateOneShot(400)
    fun rejectFeedback() = vibratePattern(longArrayOf(0, 50, 50, 50, 50, 50))
    fun toggleOnFeedback() = vibrateOneShot(150)
    fun toggleOffFeedback() = vibrateOneShot(100)
    fun gestureThresholdActivateFeedback() = vibratePattern(longArrayOf(0, 200, 50, 200))
    fun gestureThresholdDeactivateFeedback() = vibratePattern(longArrayOf(0, 200, 50, 100))
    fun dragStartFeedback() = vibratePattern(longArrayOf(0, 100, 50, 100))
    fun segmentTickFeedback() = vibrateOneShot(10)
    fun segmentFrequentTickFeedback() = vibrateOneShot(5)
}
