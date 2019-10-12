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
	
	private void loadContinentDetails() {
		// TODO Auto-generated method stub

		// continentList.clear();
		continentTable.setItems(continentList);

	}

	private void loadCountryDetails() {

		List<String> continentInfo = continentList.stream().map(ContinentDto::getContinentName)
				.collect(Collectors.toList());
		continentComboValue.clear();
		continentComboValue.addAll(continentInfo);
		continentComboBox.setItems(continentComboValue);
		// countryList.clear();
		countryTable.setItems(countryList);

	}

//	private void loadContinentComboBox() {
//
//		
//
//	}

	private void loadNeighbourDetails() {

		List<String> countryInfo = countryList.stream().map(CountryDto::getCountryName).collect(Collectors.toList());

		countryComboValue.clear();
		countryComboValue.addAll(countryInfo);
		countryComboBox.setItems(countryComboValue);

		neighbourComboValue.clear();
		neighbourComboValue.addAll(countryInfo);
		neighborComboBox.setItems(neighbourComboValue);

		// neighbourList.clear();
		neighborTable.setItems(neighbourList);

	}

	private void setContinentTableColumnProperties() {
		// TODO Auto-generated method stub
		colContinentId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colContinentName.setCellValueFactory(new PropertyValueFactory<>("continentName"));
		colContinentValue.setCellValueFactory(new PropertyValueFactory<>("continentValue"));
		colEditContinent.setCellFactory(continentCellFactory);
	}

	private void setCountryTableColumnProperties() {
		// TODO Auto-generated method stub
		colCountryId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		colCountryContinent.setCellValueFactory(new PropertyValueFactory<>("continentName"));
		colEditCountry.setCellFactory(countryCellFactory);

	}

	private void setNeighborTableColumnProperties() {
		// TODO Auto-generated method stub
		colNeighborId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colCountryNeighbor.setCellValueFactory(new PropertyValueFactory<>("countryName"));
		colNeighborName.setCellValueFactory(new PropertyValueFactory<>("countryNeighbourName"));
		colEditNeighbor.setCellFactory(neighbourCellFactory);

	}

	Callback<TableColumn<ContinentDto, Boolean>, TableCell<ContinentDto, Boolean>> continentCellFactory = new Callback<TableColumn<ContinentDto, Boolean>, TableCell<ContinentDto, Boolean>>() {
		@Override
		public TableCell<ContinentDto, Boolean> call(final TableColumn<ContinentDto, Boolean> param) {
			final TableCell<ContinentDto, Boolean> cell = new TableCell<ContinentDto, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							ContinentDto continentDto = getTableView().getItems().get(getIndex());
							updateContinent(continentDto);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateContinent(ContinentDto continentDto) {
					lblContinentId.setText(String.valueOf(continentDto.getId()));
					continentName.setText(continentDto.getContinentName());
					continentValue.setText(String.valueOf(continentDto.getContinentValue()));

				}
			};
			return cell;
		}
	};

	Callback<TableColumn<CountryDto, Boolean>, TableCell<CountryDto, Boolean>> countryCellFactory = new Callback<TableColumn<CountryDto, Boolean>, TableCell<CountryDto, Boolean>>() {
		@Override
		public TableCell<CountryDto, Boolean> call(final TableColumn<CountryDto, Boolean> param) {
			final TableCell<CountryDto, Boolean> cell = new TableCell<CountryDto, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							CountryDto countryDto = getTableView().getItems().get(getIndex());
							updateCountry(countryDto);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateCountry(CountryDto countryDto) {
					lblCountryId.setText(String.valueOf(countryDto.getId()));
					countryName.setText(countryDto.getCountryName());
					continentComboBox.getSelectionModel().select(countryDto.getContinentName());

				}
			};
			return cell;
		}
	};

	Callback<TableColumn<NeighbourTerritoriesDto, Boolean>, TableCell<NeighbourTerritoriesDto, Boolean>> neighbourCellFactory = new Callback<TableColumn<NeighbourTerritoriesDto, Boolean>, TableCell<NeighbourTerritoriesDto, Boolean>>() {
		@Override
		public TableCell<NeighbourTerritoriesDto, Boolean> call(
				final TableColumn<NeighbourTerritoriesDto, Boolean> param) {
			final TableCell<NeighbourTerritoriesDto, Boolean> cell = new TableCell<NeighbourTerritoriesDto, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							NeighbourTerritoriesDto neighbourTerritoriesDto = getTableView().getItems().get(getIndex());
							updateNeighbour(neighbourTerritoriesDto);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

				private void updateNeighbour(NeighbourTerritoriesDto neighbourTerritoriesDto) {
					lblNeighbourId.setText(String.valueOf(neighbourTerritoriesDto.getId()));
					countryComboBox.getSelectionModel().select(neighbourTerritoriesDto.getCountryName());
					neighborComboBox.getSelectionModel().select(neighbourTerritoriesDto.getCountryNeighbourName());

				}
			};
			return cell;
		}
	};


	

}
