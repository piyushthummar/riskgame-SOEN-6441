package com.riskgame.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Getter
@Setter
@ToString
public class Player {
	private int playerId;
	private String playerName;
	private int armyOwns;
	private List<PlayerTerritory> playerterritories;
}
