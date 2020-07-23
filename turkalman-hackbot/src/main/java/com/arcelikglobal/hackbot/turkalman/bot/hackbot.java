package com.arcelikglobal.hackbot.turkalman.bot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
//import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.arcelikglobal.hackbot.turkalman.utilities.DBConnection;


public class hackbot extends TelegramLongPollingBot{
	// If you are helping develop this bot, please ensure that you do not share the following private variables with anyone!
	private String botToken = "1301764983:AAFEhSz9q6gB-Lhn6BqS_eQXOwelWHEdZJY";
	private String botUsername = "arcelik_hackbot"; ///< Without '@'
	//private long batikansChatId = (long) 597803356; ///< For testing purposes
	private long botId = Long.parseLong("1301764983");
	
	private String lastQuestion = null;

	private long departmentChatId = Long.parseLong("-1001460310430");
	
	public void onUpdateReceived(Update update) {
		//
		//System.out.println(update.getInlineQuery().toString());
		if (update.hasMessage()) {
			

			Message msg = update.getMessage();
			SendMessage toSend = new SendMessage();

			int updateMessageId = msg.getMessageId();
			

			
			
			System.out.println(msg.toString());
			System.out.println(msg.getChatId().toString());
			
			if (msg.getDice() != null) {
				// basket, dart, dice ...
				int score = msg.getDice().getValue();
				
				toSend.setText(msg.getDice().getEmoji().toString() + " Skorunuz: " + score);
				toSend.setChatId(msg.getChatId());
				toSend.setReplyToMessageId(updateMessageId);
				try {
					//Thread.sleep(3500);
					execute(toSend);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //catch (InterruptedException e) {
					
					//e.printStackTrace();
				//}
			}
			
			else if (msg.getFrom().getBot() == false) {
				
				if (msg.hasText()) {
					
					String text = msg.getText();
					String textLower = text.toLowerCase();
					//long chatId = msg.getChatId();
					
					long fromId = update.getMessage().getFrom().getId();
					if (msg.getChatId() == fromId) { /// IF ON PRIVATE CHAT
						if (textLower.contentEquals("hayır") || textLower.contentEquals("hayir")) {
							// To be replaced with the click on a (maybe inline) button
								if (lastQuestion != null) {
									
									toSend.setText("Son sorunuz ilgili departmana yonlendiriliyor");
									//toSend.setReplyToMessageId(updateMessageId);
									toSend.setChatId(fromId); ///< Bot needs to have been started etc...
									//toSend.setChatId(batikansChatId);
									

									
									// Forwaring message to the respective department
									// !!!Department may be chosen regarding several factors during deployment phase
									
									forwardMessageToDepartment(fromId, updateMessageId);
									
									lastQuestion = null;
									
									try {
										System.out.println(toSend.toString());
										execute(toSend); ///< Sending message object to user
										return;
									} catch (TelegramApiException e) {
										
										e.printStackTrace();
									}
								
									
								}

								

							} else {
								lastQuestion = textLower; ///< Buyuk harfleri varken de kaydedilebilirlerdi...
								
							}
					} else {
						// On group chat, either with other bots or with a department
						if (msg.getReplyToMessage() != null) {
							if (msg.getReplyToMessage().getFrom().getId() == botId) {
								// if this bots message is being replied to
								// THEN IT IS AN ANSWER
								
								
								
								// IF IT STARTS WITH TAG, IT IS A TAG.
								if (msg.getText().toLowerCase().startsWith("tag")) {
									// SAVE THE TAG TO DB
									String[] tags = msg.getText().toLowerCase().substring(3).stripLeading().split(" ");
									for (String tag : tags) {
										DBConnection.addTag(msg.getReplyToMessage().getMessageId(), tag);
									}

								} else {
									// SAVE THE ANSWER TO DB
									DBConnection.addAnswer(msg.getReplyToMessage().getMessageId(), msg.getText());
									
								}
							}
						}
					}
					
					

					
				} else if (msg.hasSticker()) {
					
				}
				
				// musteriler 3 konuda dokunuyor, bilgi istemek, bir islem yapmak, bir konuda sikayette bulunmak
				// bu 3 case tipi
				// bayi sorabilir, bu urunu nereden alabilirm diye
				// kampanya detaylari sorabilir
				// butun bilgileri su an cagri merkezine yolluyorlar onlar cevapliyorlar
				// siparisim nerde vs bilgi amacli sorular...
				// iyi pisirmiyor, aldim fiyatini begenmedim vs.../
				
				
				// mock data girip databaseye prototip olarak girmeye calisabilirsin bir seyleri
				// juri ne yapmaya calistigimiza odaklanacaktir
				
				// nihai hedef musterinin cagri merkezini aramamasi, buna gerek olmamasi
				// chatbot cevap veremedigii durumda ilgili departmana iletildi su kadar sure icerisinde cevap gelir seklinde tamamdir
				
				// operasyonel yonlendirmeler: Uye girisi yaparak kargonuzu takip edebilirsiniz
				// mumkun oldugunda text analiz kismina gitme
				// asil odak yonlendirme olsun yani!
			
				// musteri hangi sayfada ne kadar vakit gecirmis varmis gibi dusun bu bilgiler hazirda, ona gore kupon onerisi yapilabilir
				
				// musteri klimalari gezdi, odemeyle ilgili problem yasadi vs....
				
				// adaptiflik, entegre olabilirlik de sonrasi icin olmali
				
				// buzdolabi alicam mavi isik nedir
				// klima alicam 9000 btu mu 10000 btu mu iyidir 
			
			} else {
				// Message comes from another bot
				toSend.setText("Botlardan gelen mesajlara cevap veremiyoruz, özür dileriz.");
				toSend.setChatId(msg.getChatId());
				toSend.setReplyToMessageId(updateMessageId);
				try {
					execute(toSend);
					return;
				} catch (TelegramApiException e) {
					
					e.printStackTrace();
				} 
			}

		} else {
			// Update doesn't have message
			System.out.println("No mesage on update");
		}

	}


	

	
	public String getBotUsername() {
		
		return botUsername;
	}

	@Override
	public String getBotToken() {
	
		return botToken;
	}

	public boolean forwardMessageToDepartment(final long fromId, final int messageId ) {
		// maybe take departmentChatId as parameter aswell
		// 
		
		ForwardMessage fmsg = new ForwardMessage(departmentChatId, fromId, messageId);
		
		try {
			execute(fmsg);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
			
		
		return false;
	
		
	}
	 

}
