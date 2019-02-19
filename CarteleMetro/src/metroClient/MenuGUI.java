package metroClient;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import metroServer.CardDetails;
import metroServer.SeriesServerMessage;

public class MenuGUI extends JFrame {

	private final static int windowWidth = 1024;
	private final static int windowHeight = 768;
	
	private MenuPanel menu;
	
	public MenuGUI(){
		super("Metro Software");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(windowWidth, windowHeight);
		
		menu = new MenuPanel();
		setContentPane(menu);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	MessageToServer ms = new MessageToServer(MessageToServer.closeApp);
		    	try {
					Launcher.oos.writeObject(ms);
					Launcher.oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	
		    }
		});
		
		
	}

	public void displayMenu() {
		setVisible(true);
	}

	void displayMessage(int code) {
		menu.displayMessage(code);
	}

	public void displayCardDetails(CardDetails message) {
		menu.displayCardDetails(message);
		
		// TODO Auto-generated method stub
		
	}

	public void displaySeriesDetails(SeriesServerMessage message) {
		// TODO Auto-generated method stub
		menu.displaySeriesDetails(message);
	}
}
