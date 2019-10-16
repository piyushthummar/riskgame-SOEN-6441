package com.riskgame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ContinentDto {
	
	private int id;
	private String continentName;
	private int continentValue;
	/**
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
	 * 
	 */
	public ContinentDto() {
		super();
	}

}
