package com.riskgame.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import org.slf4j.Logger;

import com.riskgame.controller.RiskPlayScreenController;
import com.riskgame.view.FxmlView;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages switching Scenes on the Primary Stage
 * 
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 * @version 1.0.0
 */
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    /**
     * This is a parameterize controller of this class and it'll initialize given values in args.
     * @param springFXMLLoader will make object from XML file
     * @param stage is a current view
     */
    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    /**
     * This method will help to switch the scene from one to another
     * @param view is scene to show
     * @param object
     */
    public void switchScene(final FxmlView view,Object object) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile(),object);
        show(viewRootNodeHierarchy, view.getTitle());
    }
    
    /**
     * This method will show the view of the title given in arguments
     * @param rootnode is a parent node of scene
     * @param title is name of the view
     */
    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        
        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }
    
    /**
     * This method will prepare scene (view) before loading
     * @param rootnode is parent Node of scene
     * @return scene
     */
    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath,Object object) {
        Parent rootNode = null;
        try {
            FXMLLoader loader = springFXMLLoader.load(fxmlFilePath);
            
            if(fxmlFilePath.contains("RiskPlayScreen")) {
            	//FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            	//loader.setLocation(getClass().getResource(fxmlFilePath));
            	rootNode = loader.load();
            	RiskPlayScreenController riskPlayScreenController = loader.getController();
            	riskPlayScreenController.transferGamePlayPhase(object);
            }else {
            	rootNode = loader.load();
            }
            
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }
    
    
    /**
     * This method will log everything and close the screen
     * @param errorMsg
     * @param exception
     */
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

}
