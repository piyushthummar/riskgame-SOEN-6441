package com.riskgame.builder;

import com.riskgame.model.GamePlayPhase;

import lombok.Getter;
import lombok.Setter;


/**
 *Product class of the builder pattern
 *
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */

@Getter
@Setter
public class GameState {

	/**
	 * The components of the GameState to be constructed
	 */
	private GamePlayPhase gamePlayPhase;
	
}
