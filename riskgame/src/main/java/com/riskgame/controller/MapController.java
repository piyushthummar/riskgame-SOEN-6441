package com.riskgame.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;

import com.riskgame.dto.ContinentDto;
import com.riskgame.dto.NeighbourTerritoriesDto;
import com.riskgame.dto.CountryDto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;


/**
 * This class is the entry point of HTTP Client Library Implementation.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Controller
public class MapController implements Initializable {

	@FXML
	private TableColumn<ContinentDto, Integer> colContinentId;

	@FXML
	private ComboBox<String> continentComboBox;

	@FXML
	private TableColumn<ContinentDto, Integer> colContinentValue;

	@FXML
	private TableColumn<CountryDto, Boolean> colEditCountry;

	@FXML
	private MenuItem deleteCountry;

	@FXML
	private TableColumn<CountryDto, String> colCountryName;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, Integer> colNeighborId;

	@FXML
	private ComboBox<String> countryComboBox;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, String> colCountryNeighbor;

	@FXML
	private MenuItem deleteContinent;

	@FXML
	private TableView<NeighbourTerritoriesDto> neighborTable;

	@FXML
	private TableColumn<CountryDto, Integer> colCountryId;

	@FXML
	private Button btnNeighbor;

	@FXML
	private TableColumn<ContinentDto, String> colContinentName;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, Boolean> colEditNeighbor;

	@FXML
	private TableColumn<CountryDto, String> colCountryContinent;

	@FXML
	private TableColumn<ContinentDto, Boolean> colEditContinent;

	@FXML
	private Button btnCountry;

	@FXML
	private TextField continentValue;

	@FXML
	private TableView<ContinentDto> continentTable;

	@FXML
	private Button btnContinent;

	@FXML
	private TableView<CountryDto> countryTable;

	@FXML
	private TextField countryName;

	@FXML
	private MenuItem deleteNeighbor;

	@FXML
	private TextField continentName;

	@FXML
	private TableColumn<NeighbourTerritoriesDto, String> colNeighborName;

	@FXML
	private ComboBox<String> neighborComboBox;

	@FXML
	private Label lblContinentId;

	@FXML
	private Label lblCountryId;

	@FXML
	private Label lblNeighbourId;

	private static int continentId = 1;
	private static int countryId = 1;
	private static int neighbourId = 1;

	private ObservableList<ContinentDto> continentList = FXCollections.observableArrayList();
	private ObservableList<CountryDto> countryList = FXCollections.observableArrayList();
	private ObservableList<NeighbourTerritoriesDto> neighbourList = FXCollections.observableArrayList();

	private ObservableList<String> continentComboValue = FXCollections.observableArrayList();

	private ObservableList<String> countryComboValue = FXCollections.observableArrayList();

	private ObservableList<String> neighbourComboValue = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		continentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		countryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		neighborTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		setContinentTableColumnProperties();
		setCountryTableColumnProperties();
		setNeighborTableColumnProperties();

		loadContinentDetails();
		loadCountryDetails();
		loadNeighbourDetails();
	}

	

}
