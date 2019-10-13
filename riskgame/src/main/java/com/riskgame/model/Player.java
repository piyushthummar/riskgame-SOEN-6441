package com.riskgame.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Player class manages information of players and their territories
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a> documenation added
 * @see PlayerTerritory
 */

/**
 * lombok dependency to generate automatic getter method
 */
@Getter

/**
 * lombok dependency to generate automatic setter method
 */
@Setter
@ToString
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
	private List<PlayerTerritory> playerterritories;
}
