/**
 * 
 */
package com.riskgame.observerpattern;

/**
 * This is the Observer class for implementing observer-pattern.
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 * 
 */
public abstract class Observer {
	protected Subject observerSubject; 
	
	/**
	 * This method update new changes
	 */
	public abstract void update();

	/**
	 * this method update fortification changes 
	 */
	public abstract void fortificationUpdate();

	/**
	 * this method update attack changes 
	 */
	public abstract void attackUpdate();

	/**
	 * this method update card changes 
	 */
	public abstract void reinforcemrentUpdate();
	
	/**
	 * This method will update player domination View
	 */
	public abstract void playerDominationUpdate();
	
	/**
	 * This method will update player phase View
	 */
	public abstract void playerPhaseViewUpdate();
	
	/**
	 * This method will update player card exchnage View
	 */
	public abstract void playerCardExchangeViewUpdate();
	
}
