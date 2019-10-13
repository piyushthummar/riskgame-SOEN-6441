/**
 * 
 */
package com.riskgame.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents the state of the game on different point of time.
 * 
 * Three dependecies (Getter, Setter and ToString) you can see on the top of the class are lombok dependencies to 
 * automatically generate getter, setter and tostring method in the code.
 *
 * @author <a href="mailto:j_banawa@encs.concordia.ca">Jaswanth Banawathu</a>
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a> documenation added
 * @see Player
 */
@Getter
@Setter
@ToString
public class GamePlayPhase {
	
	/**
	 * It'll manage list of player in that particlaur state of game
	 */
	private List<Player> gameState;
	
	/**
	 * It'll store name of game phase i.e. startUp, reinforcement etc.
	 */
	private String gamePhase;
}
