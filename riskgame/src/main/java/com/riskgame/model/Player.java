package com.riskgame.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * Player class manages information of players and their territories.
 * 
 * Two annotations (Getter, Setter) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter in the code.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a> documenation added
 * @see PlayerTerritory
 */

@Getter
@Setter
public class Player {
	/**
	 * Stores the id of player
	 */
	private int playerId;
	
	/**
	 * Stores the name of player
	 */
	private String playerName;
	
	/**
	 * The number of army owns by a player
	 */
	private int armyOwns;
	
	/**
	 * List of Territories owns by player
	 */
	private List<PlayerTerritory> playerterritories = new ArrayList<PlayerTerritory>();
	
	private List<RiskCard> cardListOwnedByPlayer;
	
	private int exchangeCount;
	
	private boolean isTerritoryConqured;

	@Override
	public String toString() {
		return "Player [playerId=" + playerId + ", playerName=" + playerName + ", armyOwns=" + armyOwns
				+ ", playerterritories=" + playerterritories + ", cardListOwnedByPlayer=" + cardListOwnedByPlayer
				+ ", exchangeCount=" + exchangeCount + ", isTerritoryConqured=" + isTerritoryConqured + "]";
	}


}
