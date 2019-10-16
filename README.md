# SOEN 6441 team Project
Here, Risk Domination Game is implemented, where players can play turn by turn. Whoever dominates or owns all the territories first, that player wins the game.

Here, the user may use the GUI to play game or there are set of commands by which the game can be played. 

User can run the application by clicking **Right click on project -> Run As -> Java Application -> Select RiskgameApplication.java**

# **Commands List**

## **1. Map editor commands:**

- editcontinent -add continentname continentvalue -remove continentname
- editcountry -add countryname continentname -remove countryname
- editneighbor -add countryname neighborcountryname -remove countryname neighborcountryname
- showmap (show all continents and countries and their neighbors)
- savemap filename
- editmap filename
- validatemap

## **2. Game play command:**
###  **2.1. Startup phase**

- showmap
- loadmap filename
- gameplayer -add playername -remove playername
- populatecountries
- placearmy countryname (by each player until all players have placed all their armies)
- placeall (automatically randomly place all remaining unplaced armies for all players) 

### **2.2. Reinforcement phase**
- reinforce countryname num (until all reinforcements have been placed)

### **2.3. Fortification phase**
- fortify fromcountry tocountry num
- fortify none (choose to not do a move)

