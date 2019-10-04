package com.riskgame;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RiskgameApplication extends JFrame{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 4763613486747091827L;

	public RiskgameApplication() {

	        initUI();
	    }

	    private void initUI() {

	        JButton quitButton = new JButton("Quit");
	        JButton ashish = new JButton("Ashish");

	        quitButton.addActionListener((ActionEvent event) -> {
	        	System.out.println("Button Click");
	            System.exit(0);
	        });

	        createLayout(quitButton,ashish);
	        //createLayout(ashish);
	        
	        

	        setTitle("Quit button");
	        setSize(300, 200);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	    }

	    private void createLayout(JComponent... arg) {

	        Container pane = getContentPane();
	        GroupLayout gl = new GroupLayout(pane);
	        pane.setLayout(gl);

	        gl.setAutoCreateContainerGaps(true);

	        gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[0]));
	        gl.setVerticalGroup(gl.createSequentialGroup().addComponent(arg[0]));
	        
	        
	        //gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(arg[1]));
	        //gl.setVerticalGroup(gl.createSequentialGroup().addComponent(arg[1]));
	        
	    }

	    public static void main(String[] args) {

	        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(RiskgameApplication.class)
	                .headless(false).run(args);

	        EventQueue.invokeLater(() -> {

	            RiskgameApplication ex = ctx.getBean(RiskgameApplication.class);
	            ex.setVisible(true);
	        });
	    }

}
