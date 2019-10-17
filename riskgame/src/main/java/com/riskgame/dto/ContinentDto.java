package com.riskgame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents all the continents in the map given from user side.
 * 
 * Three annotations (Getter, Setter and ToString) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter and tostring method in the code.
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Getter
@Setter
@ToString

public class ContinentDto {
	
	private int id;
	private String continentName;
	private int continentValue;
	/**
	 * Paramtererise constructor
	 * @param id
	 * @param continentName
	 * @param continentValue
	 */
	public ContinentDto(int id, String continentName, int continentValue) {
		super();
		this.id = id;
		this.continentName = continentName;
		this.continentValue = continentValue;
	}
	
	/**
	 * default constructor
	 */
	public ContinentDto() {
		super();
	}

}
