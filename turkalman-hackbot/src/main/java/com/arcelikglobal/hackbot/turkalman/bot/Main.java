package com.arcelikglobal.hackbot.turkalman.bot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


import com.arcelikglobal.hackbot.turkalman.utilities.DBConnection;
public class Main {
	
	// Instantiate TelegramBotsApi and register the bot
	public static void main(String[] args) {
	
		// Initialize Api Context
		ApiContextInitializer.init();
		
		// Instantiate TelegramBots API
		TelegramBotsApi botsApi = new TelegramBotsApi();
		
		// Register the bot
		try {
			botsApi.registerBot(new hackbot());
			System.out.println("Bot regisered succesfully");
		} catch (TelegramApiRequestException e) {
			
			e.printStackTrace(); 
		}
		//DBConnection.connect();
		//DBConnection.addEntry(2, "2deneme2", "mrb");
	
	
		
		
	}
	
}

