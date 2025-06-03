# HapticScenariosV2

**Advanced Android app for evaluating haptic feedback through immersive UI scenarios.**

## 🌐 Overview

This application allows the testing of 20 predefined haptic feedback patterns across 3 distinct user interface scenarios:

* **Button** (Simple tap)
* **Drag & Drop** (Drag and release)
* **Pop-up** (Alert notification)

Each vibration is evaluated by the user through 5 qualitative criteria using 3-position sliders.

---

## 📊 Test Structure

The application performs a total of 140 tests, divided into two main phases:

### 1. 🧪 Training Phase

* 20 unique vibrations, played once
* Scenario is randomly selected for each test
* **No data is sent to the backend**

### 2. 🧰 Main Test Phase

* 120 tests (3 scenarios × 2 repetitions for each vibration)
* All tests are shuffled
* Data is sent as JSON using Retrofit

---

## 🎓 Core Technologies

* **Jetpack Compose** (Modern UI framework)
* **Retrofit** (API requests)
* **ViewModel** + `mutableStateOf` for state management
* **Vibrator / VibrationEffect** (Precise haptic control)

---

## 📁 Project Structure

```bash
HapticScenariosV2/
 ├── app/
 │   ├── ui/
 │   │   ├── form/                --> User form (name + phone model)
 │   │   ├── scenario/            --> 3 scenarios (Button, Drag, Popup)
 │   │   └── sliders/             --> Evaluation screen (sliders)
 │   ├── network/             --> API calls (Retrofit + data models)
 │   ├── utils/               --> VibrationManager, TrainingManager, TestProgressController
 │   └── viewmodel/           --> ScenarioViewModel.kt

---

## 🚀 Test Flow
* **UserFormScreen: User inputs name + phone model

* **Training phase: 20 training vibrations, not saved to the database

* **Real test phase: 120 recorded vibration tests

* **SlidersScreen: Evaluation of each vibration

* **EndScreen: Completion screen with final animation

---

## 🛠️ Backend API (Symfony / API Platform)

* **POST request to /api/emotional_experiences

Example of JSON payload:

{
  "user": "/api/users/123",
  "telephone": "/api/telephones/456",
  "slider1": 1,
  "slider2": 2,
  "slider3": 2,
  "slider4": 3,
  "slider5": 2,
  "scenario": "scenario_popup",
  "vibrationId": 17,
  "mobile": 0
}

---

## 📅 Data Structure

* **Stored data: User, Telephone, EmotionalExperience

* **Integrity checks ensure correct distribution: each scenario contains 2×20 vibrations

## 🚀🚀 Future Development

📱 iOS port using SwiftUI + Combine

📄 Automatic PDF export of results

🤖 On-device AI (Ollama) for intelligent result analysis


---

Project led by Gabriel Noel for advanced experimentation in haptic perception.


___________________________________________________________________________________________________________________________________________________________________


# HapticScenariosV2

**Application Android avancée pour l'évaluation haptique des vibrations via des scénarios UI.**

## 🌐 Présentation / Overview

Cette application permet de tester 20 types de vibrations prédéfinies (Haptic Feedback) dans 3 scénarios différents :

* **Bouton** (Appui simple)
* **Drag & Drop** (Glisser-lâcher)
* **Pop-up** (Apparition d'une alerte)

Chaque vibration est évaluée par l'utilisateur selon 5 critères via des sliders 3 positions.



## 📊 Structure des tests / Test Structure

L'application effectue 140 tests au total, répartis ainsi :

### 1. 🧪 Phase d'entraînement (Training phase)

* 20 vibrations, 1 seule fois chacune
* Scénario choisi aléatoirement
* **Aucune donnée envoyée à la base**

### 2. 🧰 Phase de test réel (Main test phase)

* 120 tests (3 scénarios × 2 fois chaque vibration)
* Ordre mélangé
* Données postées en JSON via Retrofit

---

## 🎓 Technologies principales / Main Technologies

* **Jetpack Compose** (UI moderne)
* **Retrofit** (API calls)
* **ViewModel** + `mutableStateOf`
* **Vibrator / VibrationEffect** (gestion fine haptique)

---

## 📁 Structure du code / Project structure

```bash
HapticScenariosV2/
 ├── app/
 │   ├── ui/
 │   │   ├── form/                --> Formulaire utilisateur (User + Téléphone)
 │   │   ├── scenario/            --> 3 scénarios (Bouton, Drag, Popup)
 │   │   └── sliders/             --> Écran d'évaluation (sliders)
 │   ├── network/             --> Appels API (Retrofit + models)
 │   ├── utils/               --> VibrationManager, TrainingManager, TestProgressController
 │   └── viewmodel/           --> ScenarioViewModel.kt
```

---

## 🚀 Déroulement d'un test / Test Flow

1. **UserFormScreen** : saisie nom + modèle de téléphone
2. **Phase d'entraînement** : 20 vibrations non enregistrées
3. **Phase de test réel** : 120 tests
4. **SlidersScreen** : évaluation de chaque vibration
5. **EndScreen** : test terminé + animation finale

---

## 🛠️ Backend API (Symfony / API Platform)

* POST sur `/api/emotional_experiences`
* Exemple de payload JSON :

```json
{
  "user": "/api/users/123",
  "telephone": "/api/telephones/456",
  "slider1": 1,
  "slider2": 2,
  "slider3": 2,
  "slider4": 3,
  "slider5": 2,
  "scenario": "scenario_popup",
  "vibrationId": 17,
  "mobile": 0
}
```

---

## 📅 Données / Data

* Données sauvegardées : User, Telephone, EmotionalExperience
* Contrôles de cohérence assurés (2x20 vibrations par scénario)

---

## 🚀🚀 Développements futurs / Future Work

* 📱 Portage iOS (SwiftUI + Combine)
* 📄 Export PDF automatique des stats
* 🤖 IA locale (Ollama) pour analyse des résultats

---

**Projet porté par Gabriel Noel pour des expérimentations avancées en perception haptique.**
