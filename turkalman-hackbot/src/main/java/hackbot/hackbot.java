package hackbot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class hackbot extends TelegramLongPollingBot{
	// If you are helping develop this bot, please ensure that you do not share the following private variables with anyone!
	private String botToken = "1301764983:AAFEhSz9q6gB-Lhn6BqS_eQXOwelWHEdZJY";
	private String botUsername = "arcelik_hackbot"; ///< Without '@'
	//private long batikansChatId = (long) 597803356; ///< For testing purposes
	
	public void onUpdateReceived(Update update) {
		
		//System.out.println(update.getInlineQuery().toString());
		if (update.hasMessage()) {
			

			Message msg = update.getMessage();
			SendMessage toSend = new SendMessage();

			int updateMessageId = msg.getMessageId();
			

			
			
			System.out.println(msg.toString());
			
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
					
					if (textLower.contains("arcelik") || textLower.contains("arçelik")) {
					
						if (textLower.contains("hey")) {
							
							
							toSend.setText("Hey!");
							//toSend.setReplyToMessageId(updateMessageId);
							toSend.setChatId(fromId); ///< Bot needs to have been started etc...
							//toSend.setChatId(batikansChatId);
							try {
								System.out.println(toSend.toString());
								execute(toSend); ///< Sending message object to user
								return;
							} catch (TelegramApiException e) {
								
								e.printStackTrace();
							} 
						}

					}
					

					
				} else if (msg.hasSticker()) {
					
				}
				
				
				
				
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

	

}
