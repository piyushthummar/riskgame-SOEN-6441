package com.riskgame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This DTO stores the value about neighbor territories given by user side.
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Getter
@Setter
@ToString
public class NeighbourTerritoriesDto {

	private int id;
	private String countryName;
	private String countryNeighbourName;
	/**
	 * parameterise constructor
	 * @param id
	 * @param countryName
	 * @param countryNeighbourName
	 */
	public NeighbourTerritoriesDto(int id, String countryName, String countryNeighbourName) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.countryNeighbourName = countryNeighbourName;
	}
	
	/**
	 * default constructor
	 */
	public NeighbourTerritoriesDto() {
		super();
		
	}

}
