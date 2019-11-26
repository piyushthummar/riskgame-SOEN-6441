package com.riskgame.builder;

import com.riskgame.model.GamePlayPhase;

/**
 * Abstract Builder class of the Builder pattern
 *
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public abstract class GameStateBuilder {

	/**
	 * Product to be constructed by the builder
	 */

	protected GameState gameState;

	/**
	 * Get the constructed GameState from the Builder
	 */

	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Create a new unspecified GameState that will be eventually build by calling the
	 * following abstract methods in a concrete class derived from the GameState class
	 */

	public void createNewGameState() {
		gameState = new GameState();
	}
	
	abstract void buildGamePlayPhase(GamePlayPhase gamePlayPhase);

}
