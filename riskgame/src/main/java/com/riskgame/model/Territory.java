package com.riskgame.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Territory model and property of territory from the map
 * file. It's also having list of neighbourTerritories.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Continent
 */
@Getter
@Setter
@JsonIgnoreProperties
public class Territory {
	
	/**
	 * It'll store index of territory
	 */
	private int territoryIndex;
	
	/**
	 * It'll store Name of territory
	 */
	private String territoryName;
	
	/**
	 * It'll store index of continent in which this territory located
	 */
	private int continentIndex;
	
	/**
	 * x Axis of this territory in map
	 */
	@JsonIgnore
	private int xAxis;
	
	/**
	 * y Axis of this territory in map
	 */
	@JsonIgnore
	private int yAxis;
	
	/**
	 * List of neighbor territory of this territory
	 */
	private List<String> neighbourTerritories;

	@Override
	public String toString() {
		return "Territory \n [territoryIndex=" + territoryIndex + ", territoryName=" + territoryName + ", continentIndex="
				+ continentIndex + ", xAxis=" + xAxis + ", yAxis=" + yAxis + ", neighbourTerritories="
				+ neighbourTerritories + "] \n";
	}
	
	

}
