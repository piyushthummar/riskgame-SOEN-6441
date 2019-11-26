package com.riskgame.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentResults {

	private List<GameResult> gameResult = new ArrayList<GameResult>();
}
