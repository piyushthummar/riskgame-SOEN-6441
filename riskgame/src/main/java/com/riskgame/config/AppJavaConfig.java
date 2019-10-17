package com.riskgame.config;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.riskgame.logging.ExceptionWriter;

/**
 * Configure JavaFx with springBoot
 * @author <a href="mailto:p_thumma@encs.concordia.ca">Piyush Thummar</a>
 * @version 1.0.0
 */
@Configuration
public class AppJavaConfig {
	
    @Autowired 
    SpringFXMLLoader springFXMLLoader;

    /**
     * Useful when dumping stack trace to a string for logging.
     * @return ExceptionWriter contains logging utility methods
     */
    @Bean
    @Scope("prototype")
    public ExceptionWriter exceptionWriter() {
        return new ExceptionWriter(new StringWriter());
    }

    /**
     * It'll take data from bundle properties
     * @return data from bundle properties
     */
    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }
    
    /**
     * Stage only created after Spring context bootstap
     * @param stage Given FXML scene
     * @return object of stageManager
     * @throws IOException
     */
    @Bean
    @Lazy(value = true) 
    public StageManager stageManager(Stage stage) throws IOException {
        return new StageManager(springFXMLLoader, stage);
    }

}
