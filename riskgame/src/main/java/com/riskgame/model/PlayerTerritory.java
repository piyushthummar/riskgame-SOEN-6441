/**
 * 
 */
package com.riskgame.model;

import lombok.Getter;
import lombok.Setter;

/**
 * This class stores the information regarding player's territories during gameplay.
 * 
 * Two annotations (Getter, Setter) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter and tostring method in the code.
 * 
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * @author <a href="mailto:j_banava@encs.concordia.ca">Jaswanth Banavathu</a> documenation added
 */


@Getter
@Setter
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

	@Override
	public String toString() {
		return "PlayerTerritory [continentName=" + continentName + ", territoryName=" + territoryName
				+ ", armyOnterritory=" + armyOnterritory + "]"+"\n";
	}

}
