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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> convertRiskMapToDtos(RiskMap riskMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAvailableMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveMapToFile(RiskMap riskMap)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return adaptee.saveMapToFile(riskMap);
	}

	@Override
	public RiskMap readMap(String fileName) {
		// TODO Auto-generated method stub
		return adaptee.readMap(fileName);
	}

	@Override
	public boolean validateMap(RiskMap riskMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getNeighbourCountriesListByCountryName(RiskMap riskMap, String countryName) {
		// TODO Auto-generated method stub
		return null;
	}

}