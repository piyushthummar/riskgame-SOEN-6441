package com.riskgame.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the model class which will store tournament result for all games.
 * 
 * Three annotations (Getter, Setter and ToString) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter and tostring method in the code.
 * 
 * @author <a href="mailto:j_banawa@encs.concordia.ca">Jaswanth Banawathu</a>
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
public class TournamentResults {

	/**
	 * List of result of all games played by user
	 */
	private List<GameResult> gameResult = new ArrayList<GameResult>();
}
