package com.riskgame.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentInput {
	
	List<String> mapList;
	List<String> strategiesList;
	int noOfGames;
	int maxTurns;

}
