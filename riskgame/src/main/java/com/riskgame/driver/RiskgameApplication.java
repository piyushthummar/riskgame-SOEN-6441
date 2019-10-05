package com.riskgame.driver;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.riskgame.view.Menus;

@SpringBootApplication
public class RiskgameApplication extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 4763613486747091827L;

	public RiskgameApplication() {

		initUI();
	}

	private void initUI() {

		Menus frame = new Menus();
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		System.out.println();
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(RiskgameApplication.class).headless(false)
				.run(args);

		EventQueue.invokeLater(() -> {

			RiskgameApplication ex = ctx.getBean(RiskgameApplication.class);
			ex.setVisible(true);
		});
	}

}
