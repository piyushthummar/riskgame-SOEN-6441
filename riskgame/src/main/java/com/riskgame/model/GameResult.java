package com.riskgame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This is the model class which will store data for tournament game mode result.
 * 
 * Two annotations (Getter, Setter) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter method in the code.
 * 
 * @author <a href="mailto:j_banawa@encs.concordia.ca">Jaswanth Banawathu</a>
 * @version 1.0.0
 */
@Getter
@Setter
public class GameResult {

	/**
	 * Name of the map
	 */
	private String mapName;
	
	/**
	 * no of game user wants to play
	 */
	private String gameName;
	
	/**
	 * The name of winner
	 */
	private String winner;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameResult \n [mapName=" + mapName + ", gameName=" + gameName + ", winner=" + winner + "]"+"\n";
	}
	
}
