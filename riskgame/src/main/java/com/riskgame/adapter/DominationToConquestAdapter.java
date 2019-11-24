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

public class DominationToConquestAdapter implements MapManagementInterface {
	
	private ConquestMapInterface adaptee;
	
    public DominationToConquestAdapter(ConquestMapInterface adaptee) {
          super();
          this.adaptee = adaptee;
    }

	@Override
	public RiskMap convertToRiskMap(List<ContinentDto> continentList, List<CountryDto> countryList,
			List<NeighbourTerritoriesDto> neighbourList) {		
		return null;
	}

	@Override
	public Map<String, Object> convertRiskMapToDtos(RiskMap riskMap) {	
		return null;
	}

	@Override
	public List<String> getAvailableMap() {		
		return null;
	}

	@Override
	public boolean saveMapToFile(RiskMap riskMap)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {	
		return adaptee.saveMapToFile(riskMap);
	}

	@Override
	public RiskMap readMap(String fileName) {
		return adaptee.readMap(fileName);
	}

	@Override
	public boolean validateMap(RiskMap riskMap) {
		return false;
	}

	@Override
	public List<String> getNeighbourCountriesListByCountryName(RiskMap riskMap, String countryName) {
		
		return null;
	}

	@Override
	public boolean isMapConquest(String fileName) {		
		return false;
	}

}
