package com.riskgame.view;

import java.util.ResourceBundle;

public enum FxmlView {

	WELCOME {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("welcome.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Welcome.fxml";
		}
	},
	MAP {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("map.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Map.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
