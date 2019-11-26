package com.riskgame.builder;

import com.riskgame.model.GamePlayPhase;

/**
 * Director of the Builder pattern
 *
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public class GameStateDirector {

	/**
	 * GamePlayPhase model
	 */
	private GamePlayPhase gamePlayPhase;

	public GameStateDirector(GamePlayPhase gamePlayPhase) {
		this.gamePlayPhase = gamePlayPhase;
	}

	/**
	 * The GameStateDirector is to use a specific GameState build: the
	 * GameStateBuilder
	 */
	private GameStateBuilder gameStateBuilder;

	
	/**
	 * setBuilder which will provide the builder of gamestateBuilder
	 */
	public void setBuilder(GameStateBuilder builder) {

		gameStateBuilder = builder;

	}

	/**
	 * constructGameState which build the GameState parts
	 */
	public void constructGameState() {
		gameStateBuilder.createNewGameState();
		gameStateBuilder.buildGamePlayPhase(this.gamePlayPhase);
	}

	/**
	 * @return gets the GameState after it has been built
	 */
	public GameState getGameState() {
		return gameStateBuilder.getGameState();
	}
}
