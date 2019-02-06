package sss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIHandler extends JFrame {
	
	// A part of BeanBoyBot
	// Copyright 2017 Ben Massey
	// https://github.com/BenjaminMassey/BeanBoyBot
	
	private static final long serialVersionUID = 1L;
	
	private static GUIHandler frame;
	private static JPanel panel;
	private static JPanel main;
	private static JButton startButtonConfig;
	private static JButton startButtonNonConfig;
	
	public static boolean approval = false;
	
	public static void createWindow(String name, String icon) {
		// Create and set up the window
        frame = new GUIHandler(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Display the window
        ImageIcon ico = new ImageIcon(icon);
        frame.setIconImage(ico.getImage());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(250,225);
	}
	
	public GUIHandler(String name) {
        super(name);

        panel = generatePanel();

        main = new JPanel();
        main.setLayout(new CardLayout());

        main.add(panel);
        
        add(main);
        
        
        // Handle exit
        addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent we) {
        		System.exit(1);
        		if(TwitchChat.connected) {
        			try{
        				TwitchChat.deactivate();
        			}catch(Exception e) {
        				System.err.println("Oops: " + e);
        			}
        		}
        	}
        	public void windowIconified(WindowEvent we) {}
			public void windowActivated(WindowEvent we) {}
			public void windowClosed(WindowEvent we) {}
			public void windowDeactivated(WindowEvent we) {}
			public void windowDeiconified(WindowEvent we) {}
			public void windowOpened(WindowEvent we) {}
        });
    }
	
	private static JButton generateStartButton() {
		JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae) {
        		// Stop the bot
        		if(TwitchChat.connected) {
        			try{
        				TwitchChat.deactivate();
        				startButtonConfig.setText("Start");
            			startButtonNonConfig.setText("Start");
        			}catch(Exception e) {
        				System.err.println("Oops: " + e);
        			}
        		}
        		// Start the bot
        		else {
        			try{
        				try{
            				AccountsManager.updateAll();
            				TwitchChat.initialize();
            				startButtonConfig.setText("Stop");
                			startButtonNonConfig.setText("Stop");
            			}catch(Exception e) {
            				System.err.println("Error: " + e);
            				JOptionPane.showMessageDialog(null,"Failed to initialize...perhaps no internet?");
            			}
        			}catch(Exception e) {
        				System.err.println("Error: " + e);
        				JOptionPane.showMessageDialog(null,"Could not connect to LiveSplit - make sure you started the server!");
        			}
        		}
        	}
        });
        return startButton;
	}
	
	private static JPanel generatePanel() {

		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(9,1));

		// Put on a title label
		jp.add(new JLabel("                BeanBoyBot Twitch Bot                ", SwingConstants.CENTER));

		// Entry for what channel the bot should be in
		JTextField chatChannel = new JTextField(20);
		if(!AccountsManager.getChatChannel().substring(1).equals("ailed D:"))
			chatChannel.setText(AccountsManager.getChatChannel().substring(1));
		jp.add(chatChannel);
		// Button to confirm channel
		JButton channelButton = new JButton("Set Chat Channel");
		jp.add(channelButton);
		channelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				AccountsManager.setChatChannel(chatChannel.getText());
			}
		});

		// Entry for the bot's name
		JTextField botName = new JTextField(20);
		if(!AccountsManager.getBotName().equals("Failed D:"))
			botName.setText(AccountsManager.getBotName());
		jp.add(botName);
		// Button to confirm bot's name
		JButton botNameButton = new JButton("Set Bot Name");
		jp.add(botNameButton);
		botNameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				AccountsManager.setBotName(botName.getText());
			}
		});

		// Entry for bot oauth code (will be obscured very simply)
		JTextField botOauth = new JTextField(20);
		if(!AccountsManager.getBotOauth().equals("Failed D:"))
			botOauth.setText("****************");
		jp.add(botOauth);
		// Button to confirm bot's oauth code
		JButton botOauthButton = new JButton("Set Bot Oauth");
		jp.add(botOauthButton);
		botOauthButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				AccountsManager.setBotOauth(botOauth.getText());
				botOauth.setText("****************");
			}
		});

		// Blank space for spacing
		JLabel blank = new JLabel("");
		jp.add(blank);

		// Button that toggles the bot on and off
		startButtonNonConfig = generateStartButton();
		jp.add(startButtonNonConfig);

		return jp;

	}
	
}
