# HapticScenariosV2

Une application Android développée en **Jetpack Compose** permettant de tester et d'évaluer différentes vibrations contextuelles dans des scénarios interactifs (bouton, popup, drag & drop...).

---

## 1. Fonctionnalité principale

- 🎯 Objectif : Tester des vibrations et **recueillir les ressentis** des utilisateurs.
- 📊 Les ressentis sont évalués via plusieurs **sliders** (durée, stress, surprise, clarté…).
- 🔄 Chaque scénario est suivi par une phase de notation, avec **navigation fluide** entre les étapes.
- 🔧 Le système est prêt à être connecté à un backend pour stocker les données.

---

## 2. Ouvrir le dossier dans Android Studio

1. Ouvrir Android Studio  
2. Choisir `Open an Existing Project`  
3. Naviguer jusqu’au dossier du projet `HapticScenariosV2`  
4. Attendre que le projet se synchronise

---

## 3. Lancer l’application

1. Connecter un appareil Android **ou** utiliser un **émulateur**  
2. Cliquer sur le bouton **Run ▶️** dans Android Studio  
3. L’application démarre sur la page d’accueil avec les différents scénarios interactifs

---

## 4. Fonctionnalités principales

- 🔁 **Système de vibrations contextuelles** (ex: bouton, pop-up, drag-and-drop)
- 🎛️ **Sliders pour évaluer les sensations** : durée, intensité, stress, clarté, etc.
- 🧠 **Logique de navigation dynamique** entre les scénarios
- ✅ **Enregistrement des ressentis utilisateurs** (prévu dans un backend)
- 🌈 **Interface moderne avec animations et transitions fluides**

---

## 5. Structure du projet

📁 app/ 
		├──ui/ 
		│ 
		├──scenario/ Tous les scénarios interactifs(bouton, popup,drag)  
		│ 
		└── sliders/ Page avec les sliders 
		└── utils/ Gestion des vibrations & logique de scénario 
		└── MainActivity.kt # Entrée de l'application


---

## 6. Exigences

- ✅ Android Studio Hedgehog ou + récent  
- ✅ JDK 17  
- ✅ Minimum SDK 26 (Android 8.0)  
- ✅ Kotlin + Jetpack Compose  

---

## 7. Contribution

Tu veux contribuer ? Fais un fork, clone le projet, puis :

```bash
git checkout -b nouvelle-fonction
# Ajoute ta fonctionnalité ou amélioration
git commit -m "Ajout cool 🎉"
git push origin nouvelle-fonction


---

💡 **Étape suivante** : colle ce texte dans un fichier `README.md` (pas `.txt`) à la racine de ton projet (là où il y a le `.gitignore`, `build.gradle.kts`, etc.), puis exécute ces commandes pour le valider :

```bash
git add README.md
git commit -m "Ajout du README"
git push
