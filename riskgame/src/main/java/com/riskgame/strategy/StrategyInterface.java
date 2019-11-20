/**
 * 
 */
package com.riskgame.strategy;

import com.riskgame.model.GamePlayPhase;

/**
 * These abstract methods are part of strategy pattern. We have 5 types of
 * player's strategy from human, aggresive, random, benevolent and cheater. This
 * methods will have their own implementation according to strategy.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 */
public interface StrategyInterface {

	/**
	 * This method will be omplemented for different strategy for reinforcement as
	 * well as card exchange.
	 * 
	 * @param gps
	 *            is a gameplayphase object, which is current state of the game.
	 * @return GamePlayPhase after reinforcement phase.
	 */
	public abstract GamePlayPhase reinforce(GamePlayPhase gps);

	/**
	 * This method will be implemented for different strategy for attacks, it will
	 * have different implementation for different types of player's strategy
	 * 
	 * @param gps
	 *            is a gameplayphase object, which is current state of the game.
	 * @return GamePlayPhase after attack done with changes.
	 */
	public abstract GamePlayPhase attack(GamePlayPhase gps);

	/**
	 * This method will be implemented for different strategy player for
	 * fortification.
	 * 
	 * @param gps
	 *            is a gameplayphase object, which is current state of the game.
	 * @return gamePlayPhase after fortification done.
	 */
	public abstract GamePlayPhase fortify(GamePlayPhase gps);

}
