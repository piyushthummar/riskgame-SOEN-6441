package com.riskgame.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;
import com.riskgame.dto.NeighbourTerritoriesDto;
import com.riskgame.model.RiskMap;
import com.riskgame.service.ConquestMapInterface;
import com.riskgame.service.MapManagementInterface;

/**
 * This is the adapter class to handle given map fileName from user and call
 * respective conquest map management method for further operations.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.service.ConquestMapInterface
 * @version 1.0.0
 */
public class DominationToConquestAdapter implements MapManagementInterface {

	private ConquestMapInterface adaptee;

	/**
	 * parameterise constructor of this class having object of ConquestMapInterface
	 * 
	 * @param adaptee
	 */
	public DominationToConquestAdapter(ConquestMapInterface adaptee) {
		super();
		this.adaptee = adaptee;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#convertToRiskMap(java.util.List,
	 *      java.util.List, java.util.List)
	 */
	@Override
	public RiskMap convertToRiskMap(List<ContinentDto> continentList, List<CountryDto> countryList,
			List<NeighbourTerritoriesDto> neighbourList) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#convertRiskMapToDtos(com.riskgame.model.RiskMap)
	 */
	@Override
	public Map<String, Object> convertRiskMapToDtos(RiskMap riskMap) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#getAvailableMap()
	 */
	@Override
	public List<String> getAvailableMap() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#saveMapToFile(com.riskgame.model.RiskMap)
	 */
	@Override
	public boolean saveMapToFile(RiskMap riskMap)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		return adaptee.saveMapToFile(riskMap);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#readMap(java.lang.String)
	 */
	@Override
	public RiskMap readMap(String fileName) {
		return adaptee.readMap(fileName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#validateMap(com.riskgame.model.RiskMap)
	 */
	@Override
	public boolean validateMap(RiskMap riskMap) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#getNeighbourCountriesListByCountryName(com.riskgame.model.RiskMap,
	 *      java.lang.String)
	 */
	@Override
	public List<String> getNeighbourCountriesListByCountryName(RiskMap riskMap, String countryName) {

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.riskgame.service.MapManagementInterface#isMapConquest(java.lang.String)
	 */
	@Override
	public boolean isMapConquest(String fileName) {
		return false;
	}

}
