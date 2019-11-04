package com.riskgame.service.Impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.riskgame.model.GamePlayPhase;
import com.riskgame.model.Player;
import com.riskgame.model.RiskCard;
import com.riskgame.model.RiskCardExchange;
import com.riskgame.service.RiskPlayInterface;

/**
 * This is a implementation class of RiskPlayInterface where all buisness logic.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @author <a href="mailto:ko_pate@encs.concordia.ca">Koshaben Patel</a>
 * 
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Player
 * @see com.riskgame.model.Territory com.riskgame.model.PlayerTerritory
 */

@Service
public class RiskPlayImpl implements RiskPlayInterface {
	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "CAVALRY";
	public static final String ARTILLERY = "ARTILLERY";
	public static int updatedArmy;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#checkForReinforcement(int)
	 */
	@Override
	public int checkForReinforcement(int totalOwnedCountries) {
		int total = Math.floorDiv(totalOwnedCountries, 3);
		int totalArmyforReinforce = Math.max(total, 3);
		return totalArmyforReinforce;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#makeCards(int)
	 */
	@Override
	public Map<Integer, String> makeCards(int noOfCountries) {
		Map<Integer, String> cardMap = new HashMap<>();
		/*
		 * for (int i = 0; i < noOfCountries; i = i + 3) { cardMap.put(i, INFANTRY); }
		 * for (int i = 1; i < noOfCountries; i = i + 3) { cardMap.put(i, INFANTRY); }
		 * for (int i = 2; i < noOfCountries; i = i + 3) { cardMap.put(i, INFANTRY); }
		 */
		return cardMap;
	}

	/**
	 * @see com.riskgame.service.RiskPlayInterface#checkForContinentControlValue(com.riskgame.model.GamePlayPhase)
	 */
	@Override
	public int checkForContinentControlValue(GamePlayPhase gamePlayPhase) {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#updateArmyAfterCardExchange(com.riskgame.model.Player)
	 */
	@Override
	public int updateArmyAfterCardExchange(Player player) {
		if (player.getExchangeCount() == 0) {
			updatedArmy = 5;
			player.setExchangeCount(1);
		} else if (player.getExchangeCount() == 1) {
			updatedArmy = 10;
			player.setExchangeCount(2);
		} else if (player.getExchangeCount() == 2) {
			updatedArmy = 15;
			player.setExchangeCount(3);
		} else if (player.getExchangeCount() == 3) {
			updatedArmy = 20;
			player.setExchangeCount(4);
		} else if (player.getExchangeCount() == 4) {
			updatedArmy = 25;
			player.setExchangeCount(5);
		} else if (player.getExchangeCount() == 5) {
			updatedArmy = 30;
			player.setExchangeCount(6);
		}
		return updatedArmy;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.RiskPlayInterface#updateCardListAfterExchange(com.riskgame.model.Player,
	 *      java.util.List, com.riskgame.model.RiskCardExchange)
	 */
	@Override
	public void updateCardListAfterExchange(Player player, List<RiskCard> cards, RiskCardExchange exchangedCards) {
		cards.add(exchangedCards.getExchange1());
		cards.add(exchangedCards.getExchange2());
		cards.add(exchangedCards.getExchange3());
		Iterator<RiskCard> itr = player.getCardListOwnedByPlayer().listIterator();
		while (itr.hasNext()) {
			RiskCard riskCard = itr.next();
			if ((riskCard.getCardNumber() == exchangedCards.getExchange1().getCardNumber()
					&& riskCard.getArmyType().equalsIgnoreCase(exchangedCards.getExchange1().getArmyType()))
					|| (riskCard.getCardNumber() == exchangedCards.getExchange2().getCardNumber()
							&& riskCard.getArmyType().equalsIgnoreCase(exchangedCards.getExchange2().getArmyType()))
					|| (riskCard.getCardNumber() == exchangedCards.getExchange3().getCardNumber()
							&& riskCard.getArmyType().equalsIgnoreCase(exchangedCards.getExchange3().getArmyType()))) {
				itr.remove();
			}
		}
	}
}
