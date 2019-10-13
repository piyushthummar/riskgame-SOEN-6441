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
 * @author <a href="mailto:j_banawa@encs.concordia.ca">Jaswanth Banawathu</a>
 * @see Player
 */
@Getter
@Setter
@ToString
public class GamePlayPhase {
	private List<Player> gameState;
	private String gamePhase;
}
