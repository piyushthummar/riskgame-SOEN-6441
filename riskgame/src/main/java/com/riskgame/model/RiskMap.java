package com.riskgame.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RiskMap {
	private String mapName;
	private Map<Integer, Continent> continents;
	private String status;
}
