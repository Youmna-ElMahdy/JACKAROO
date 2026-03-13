# 🃏 Jackaroo: A New Game Spin

<div align="center">

![Version](https://img.shields.io/badge/version-1.0.0-purple)
![Status](https://img.shields.io/badge/status-completed-brightgreen)
![Course](https://img.shields.io/badge/course-CSEN%20401-blue)
![University](https://img.shields.io/badge/university-GUC-orange)
![Language](https://img.shields.io/badge/language-Java-red)
![GUI](https://img.shields.io/badge/GUI-JavaFX-teal)

  
Built as part of *CSEN 401 — Computer Programming Lab*, Spring 2025.

</div>

---


  

---

## 📌 Overview

**Jackaroo** is a beloved strategic board/card game with deep roots in Middle Eastern culture. This project reimagines it as a **single-player experience**, where one human player competes against **3 CPU opponents**, using a custom-designed deck with new rules and mechanics.

> 🎯 **Goal:** Move all 4 of your marbles from your Home Zone to your Safe Zone before any opponent does.

---

## 🎮 Game Description

### 🧩 Game Elements

| Element | Description |
|---------|-------------|
| 👥 Players | 1 human player vs 3 CPU players, each with a unique colour |
| 🔵 Marbles | 4 coloured marbles per player, starting in their Home Zone |
| 🗺️ Board | 100-cell circular track with special zones and cells |
| 🃏 Deck | 102 custom cards across 15 types (standard + wild) |
| 🔥 Firepit | Discard pile for played/used cards |



### ⚙️ Game Dynamics

- 🔄 **Rounds** consist of 4 turns per player in clockwise order
- 🃏 Each player is dealt **4 cards** at the start of every round
- 🤖 **CPU players** randomly select and execute cards each turn
- ❌ If a card's action is **invalid**, it is discarded and the turn ends
- 🏆 **First player** to fill their Safe Zone with all 4 marbles **wins**

### 📜 Movement Rules

- ✅ Marbles move **clockwise** by the exact rank of the played card
- 🚫 Cannot bypass or destroy **your own marbles**
- 🚫 Movement blocked if **more than one marble** is in the path
- 🚫 Cannot enter Safe Zone if **Safe Zone Entry is occupied**
- 💥 Landing on an **opponent's marble** destroys it (sends it home)
- 👑 **King card** destroys **all marbles** in the path

---

## 🏗️ Architecture

The project follows an **OOP architecture** with optional **MVC pattern** for the GUI:

```
🧠 Model   → Game logic, rules, data structures (engine + model packages)
🖥️  View    → JavaFX GUI displaying board, cards, marbles, and player info
🎮 Controller → Handles player input and bridges View ↔ Model
```





---

<div align="center">



</div>
