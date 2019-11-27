/**
 * 
 */
package com.riskgame.model;

import java.util.List;
import java.util.Observable;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the state of the game on different point of time.
 * 
 * Two annotations (Getter, Setter) you can see on the top of the class are
 * lombok dependencies to automatically generate getter, setter in the code.
 *
 * @author <a href="mailto:j_banawa@encs.concordia.ca">Jaswanth Banawathu</a>
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 *         documenation added
 * @see Player
 */
@Getter
@Setter
public class GamePlayPhase {

	/**
	 * It'll manage list of player in that particlaur state of game
	 */
	private List<Player> playerList;

	/**
	 * It'll store name of game phase i.e. startUp, reinforcement etc.
	 */
	private String gamePhase;

	/**
	 * name of the map file
	 */
	private String fileName;

	/**
	 * List of total risk cards
	 */
	private List<RiskCard> riskCardList;

	/**
	 * Winner player name
	 */
	private String winner;

	/**
	 * Total number of games in tournament
	 */
	private int numberOfGames;

	/**
	 * number of turn in one game
	 */
	private int maxNumberOfTurns;

	/**
	 * game play phase id (unique)
	 */
	private int gamePlayPhaseId;

	/**
	 * entire map information
	 */
	private RiskMap riskMap;

	/**
	 * current player id who is playing the game
	 */
	private int currentPlayerId;

	/**
	 * current status of game
	 */
	private String status;

	/**
	 * current player riskcardExchane will store in this model
	 */
	private RiskCardExchange riskCardExchange;

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GamePlayPhase [playerList=" + playerList + ", gamePhase=" + gamePhase + ", fileName=" + fileName
				+ ", riskCardList=" + riskCardList + ", winner=" + winner + ", numberOfGames=" + numberOfGames
				+ ", maxNumberOfTurns=" + maxNumberOfTurns + ", gamePlayPhaseId=" + gamePlayPhaseId + ", riskMap="
				+ riskMap + ", currentPlayerId=" + currentPlayerId + ", status=" + status + ", riskCardExchange="
				+ riskCardExchange + "]";
	}

}
