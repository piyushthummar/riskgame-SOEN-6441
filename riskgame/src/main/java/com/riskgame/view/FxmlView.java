package com.riskgame.view;

import java.util.ResourceBundle;

/**
 * This the FXML View enum, which we is holding vlue respective FXML page
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
public enum FxmlView {

	/**
	 * This is the enum of welcome page
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 */
	WELCOME {

		/**
		 * @see com.riskgame.view.FxmlView#getTitle()
		 */
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("welcome.title");
		}

		/**
		 * @see com.riskgame.view.FxmlView#getFxmlFile()
		 */
		@Override
		public String getFxmlFile() {
			return "/fxml/Welcome.fxml";
		}
	},

	/**
	 * Enum for Map management screen
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 */
	MAP {
		/**
		 * @see com.riskgame.view.FxmlView#getTitle()
		 */
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("map.title");
		}

		/**
		 * @see com.riskgame.view.FxmlView#getFxmlFile()
		 */
		@Override
		public String getFxmlFile() {
			return "/fxml/Map.fxml";
		}
	},

	/**
	 * This is startupPhase enum which will redirect to startup screen, which can
	 * add player, loadmap, populatecountries, placeall etc.
	 * 
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 */
	STARTUPPHASE {

		/**
		 * @see com.riskgame.view.FxmlView#getTitle()
		 */
		@Override
		public String getTitle() {

			return getStringFromResourceBundle("startupphase.title");
		}

		/**
		 * @see com.riskgame.view.FxmlView#getFxmlFile()
		 */
		@Override
		public String getFxmlFile() {

			return "/fxml/StartupPhase.fxml";
		}

	},
	
	/**
	 * This is playGame enum which can redirect to playGame screen for playing realted operation
	 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
	 */
	PLAYGAME {

		/**
		 * @see com.riskgame.view.FxmlView#getTitle()
		 */
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("riskgame.title");
		}

		/**
		 * @see com.riskgame.view.FxmlView#getFxmlFile()
		 */
		@Override
		public String getFxmlFile() {

			return "/fxml/RiskPlayScreen.fxml";
		}

	};

	/**
	 * This method will return title of FXML file
	 * 
	 * @return title of FXML file
	 */
	public abstract String getTitle();

	/**
	 * This method will return FXML file
	 * 
	 * @return FXML file
	 */
	public abstract String getFxmlFile();

	/**
	 * This method will return String, which will displayed on top of FXML view
	 * 
	 * @param key
	 * @return
	 */
	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
