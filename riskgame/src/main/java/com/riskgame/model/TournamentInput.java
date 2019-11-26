package com.riskgame.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the model class for tournament input, the command fired for
 * tournament, it'll be saved as the data of this class.
 * 
 *  Three annotations
 * (Getter, Setter and ToString) you can see on the top of the class are lombok
 * dependencies to automatically generate getter, setter and tostring method in
 * the code.
 * 
 * @author <a href="mailto:j_banawa@encs.concordia.ca">Jaswanth Banawathu</a>
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
public class TournamentInput {

	/**
	 * List of map files name
	 */
	List<String> mapList;
	
	/**
	 * List of player strategies
	 */
	List<String> strategiesList;
	
	/**
	 * No of games user wants to play
	 */
	int noOfGames;
	
	/**
	 * No of turn per game in tournament mode
	 */
	int maxTurns;

}
