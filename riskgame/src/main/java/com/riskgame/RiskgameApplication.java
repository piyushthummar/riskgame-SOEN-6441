package com.riskgame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.riskgame.config.StageManager;
import com.riskgame.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the main entry point for RiskgameApplication and it extends
 * Application (Which is JavaFx class), as we want it to load through JavaFx.
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 * @version 1.0.0
 */
@Slf4j
@SpringBootApplication
public class RiskgameApplication extends Application {
	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	/**
	 * This the main method and the entry point for the class
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		Application.launch(args);
	}

	/**
	 * To assign main method to springBootApplicationContext
	 * 
	 * @see javafx.application.Application#init()
	 */
	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
		log.info("\nSystem started");
	}

	/**
	 * Initilaization of stageManager
	 * 
	 * @param stage
	 *            JavaFx view page
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, stage);
		displayInitialScene();
	}

	/**
	 * This method will close springContext
	 * 
	 * @see javafx.application.Application#stop()
	 */
	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main window.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.WELCOME, null);
	}

	/**
	 * @return builder.run(args); It'll will return ConfigurableApplicationContext
	 *         of springBootApplicationContext
	 */
	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(RiskgameApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
		
	}

}
