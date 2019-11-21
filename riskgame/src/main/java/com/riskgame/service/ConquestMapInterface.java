package com.riskgame.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.riskgame.model.RiskMap;

public interface ConquestMapInterface {
	
	/**
	 * This method will make returns true if riskmap file successfully created from
	 * data given from user side.
	 * 
	 * @param riskMap
	 *            is model sent by service side
	 * @return true if file got saved successfully
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	boolean saveMapToFile(RiskMap riskMap) throws UnsupportedEncodingException, FileNotFoundException, IOException;
	
	/**
	 * This method will read file and store data in riskMap Model into particular
	 * field
	 * 
	 * @param fileName
	 * @return RiskMap model
	 */
	RiskMap readMap(String fileName);

}
