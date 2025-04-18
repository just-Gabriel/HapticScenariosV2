# HapticScenariosV2

Cette application Android permet de tester et d’évaluer des scénarios de vibrations (haptique) via des interactions utilisateur.  
Elle s’inscrit dans un projet de recherche sur la perception haptique.

## 🧪 Fonctionnalités

- 🔘 Plusieurs scénarios de test : drag & drop, pop-up, boutons…
- 🎛️ Sliders pour évaluer la vibration selon des critères subjectifs
- 🔄 Navigation automatique entre les étapes
- 📊 Données prêtes à être transmises à un backend pour analyse

## 🎯 Échelles d’évaluation

| Critère                  | Axe                        |
|--------------------------|----------------------------|
| Durée                    | Courte ↔ Longue           |
| Résultat perçu           | Succès ↔ Échec            |
| Surprise                 | Surprenant ↔ Prévisible   |
| Réalisme                 | Naturel ↔ Artificiel      |
| Émotion                  | Stressant ↔ Rassurant     |
| Clarté                   | Clair ↔ Confus            |

## 🚀 Lancer le projet

1. Cloner le repo :
   ```bash
   git clone https://github.com/ton-utilisateur/HapticScenariosV2.git

## Ouvrir le dossier dans Android Studio

1. Ouvrir Android Studio
2. Choisir `Open an Existing Project`
3. Naviguer jusqu’au dossier du projet `HapticScenariosV2`
4. Attendre que le projet se synchronise

## 3. Lancer l’application

1. Connecter un appareil Android **ou** utiliser un **émulateur**
2. Cliquer sur le bouton **Run ▶️** dans Android Studio
3. L’application démarre sur la page d’accueil avec les différents scénarios interactifs

## 4. Fonctionnalités principales

- 🔁 **Système de vibrations contextuelles** (ex: bouton, pop-up, drag-and-drop)
- 🎛️ **Sliders pour évaluer les sensations** : durée, intensité, stress, clarté, etc.
- 🧠 **Logique de navigation dynamique** entre les scénarios
- ✅ **Enregistrement des ressentis utilisateurs** (prévu dans un backend)
- 🌈 **Interface moderne avec animations et transitions fluides**

## 5. Structure du projet

📁 app/ ├── ui/ │ ├── scenario/ # Tous les scénarios interactifs (bouton, popup, drag) │ └── sliders/ # Page de notation avec les sliders ├── utils/ # Gestion des vibrations & logique de scénario └── MainActivity.kt # Entrée de l'application


## 6. Exigences

- ✅ Android Studio Hedgehog ou + récent
- ✅ JDK 17
- ✅ Minimum SDK 26 (Android 8.0)
- ✅ Kotlin + Jetpack Compose

## 7. Contribution

Tu veux contribuer ? Fais un fork, clone le projet, puis :

```bash
git checkout -b nouvelle-fonction
# Ajoute ta fonctionnalité ou amélioration
git commit -m "Ajout cool 🎉"
git push origin nouvelle-fonction
