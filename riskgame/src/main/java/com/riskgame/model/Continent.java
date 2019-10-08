package com.riskgame.model;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Continent {
	private String continentName;
	private int continentValue;
	private List<Territory> TerritoryList = new ArrayList<>();

}



