/**
 * 
 */
package com.riskgame.strategy.impl;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.strategy.StrategyInterface;

/**
 * Concrete implementation of Strategy interface of design pattern for Random
 * strategy of player
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.strategy
 * @version 1.0.0
 */
public class RandomPlayer implements StrategyInterface {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#reinforce(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase reinforce(GamePlayPhase gamePlayPhase) {
		// TODO Auto-generated method stub
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#attack(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase attack(GamePlayPhase gamePlayPhase) {
		// TODO Auto-generated method stub
		return gamePlayPhase;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.strategy.StrategyInterface#fortify(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public GamePlayPhase fortify(GamePlayPhase gamePlayPhase) {
		// TODO Auto-generated method stub
		return gamePlayPhase;
	}

}
