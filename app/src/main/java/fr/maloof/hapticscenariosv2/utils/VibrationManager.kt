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

    // === Liste des vibrations associées à un ID ===
    private var vibrationListWithId: List<Pair<Int, () -> Unit>> = emptyList()
    private var currentIndex = 0
    var currentVibrationId: Int = 0
        private set

    private var currentVibrationCallback: (() -> Unit)? = null

    // === Initialisation de la liste mélangée au démarrage ===
    init {
        reshuffleVibrationList()
    }

    // Mélange la liste des vibrations
    private fun reshuffleVibrationList() {
        vibrationListWithId = listOf(
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
        ).shuffled()
        currentIndex = 0
    }

    // Joue la prochaine vibration (et reshuffle si fin de liste atteinte)
    fun playNextVibration() {
        if (currentIndex >= vibrationListWithId.size) {
            reshuffleVibrationList()
        }

        val (id, vibration) = vibrationListWithId[currentIndex]
        currentVibrationId = id
        currentVibrationCallback = vibration

        Log.d("HAPTIC_UTIL", "▶️ Playing vibration ID: $currentVibrationId")
        vibration.invoke()
        currentIndex++
    }

    // Rejoue la vibration actuelle (ex: dans le scénario)
    fun replayCurrentVibration() {
        currentVibrationCallback?.invoke()
            ?: Log.w("HAPTIC_UTIL", "⚠️ No vibration to replay")
    }

    // Si jamais tu appelles une vibration manuelle par type
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


    // === Méthodes de base ===
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
    //___________________________________________________________________________________________________________________________________________________________
    fun getCurrentVibrationList(): List<Int> {
        return vibrationListWithId.map { it.first }
    }

    fun isFinished(): Boolean {
        return currentIndex >= vibrationListWithId.size
    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    // === Feedback personnalisés (patterns ou durées) ===
    fun keyboardReleaseFeedback() {
        val pattern = longArrayOf(0, 50, 50, 100)
        vibratePattern(pattern)
    }

    // VIRTUAL_KEY_RELEASE
    fun virtualKeyReleaseFeedback() {
        val pattern = longArrayOf(0, 30, 30, 70)
        vibratePattern(pattern)
    }

    // CLOCK_TICK
    fun clockTickFeedback() {
        val pattern = longArrayOf(0, 10, 20, 10)
        vibratePattern(pattern)
    }

    // TEXT_HANDLE_MOVE
    fun textHandleMoveFeedback() {
        val pattern = longArrayOf(0, 40, 40, 80)
        vibratePattern(pattern)
    }

    //GESTURE_END
    fun gestureEndFeedback() {
        val pattern = longArrayOf(0, 60, 60, 120)
        vibratePattern(pattern)
    }

    // KEYBOARD_PRESS
    fun keyboardPressFeedback() {
        vibrateOneShot(50)
    }

    // VIRTUAL_KEY
    fun virtualKeyFeedback() {
        vibrateOneShot(30)
    }

    // KEYBOARD_TAP
    fun keyboardTapFeedback() {
        vibrateOneShot(20)
    }

    // CONTEXT_CLICK
    fun contextClickFeedback() {
        vibrateOneShot(100)
    }

    // GESTURE_START
    fun gestureStartFeedback() {
        val pattern = longArrayOf(0, 70, 70, 140)
        vibratePattern(pattern)
    }

    // CONFIRM
    fun confirmFeedback() {
        vibrateOneShot(200)
    }

    //r LONG_PRESS
    fun longPressFeedback() {
        vibrateOneShot(400)
    }

    // REJECT
    fun rejectFeedback() {
        val pattern = longArrayOf(0, 50, 50, 50, 50, 50)
        vibratePattern(pattern)
    }

    // TOGGLE_ON
    fun toggleOnFeedback() {
        vibrateOneShot(150)
    }

    //TOGGLE_OFF
    fun toggleOffFeedback() {
        vibrateOneShot(100)
    }

    // GESTURE_THRESHOLD_ACTIVATE
    fun gestureThresholdActivateFeedback() {
        val pattern = longArrayOf(0, 200, 50, 200)
        vibratePattern(pattern)
    }

    // GESTURE_THRESHOLD_DEACTIVATE
    fun gestureThresholdDeactivateFeedback() {
        val pattern = longArrayOf(0, 200, 50, 100)
        vibratePattern(pattern)
    }

    //  DRAG_START
    fun dragStartFeedback() {
        val pattern = longArrayOf(0, 100, 50, 100)
        vibratePattern(pattern)
    }

    // SEGMENT_TICK
    fun segmentTickFeedback() {
        vibrateOneShot(10)
    }

    // SEGMENT_FREQUENT_TICK
    fun segmentFrequentTickFeedback() {
        vibrateOneShot(5)
    }
}
