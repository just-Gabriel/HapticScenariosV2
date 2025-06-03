package fr.maloof.hapticscenariosv2.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class DataModel {

    @Parcelize
    data class User(
        val id: Int? = null,
        val age: Int,
        val sexe: String,
        val mainDominante: String,
        val password: String?,
        val paysResidence: String,
        val profession: String,
        val vibrationTelActive: Boolean,
        val vibrationClavierActive: Boolean,
        val coqueTel: Boolean,
        val niveauInformatique: Int,
    ) : Parcelable

    @Parcelize
    data class Telephone(
        val id: Int? = null,
        val marque: String,
        val modele: String,
        val versionLogiciel: String,
        val numeroModele: String,
    ) : Parcelable

    @Parcelize
    data class VibrationEntry(
        val id: Int,
        val action: () -> Unit
    ) : Parcelable

    @Parcelize
    data class ScenarioVibration(
        val scenario: String,
        val vibrationId: Int,
        val callback: () -> Unit,
        val isTraining: Boolean = false
    ): Parcelable

    data class EmotionalExperience(
        val user: String,
        val telephone: String,
        val slider1: Int,
        val slider2: Int,
        val slider3: Int,
        val slider4: Int,
        val slider5: Int,
        val scenario: String,
        val vibrationId: Int,
        val mobile: Int
    )
}
