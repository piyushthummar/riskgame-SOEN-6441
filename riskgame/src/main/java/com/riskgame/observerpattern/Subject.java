/**
 * 
 */
package com.riskgame.observerpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is Subject class to implement the observer pattern.
 * @author PIYUSH
 *
 */
public class Subject {
	private List<Observer> observers = new ArrayList<>();
	private String message;
	private String state;
	private String phase;
	private String reinforcementMessage;
	private String attackMessage;
	private String fortificationMessage;
	private String playerDominationViewMessage;
	private String playerPhaseViewMessage;
	private String playerCardExchangeViewMessage;
	
	/**
	 * This method will notify all oberver the changes were happened
	 */
	public void notifyAllObservers()
	{
		for(Observer observer : observers)
		{
			observer.update();
		}
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	/**
	 * This method will set reinforcement message for reinforcement phase
	 * @return reinforcementMessage
	 */
	public String getReinforcementMessage() {
		return reinforcementMessage;
	}
	
	/**
	 * This method will set reinforcement message for reinforcement phase
	 * @param reinforcementMessage
	 */
	public void setReinforcementMessage(String reinforcementMessage) {
		this.reinforcementMessage = reinforcementMessage;
		
		//notifyForReinforcement
		for(Observer observer : observers)
		{
			observer.reinforcemrentUpdate();
		}
	}
	
	/**
	 * This method will get attack message for attack phase
	 * @return attack message
	 */
	public String getAttackMessage() {
		return attackMessage;
	}
	
	/**
	 * This method will set attack message for attack phase
	 * @param attackMessage
	 */
	public void setAttackMessage(String attackMessage) {
		this.attackMessage = attackMessage;
		
		//notifyForAttack
		for(Observer observer : observers)
		{
			observer.attackUpdate();
		}
	}
	/**
	 * This method will get fortification message for fortification phase
	 * @return fortification message
	 */
	public String getFortificationMessage() {
		return fortificationMessage;
	}
	/**
	 * This method will set fortification message for fortification phase
	 * @param fortificationMessage
	 */
	public void setFortificationMessage(String fortificationMessage) {
		this.fortificationMessage = fortificationMessage;
		
		//notifyForFortification
		for(Observer observer : observers)
		{
			observer.fortificationUpdate();
		}
	}
	
	public String getPlayerDominationViewMessage() {
		return playerDominationViewMessage;
	}
	
	public void setPlayerDominationViewMessage(String playerDominationViewMessage) {
		this.playerDominationViewMessage = playerDominationViewMessage;
		
		//notifyForFortification
		for(Observer observer : observers)
		{
			observer.playerDominationUpdate();
		}
	}
	
	
	public String getPlayerPhaseViewMessage() {
		return playerPhaseViewMessage;
	}

	public void setPlayerPhaseViewMessage(String playerPhaseViewMessage) {
		this.playerPhaseViewMessage = playerPhaseViewMessage;
		
		for(Observer observer : observers)
		{
			observer.playerPhaseViewUpdate();
		}
	}
	
	public String getPlayerCardExchangeViewMessage() {
		return playerCardExchangeViewMessage;
	}

	public void setPlayerCardExchangeViewMessage(String playerCardExchangeViewMessage) {
		this.playerCardExchangeViewMessage = playerCardExchangeViewMessage;
		
		for(Observer observer : observers)
		{
			observer.playerCardExchangeViewUpdate();
		}
	}

	/**
	 * This method will attack observer to subject
	 * @param observer
	 */
	public void attach(Observer observer)
	{
		observers.add(observer);
	}
	
	/**
	 * This method will detach observer from subject
	 * @param observer
	 */
	public void detach(Observer observer)
	{
		boolean detachSuccess = observers.remove(observer);
		if(detachSuccess == true)
		{
			System.out.println(observer + " is detached successfully.");
		}
		else
		{
			System.out.println(observer + " is not found in observer list to be detached.");
		}
	}	
}
