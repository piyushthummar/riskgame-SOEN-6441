package com.riskgame.builder;

import com.riskgame.model.GamePlayPhase;

/**
 * ConcreteBuilder class of the Builder pattern
 *
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public class GameStateConcreteBuilder extends GameStateBuilder {


	/**
	 *  The construction of the GameState parts 
	 */
	@Override
	void buildGamePlayPhase(GamePlayPhase gamePlayPhase) {
		gameState.setGamePlayPhase(gamePlayPhase);
	}

}
