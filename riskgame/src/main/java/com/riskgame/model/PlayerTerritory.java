/**
 * 
 */
package com.riskgame.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class stores the information regarding player's territories during gameplay.
 * 
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * @author <a href="mailto:j_banava@encs.concordia.ca">Jaswanth Banavathu</a> documenation added
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
public class PlayerTerritory {
	/**
	 * name of the continent of that particular player's territory
	 */
	private String continentName;
	
	/**
	 * Name of the territory owned by player
	 */
	private String territoryName;
	
	/**
	 * Total number of army on that particular territory
	 */
	private int armyOnterritory;

}
