package com.riskgame.model;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents all the continents in the map and it is having list of territories that continent owned.
 * 
 * Three annotations (Getter, Setter and ToString) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter and tostring method in the code.
 *
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Getter
@Setter
@ToString
public class Continent {
	
	/**
	 * 4 data members of Continent class will be used to store continent data from map.
	 * continentIndex will store index of the continent
	 */
	private int continentIndex;
	
	/**
	 * It'll represents name of the continent
	 */
	private String continentName;
	
	/**
	 * It'll represents value of the continent
	 */
	private int continentValue;
	
	/**
	 * List of territories this continent is having
	 */
	private List<Territory> TerritoryList = new ArrayList<>();

}



