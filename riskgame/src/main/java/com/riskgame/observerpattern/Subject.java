/**
 * 
 */
package com.riskgame.observerpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is Subject class to implement the observer pattern.
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
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
	 * This method will notify all obervers the changes that were happened
	 */
	public void notifyAllObservers()
	{
		for(Observer observer : observers)
		{
			observer.update();
		}
	}
	
	/**
	 * This method gives the update message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * This method sets the update message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * This method gives the current state of the game
	 * @return state
	 */
	public String getState() {
		return state;
	}
	/**
	 * This method sets the current state of the game
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * This method gives the current phase of the game
	 * @return
	 */
	public String getPhase() {
		return phase;
	}
	/**
	 * This method sets the current phase of the game
	 * @param phase
	 */
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
	 * This method will get fortification message for fortification phase.
	 * @return fortification message
	 */
	public String getFortificationMessage() {
		return fortificationMessage;
	}
	/**
	 * This method will set fortification message for fortification phase.
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
	
	/**
	 * This method gives the message for playerWorldDominationView.
	 * @return playerDominationViewMessage
	 */
	public String getPlayerDominationViewMessage() {
		return playerDominationViewMessage;
	}
	
	/**
	 * This method sets the message for playerWorldDominationView.
	 * @param playerDominationViewMessage
	 */
	public void setPlayerDominationViewMessage(String playerDominationViewMessage) {
		this.playerDominationViewMessage = playerDominationViewMessage;
		
		//notifyForFortification
		for(Observer observer : observers)
		{
			observer.playerDominationUpdate();
		}
	}
	
	
	/**
	 * This method gives the message for getPlayerPhaseView.
	 * @return getPlayerPhaseViewMessage
	 */
	public String getPlayerPhaseViewMessage() {
		return playerPhaseViewMessage;
	}

	/**
	 * This method sets the message for getPlayerPhaseView.
	 * @param playerPhaseViewMessage
	 */
	public void setPlayerPhaseViewMessage(String playerPhaseViewMessage) {
		this.playerPhaseViewMessage = playerPhaseViewMessage;
		
		for(Observer observer : observers)
		{
			observer.playerPhaseViewUpdate();
		}
	}
	
	/**
	 * This method gives the message for getPlayerCardExchangeView.
	 * @return getPlayerCardExchangeViewMessage
	 */
	public String getPlayerCardExchangeViewMessage() {
		return playerCardExchangeViewMessage;
	}

	/**
	 * This method gives the message for getPlayerCardExchangeView.
	 * @param playerCardExchangeViewMessage
	 */
	public void setPlayerCardExchangeViewMessage(String playerCardExchangeViewMessage) {
		this.playerCardExchangeViewMessage = playerCardExchangeViewMessage;
		
		for(Observer observer : observers)
		{
			observer.playerCardExchangeViewUpdate();
		}
	}

	/**
	 * This method will attach observer to subject.
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
