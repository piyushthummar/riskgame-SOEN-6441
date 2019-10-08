package com.riskgame.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Territory {
	private int territoryIndex;
	private String territoryName;
	private List<String> neighbourTerritories;

}
