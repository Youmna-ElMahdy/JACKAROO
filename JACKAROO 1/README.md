# 🃏 Jackaroo: A New Game Spin

<div align="center">

![Version](https://img.shields.io/badge/version-1.0.0-purple)
![Status](https://img.shields.io/badge/status-completed-brightgreen)
![Course](https://img.shields.io/badge/course-CSEN%20401-blue)
![University](https://img.shields.io/badge/university-GUC-orange)
![Language](https://img.shields.io/badge/language-Java-red)
![GUI](https://img.shields.io/badge/GUI-JavaFX-teal)

**A single-player reimagining of the classic Middle Eastern board game Jackaroo.**  
Built as part of *CSEN 401 — Computer Programming Lab*, Spring 2025.

</div>

---

## 📖 Table of Contents

- [📌 Overview](#-overview)
- [🎮 Game Description](#-game-description)
- [🏗️ Architecture](#️-architecture)
- [🃏 Card Types](#-card-types)
- [📦 Package Structure](#-package-structure)
- [📅 Milestones](#-milestones)
- [👨‍💻 Team](#-team)

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

### 🗺️ Board Zones & Cells

```
🏠 Home Zone     → Starting point for all marbles (inactive until fielded)
🔵 Base Cell     → Entry point onto the track (via Ace or King card)
🛤️  Track        → 100-cell circular path, marbles move clockwise
🚪 Safe Zone Entry → Gate just before the Safe Zone
🟢 Safe Zone     → Final destination — 4 cells per player, immune to attacks
⚠️  Trap Cells   → 8 random normal cells that destroy any marble landing on them
```

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

## 🃏 Card Types

### ⚡ Standard Special Cards

| Code | Card | Action |
|------|------|--------|
| 0 | **Standard** | Move marble forward by card rank |
| 1 | **Ace** | Field a marble from Home Zone **OR** move 1 step |
| 13 | **King** | Field a marble **OR** move 13 steps destroying all in path (bypasses 3 rules) |
| 12 | **Queen** | Discard random card from random opponent & skip their turn **OR** move 12 steps |
| 11 | **Jack** | Swap your marble with an opponent's marble **OR** move 11 steps |
| 4 | **Four** | Move marble **4 steps BACKWARDS** |
| 5 | **Five** | Move **any** marble on track 5 steps forward |
| 7 | **Seven** | Split 7 steps between 2 of your marbles (1–6 split) **OR** move 7 steps |
| 10 | **Ten** | Discard random card from the **next** player & skip their turn **OR** move 10 steps |

### 🌟 Wild Cards

| Code | Card | Action |
|------|------|--------|
| 14 | **🔥 Burner** | Destroy an opponent's marble and send it to their Home Zone |
| 15 | **🛡️ Saver** | Send your own marble to a random empty Safe Zone cell |

---

## 📦 Package Structure

```
📁 src/
├── 📁 engine/
│   ├── 🎮 Game.java                  ← Main game engine (implements GameManager)
│   ├── 🔌 GameManager.java           ← Interface for game-level operations
│   └── 📁 board/
│       ├── 🗺️  Board.java            ← Game board (implements BoardManager)
│       ├── 🔌 BoardManager.java      ← Interface for board-level operations
│       ├── 🔲 Cell.java              ← Single board cell
│       ├── 🟢 SafeZone.java          ← Player safe zone (4 cells)
│       └── 🔷 CellType.java          ← Enum: NORMAL, SAFE, BASE, ENTRY
├── 📁 model/
│   ├── 🎨 Colour.java                ← Enum: RED, GREEN, BLUE, YELLOW
│   ├── 📁 player/
│   │   ├── 👤 Player.java            ← Human player
│   │   ├── 🤖 CPU.java               ← CPU player (extends Player)
│   │   └── 🔵 Marble.java            ← Marble object
│   └── 📁 card/
│       ├── 🃏 Card.java              ← Abstract base card class
│       ├── 🗃️  Deck.java             ← Deck loader from Cards.csv
│       ├── 📁 standard/
│       │   ├── 🎴 Standard.java      ← Abstract standard card
│       │   ├── 🅰️  Ace.java          ← Rank 1
│       │   ├── 👑 King.java          ← Rank 13
│       │   ├── 👸 Queen.java         ← Rank 12
│       │   ├── 🃏 Jack.java          ← Rank 11
│       │   ├── 🔟 Ten.java           ← Rank 10
│       │   ├── 7️⃣  Seven.java        ← Rank 7
│       │   ├── 5️⃣  Five.java         ← Rank 5
│       │   └── 4️⃣  Four.java         ← Rank 4
│       └── 📁 wild/
│           ├── 🌟 Wild.java          ← Abstract wild card
│           ├── 🔥 Burner.java        ← Destroy opponent marble
│           └── 🛡️  Saver.java        ← Save own marble to Safe Zone
├── 📁 exception/
│   ├── ⚠️  GameException.java        ← Abstract base exception
│   ├── ❌ InvalidSelectionException  ← Abstract: invalid selection
│   ├── 🃏 InvalidCardException       ← Invalid card selected
│   ├── 🔵 InvalidMarbleException     ← Invalid marble selected
│   ├── ✂️  SplitOutOfRangeException  ← Seven card split out of range
│   ├── 🚫 ActionException            ← Abstract: illegal action
│   ├── 🚷 IllegalMovementException   ← Illegal marble movement
│   ├── 🔄 IllegalSwapException       ← Illegal marble swap
│   ├── 💥 IllegalDestroyException    ← Illegal marble destruction
│   ├── 🏠 CannotFieldException       ← Cannot field marble
│   └── 🗑️  CannotDiscardException    ← Cannot discard card
├── 📁 view/
│   └── 🖥️  [JavaFX GUI classes]
└── 📁 test/
    └── 🧪 [Test classes]
```

---

## 📅 Milestones

### 🏗️ Milestone 1 — OOP Structure & Data Setup
> 📆 Deadline: **March 7, 2025**

Built the complete **class hierarchy and data structures** for the game engine.

**Delivered:**
- 📦 10 packages organized by responsibility (`engine`, `model`, `model.card`, `model.player`, `model.card.standard`, `model.card.wild`, `engine.board`, `exception`, `test`, `view`)
- 🔷 3 Enums: `Colour`, `CellType`, `Suit`
- 🔌 2 Interfaces: `GameManager`, `BoardManager`
- 🧱 Core classes: `Marble`, `Cell`, `SafeZone`, `Board`, `Player`, `CPU`, `Game`
- 🃏 Abstract card hierarchy: `Card` → `Standard` / `Wild` → 8 standard + 2 wild subclasses
- 📂 `Deck` class with CSV loading from `Cards.csv`
- ⚠️ Full exception hierarchy: `GameException` → `InvalidSelectionException` + `ActionException` and all their subclasses

---

### ⚙️ Milestone 2 — Game Engine & Logic
> 📆 Deadline: **April 25, 2025**

Implemented the complete **game logic and engine**, playable on the console.

**Delivered:**

🗺️ **Board Logic**
- Marble movement with full path validation (`validateSteps`, `validatePath`, `move`)
- Support for all special card rules (King destroys all, Four moves backwards, Five moves any marble)
- Swap, destroy, field, and save marble operations
- Trap cell mechanics — destroyed marble reassigns trap randomly

🃏 **Card Actions**
- All 9 standard cards + 2 wild cards fully implemented with `act()` method
- Marble size and colour validation per card type

👤 **Player & CPU**
- `play()` method for human player with card/marble selection and validation
- `CPU.play()` — automated random card + marble selection with full exception handling

🎮 **Game Flow**
- Turn management, round cycling, card distribution
- Firepit and deck refill logic
- Win condition checking (`checkWin()`)
- Split distance editor for Seven card (`editSplitDistance()`)

---

### 🖥️ Milestone 3 — JavaFX GUI
> 📆 Deadline: **May 21, 2025**

Built the full **graphical user interface** using **JavaFX**, connected to the game engine.

**GUI Features:**

🚀 **Game Start**
- Prompt human player to enter their name
- Auto-assign names to 3 CPU players
- Initialize full board with all cells, zones, and marbles
- Distribute 4 cards to each player

📊 **Always Visible During Game**
- Each player's name and colour
- Number of remaining cards per player
- Top card on the firepit
- Current player's turn indicator
- Next player in turn order

🎮 **Human Player Controls**
- View all cards with full details (name, rank, suit)
- Click to select a card to play
- Click to select marbles
- Input split distance for Seven card
- Deselect card/marbles
- Play turn with all card action types
- ⌨️ Keyboard shortcut to field a marble

🤖 **CPU Behaviour**
- Auto-plays random valid card each turn
- Wait time between each CPU turn for visibility

🔔 **Event Feedback**
- Trap cell landing clearly indicated
- Invalid actions shown as popup messages with reason
- Popups do not terminate the game
- Winner's colour announced on win screen
- Board updates reflected in real-time after every action

> 🎨 Bonus available for exceptional GUI design.

---

## 👨‍💻 Team

> 🏫 **University:** German University in Cairo (GUC)  
> 📚 **Course:** CSEN 401 — Computer Programming Lab, Spring 2025  
> 👩‍🏫 **Instructors:** Prof. Dr. Slim Abdennadher, Assoc. Prof. Mervat Abu-ElKheir, Dr. Ahmed Abdelfattah  
> 🏛️ **Faculty:** Media Engineering and Technology (MET)

---

<div align="center">

Made with ❤️ by the team &nbsp;·&nbsp; GUC &nbsp;·&nbsp; Spring 2025

</div>
