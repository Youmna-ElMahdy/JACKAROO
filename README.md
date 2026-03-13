🃏 Jackaroo: A New Game Spin

📖 Table of Contents
📌 Overview
🎮 Game Description
🏗️ Architecture


---
📌 Overview
Jackaroo is a beloved strategic board/card game with deep roots in Middle Eastern culture. This project reimagines it as a single-player experience, where one human player competes against 3 CPU opponents, using a custom-designed deck with new rules and mechanics.
> 🎯 **Goal:** Move all 4 of your marbles from your Home Zone to your Safe Zone before any opponent does.
---
<div align="center">
⚙️ Game Dynamics
🔄 Rounds consist of 4 turns per player in clockwise order
🃏 Each player is dealt 4 cards at the start of every round
🤖 CPU players randomly select and execute cards each turn
❌ If a card's action is invalid, it is discarded and the turn ends
🏆 First player to fill their Safe Zone with all 4 marbles wins
📜 Movement Rules
✅ Marbles move clockwise by the exact rank of the played card
🚫 Cannot bypass or destroy your own marbles
🚫 Movement blocked if more than one marble is in the path
🚫 Cannot enter Safe Zone if Safe Zone Entry is occupied
💥 Landing on an opponent's marble destroys it (sends it home)
👑 King card destroys all marbles in the path
---
🏗️ Architecture
The project follows an OOP architecture with optional MVC pattern for the GUI
```


</div>
