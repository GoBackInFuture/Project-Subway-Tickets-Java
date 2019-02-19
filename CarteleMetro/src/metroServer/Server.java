package metroServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);				
			Socket socket = serverSocket.accept();

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			ServerThread serverThread = new ServerThread(ois, oos);
			serverThread.start();
			//serverThread.join();
				
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
}
