package com.riskgame.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.CountryDto;
import com.riskgame.dto.NeighbourTerritoriesDto;
import com.riskgame.model.RiskMap;

/**
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @see com.riskgame.model.RiskMap
 * @see com.riskgame.model.Continent
 * @see com.riskgame.model.Territory */
public interface MapManagementInterface {

	public RiskMap convertToRiskMap(List<ContinentDto> continentList,List<CountryDto> countryList,List<NeighbourTerritoriesDto> neighbourList);
	public Map<String, Object> convertRiskMapToDtos (RiskMap riskMap);
	public List<String> getAvailableMap();
	public boolean saveMapToFile(RiskMap riskMap) throws UnsupportedEncodingException, FileNotFoundException, IOException;
	public RiskMap readMap(String fileName);
	public boolean validateMap(RiskMap riskMap);
	
}
