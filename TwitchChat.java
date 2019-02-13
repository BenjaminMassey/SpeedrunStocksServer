package sss;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import java.io.IOException;

public class TwitchChat extends PircBot {

	// A part of BeanBoyBot
	// Copyright 2017 Ben Massey
	// https://github.com/BenjaminMassey/BeanBoyBot

	// This class handles actually reading from and talking to Twitch Chat

	// Code for Twitch Chat functionality based on tutorial from this channel:
	// https://www.youtube.com/channel/UCoQuKOXYxUBeNWbendo3sF

	public static boolean connected = false;
	private static String channel; // What channel the bot should talk to/read from
	private static TwitchChat bot;
	//private static TwitchChat whisperBot;

	public TwitchChat() {
		// Quick mini setup

		this.setName(AccountsManager.getBotName());
		this.isConnected();
	}

	public static void initialize() throws NickAlreadyInUseException, IOException, IrcException {
		// Set up the Twitch Bot to be in chat
		//channel = AccountsManager.getChatChannel();
		connected = true;
		bot = new TwitchChat();
		bot.setVerbose(true);
		bot.connect("irc.twitch.tv", 6667, AccountsManager.getBotOauth());
		bot.sendRawLine("CAP REQ :twitch.tv/membership"); // Allows special stuff (viewer list)
		// Below is common permission but I don't need it yet
		//bot.sendRawLine("CAP REQ :twitch.tv/tags");
		bot.sendRawLine("CAP REQ :twitch.tv/commands"); // Need it to parse whispers

		bot.joinChannel("#beanssbm");

		TimeForPoints TFP = new TimeForPoints();
		new Thread(TFP).start();
		TFP.start();
	}

	public static void deactivate() throws IOException, IrcException {
		bot.disconnect();
		connected = false;
	}

	public static void connectToChat(String channelName) {
		bot.joinChannel(channelName);
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		// React to a given message
		System.out.println("GOT MESSSAGE : " + message);
		// Here are the commands that should be taken action for

		if (message.equalsIgnoreCase("!SplitGame")) {
			sendMessage(channel, "Now you can play a game in chat with the speedrun! "
					+ "The run acts like a stock. First step is to enter the "
					+ "game with !join. Then you can buy with !buy, sell with "
					+ "!sell and check your points with !points. You can whisper "
					+ "https://www.twitch.tv/" +  AccountsManager.getBotName() + " "
					+ "!help and/or !summary for more info.");
		}
		
		if (message.equalsIgnoreCase("!join")) {

		}
		
		if (message.equalsIgnoreCase("!buy")) {

		}

		if (message.equalsIgnoreCase("!sell")) {

		}
		
		if (message.equalsIgnoreCase("!points")) {

		}
	}
	
	protected void onUnknown(String line) {
		if(line.contains("WHISPER")) {
			String sender = line.split("!")[0].substring(1);
			String message = line.split("WHISPER ")[1].split(" :")[1];
			
			if (message.equalsIgnoreCase("!help")) {
				privateMessage(sender, "To get a basic synopsis of the game, message me "
						+ "!summary. To view all of the commands, message me !commands. Only "
						+ "!buy, !sell and !points will work in the main chat - everything "
						+ "else must be done through whsipers.");
			}
			
			if (message.equalsIgnoreCase("!summary")) {
				privateMessage(sender, "The current run will have a cost associated with it. "
						+ "The cost will change depending on the quality of the run - the "
						+ "more likely a PB the higher the price, the less likely the lower. "
						+ "You can buy or sell the current run at any time, so you want to "
						+ "buy it cheap, and sell it for a high profit. The major catch is "
						+ "that a reset means the run will autosell - at only 75% of its "
						+ "cost. So buy low, sell high, and avoid the resets. If you need "
						+ "more details, feel free to ask!");
			}
			
			if (message.equalsIgnoreCase("!commands")) {
				privateMessage(sender, "!join : join the game (start with 100 points) | "
						+ "!points : check your point count | "
						+ "!buy : invest in the current run at the current cost (on screen) | "
						+ "!sell : sell your current run for the current cost (on screen) | "
						+ "!investment : check how much you bought for (and if you bought) | "
						+ "!gamble XX : 50% chance to win XX points, 50% chance to lose XX points | "
						+ "!buymessage XX : put XX on stream for 12 seconds for 1000 points "
						+ "...for more commands, type !commands2");
			}
			
			if(message.equalsIgnoreCase("!commands2")) {
				privateMessage(sender,"!buyemote XX : put XX emote on stream for 8 seconds for 200 points | "
						+ "!leaderboard : show the top 5 point holders | "
						+ "!flex : attempt to show off your points in chat | "
						+ "!give XX YY : give user XX YY points | "
						+ "!contact : get email for contact about the bot | "
						+ "!buyimage XX : put image from XX URL in image queque");
			}
			
			if (message.equalsIgnoreCase("!points")) {

			}
			if (message.equalsIgnoreCase("!buy")) {

			}

			if (message.equalsIgnoreCase("!sell")) {

			}
			
			if (message.equalsIgnoreCase("!investment")) {

			}
			
			if (message.startsWith("!gamble ")) {

			}
			
			if (message.startsWith("!buymessage ")) {

			}
			
			if (message.startsWith("!buyemote ")) {

			}
			
			if (message.startsWith("!buyimage ")) {

			}
			
			if (message.startsWith("!give ")) {

			}
			
			if(message.startsWith("!take")) {

			}
			
			if(message.startsWith("!check")) {

			}
			
			if(message.equalsIgnoreCase("!leaderboard")) {

			}
			
			if (message.equalsIgnoreCase("!flex")) {

			}
			
			if (message.equalsIgnoreCase("!contact"))
				privateMessage(sender, "contact@speedrunstocks.com");
		}
	}
	
	public static String[] getViewers(String channelName) {
		try {
			User[] users = bot.getUsers(channelName);
			String[] viewers = new String[users.length];
			for(int i = 0; i < users.length; i++)
				viewers[i] = users[i].getNick();
			return viewers;
		}catch(Exception e) {
			return new String[0];
		}
	}

	private void privateMessage(String person, String message) {
		sendMessage(AccountsManager.getBotName(), "/w " + person + " " + message);
	}

	public static void outsideDEBUGMessage(String message) {
		bot.sendMessage("#beanssbm", message);
	}

}