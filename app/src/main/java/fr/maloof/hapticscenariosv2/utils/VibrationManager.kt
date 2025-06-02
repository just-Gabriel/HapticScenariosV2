package fr.maloof.hapticscenariosv2.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

class VibrationManager(private val context: Context) {

    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    // === Méthodes de vibration personnalisées ===

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

    // === Méthodes utilitaires ===

    fun vibratePattern(pattern: LongArray) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }

    fun vibrateOneShot(milliseconds: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(longArrayOf(0, milliseconds), -1)
        }
    }

    fun defaultFeedback() {
        vibrateOneShot(100)
    }
    fun getCallbackForId(id: Int): () -> Unit {
        return when (id) {
            1 -> ::keyboardReleaseFeedback
            2 -> ::virtualKeyReleaseFeedback
            3 -> ::clockTickFeedback
            4 -> ::textHandleMoveFeedback
            5 -> ::gestureEndFeedback
            6 -> ::virtualKeyFeedback
            7 -> ::keyboardPressFeedback
            8 -> ::dragStartFeedback
            9 -> ::contextClickFeedback
            10 -> ::gestureStartFeedback
            11 -> ::confirmFeedback
            12 -> ::longPressFeedback
            13 -> ::rejectFeedback
            14 -> ::toggleOnFeedback
            15 -> ::toggleOffFeedback
            16 -> ::gestureThresholdActivateFeedback
            17 -> ::gestureThresholdDeactivateFeedback
            18 -> ::keyboardTapFeedback
            19 -> ::segmentTickFeedback
            20 -> ::segmentFrequentTickFeedback
            else -> { {} } // Par défaut : ne fait rien
        }
    }
    // === Pour ScenarioTestManager ===

    fun getBaseList(): List<Pair<Int, () -> Unit>> {
        return listOf(
            1 to ::keyboardReleaseFeedback,
            2 to ::virtualKeyReleaseFeedback,
            3 to ::clockTickFeedback,
            4 to ::textHandleMoveFeedback,
            5 to ::gestureEndFeedback,
            6 to ::virtualKeyFeedback,
            7 to ::keyboardPressFeedback,
            8 to ::dragStartFeedback,
            9 to ::contextClickFeedback,
            10 to ::gestureStartFeedback,
            11 to ::confirmFeedback,
            12 to ::longPressFeedback,
            13 to ::rejectFeedback,
            14 to ::toggleOnFeedback,
            15 to ::toggleOffFeedback,
            16 to ::gestureThresholdActivateFeedback,
            17 to ::gestureThresholdDeactivateFeedback,
            18 to ::keyboardTapFeedback,
            19 to ::segmentTickFeedback,
            20 to ::segmentFrequentTickFeedback
        )
    }

    fun vibrateByType(type: Int) {
        Log.d("VibrationManager", "🎯 vibrateByType($type)")
        when (type) {
            1 -> keyboardReleaseFeedback()
            2 -> virtualKeyReleaseFeedback()
            3 -> clockTickFeedback()
            4 -> textHandleMoveFeedback()
            5 -> gestureEndFeedback()
            6 -> virtualKeyFeedback()
            7 -> keyboardPressFeedback()
            8 -> dragStartFeedback()
            9 -> contextClickFeedback()
            10 -> gestureStartFeedback()
            11 -> confirmFeedback()
            12 -> longPressFeedback()
            13 -> rejectFeedback()
            14 -> toggleOnFeedback()
            15 -> toggleOffFeedback()
            16 -> gestureThresholdActivateFeedback()
            17 -> gestureThresholdDeactivateFeedback()
            18 -> keyboardTapFeedback()
            19 -> segmentTickFeedback()
            20 -> segmentFrequentTickFeedback()
            else -> defaultFeedback()
        }
    }
}
