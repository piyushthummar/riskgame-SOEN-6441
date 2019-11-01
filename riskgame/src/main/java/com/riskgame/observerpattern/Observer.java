/**
 * 
 */
package com.riskgame.observerpattern;

/**
 * This is the Observer class.
 * @author PIYUSH
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
	
}
