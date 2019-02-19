package metroClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import metroServer.CardDetails;
import metroServer.SeriesServerMessage;
import metroServer.ServerMessage;

public class Launcher {

	private static final int PORT = 1234;
	private static final String ADDRESS = "127.0.0.1";
	
	static ObjectInputStream ois;
	static ObjectOutputStream oos;
	
	public static void main(String[] args) {
		
		COBIANU IONUT ADRIAN - GRUPA 241 9
		try {
			
			Socket socket = new Socket(ADDRESS, PORT);
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			MenuGUI meniu = new MenuGUI();
			meniu.displayMenu();
			
			Thread thread = new Thread() {
				
				public void run() {
					while(true) {
						try {
							ServerMessage message = (ServerMessage)ois.readObject();
							switch(message.getCode()) {
							case ServerMessage.cardDetails:
								meniu.displayCardDetails((CardDetails)message);
								break;
							case ServerMessage.seriesDetails:
								meniu.displaySeriesDetails((SeriesServerMessage)message);
							default:
								meniu.displayMessage(message.getCode());
								break;
								
								
							}
							
							
							
							
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			};
			
			thread.start();
			thread.join();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
