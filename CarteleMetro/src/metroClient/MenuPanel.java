package metroClient;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import metroServer.CardDetails;
import metroServer.SeriesServerMessage;
import metroServer.ServerMessage;


public class MenuPanel extends JPanel {
	
	private static final String TYPE_OF_CARD_TEXT = "Tip Abonament";
	private static final String TO_SERIES_TEXT = "La seria: ";
	private static final String FROM_SERIES_TEXT = "De la seria:";
	
	private static final String addCards = "Adaugare cartele";
	private static final String validateCards = "Validare/Detalii cartela/abonament";
	private static final String cardDetails = "Detalii cartele";
	
	private static final String[] comboBoxOptions = {"2 calatorii", 
													"10 calatorii", 
													"Abonament de o zi", 
													"Abonament de o luna"};
	
	static final int textFieldWidth = 200;
	static final int textFieldHeight = 40;
		
	static final int newFrameWidth = 300;
	static final int newFrameHeight = 200;
	
	static final int cardDetailsFrameWidth = 500;
	static final int cardDetailsFrameHeight = 250;
	
	static final String[] columnNames = {"Id", "Type", "LastCheckTime", "NumberTicketsLeft", "DateOfExpiration"};
	
	private CardManagementFrame cardManagementFrame = new CardManagementFrame();

	private JButton addMetroCards;
	private JButton validateCard;
	private JButton showSeriesDetails;

	public MenuPanel(){
		
		setLayout(new GridBagLayout());
		
		addMetroCards = new JButton(addCards);
		validateCard = new JButton(validateCards);
		showSeriesDetails = new JButton(cardDetails);
		
		buildMetroCardsButton();
		buildValidateCardsButton();
		buildShowSeriesButton();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
			
		add(addMetroCards, gbc);
		
		gbc.gridy = 1;
		
		add(validateCard, gbc);
		
		gbc.gridy = 2;
		
		add(showSeriesDetails, gbc);
	}
	
	private void buildShowSeriesButton() {
		// TODO Auto-generated method stub
		showSeriesDetails.addActionListener(al -> {

			JFrame frame2 = new JFrame();
			frame2.setSize(newFrameWidth, newFrameHeight);
			frame2.setLayout(new GridBagLayout());
			
			JPanel topPanel = new JPanel();
			
			topPanel.setLayout(new GridBagLayout());
			
			JLabel from = new JLabel(FROM_SERIES_TEXT);
			JTextField fromField = new JTextField();
			
			JLabel to = new JLabel(TO_SERIES_TEXT);
			JTextField toField = new JTextField();
			
			fromField.setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
			toField.setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			
			topPanel.add(from, gbc);
			
			gbc.gridx = 1;
			gbc.weightx = 1.0;
			
			topPanel.add(fromField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0.1;
			
			topPanel.add(to, gbc);
			
			gbc.gridx = 1;
			
			topPanel.add(toField, gbc);
			
			GridBagConstraints gbc2 = new GridBagConstraints();
			
			gbc2.gridx = 0;
			gbc2.gridy = 0;
			gbc2.weightx = 1.0;
			
			frame2.add(topPanel, gbc2);
			
			gbc2.gridy = 1;
			
			JButton arataDetalii = new JButton("Arata Detalii");
			
			arataDetalii.addActionListener(al2 -> {
				if(!fromField.getText().matches("\\d+") || !toField.getText().matches("\\d+")) {
					JOptionPane.showMessageDialog(frame2, "Datele introduse nu sunt un numar valid!");
					fromField.setText("");
					toField.setText("");
				}
				else
				{
					try {
						frame2.setVisible(false);
						Launcher.oos.writeObject(new SeriesMessage(Integer.parseInt(fromField.getText()),
								Integer.parseInt(toField.getText())));
						
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			});
			
			
			frame2.add(arataDetalii, gbc2);
			
			frame2.setVisible(true);
		});
		
	}

	private void buildValidateCardsButton() {
		
		validateCard.addActionListener(al -> {
			cardManagementFrame = new CardManagementFrame();
			cardManagementFrame.displayFrame();
		});
	}

	private void buildMetroCardsButton() {
		
		addMetroCards.addActionListener(al ->{
			
			JFrame frame2 = new JFrame();
			frame2.setSize(newFrameWidth, newFrameHeight);
			frame2.setLayout(new GridBagLayout());
			
			JPanel topPanel = new JPanel();
			
			topPanel.setLayout(new GridBagLayout());
			
			JLabel from = new JLabel(FROM_SERIES_TEXT);
			JTextField fromField = new JTextField();
			
			JLabel to = new JLabel(TO_SERIES_TEXT);
			JTextField toField = new JTextField();
			
			fromField.setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
			toField.setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
			
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			
			topPanel.add(from, gbc);
			
			gbc.gridx = 1;
			gbc.weightx = 1.0;
			
			topPanel.add(fromField, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0.1;
			
			topPanel.add(to, gbc);
			
			gbc.gridx = 1;
			
			topPanel.add(toField, gbc);
			
			JPanel midPanel = new JPanel();
			
			JLabel comboLabel = new JLabel(TYPE_OF_CARD_TEXT);
			JComboBox<String> jcb = new JComboBox<String>(comboBoxOptions);
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			
			midPanel.add(comboLabel, gbc);
			
			gbc.gridx = 1;
			gbc.weightx = 1.0;
			
			midPanel.add(jcb, gbc);
			
			GridBagConstraints gbc2 = new GridBagConstraints();
			
			gbc2.gridx = 0;
			gbc2.gridy = 0;
			gbc2.weightx = 1.0;
			
			gbc2.fill = GridBagConstraints.HORIZONTAL;
			
			frame2.add(topPanel, gbc2);
			
			gbc2.gridy = 1;
			gbc2.fill = GridBagConstraints.NONE;
			
			frame2.add(midPanel, gbc2);
			
			gbc2.gridy = 2;
			
			JButton adauga = new JButton("Adaugare");
			
			adauga.addActionListener(al2 ->{
			
				if(!fromField.getText().matches("\\d+") || !toField.getText().matches("\\d+")) {
					JOptionPane.showMessageDialog(frame2, "Datele introduse nu sunt un numar valid!");
					fromField.setText("");
					toField.setText("");
				}
				else
				{
					try {
						frame2.setVisible(false);
						Launcher.oos.writeObject(new AddCardsMessage(Integer.parseInt(fromField.getText()),
								Integer.parseInt(toField.getText()), jcb.getSelectedItem().toString()));
						
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}			
			});
			
			frame2.add(adauga, gbc2);
			
			frame2.setVisible(true);
		});
	}

	public void displayMessage(int code) {
		// TODO Auto-generated method stub
		switch(code) {
		case ServerMessage.noRemainingTripsLeft:
			JOptionPane.showMessageDialog(cardManagementFrame, "Fara calatorii disponibile!");
			break;
		case ServerMessage.idNotFound:
			JOptionPane.showMessageDialog(cardManagementFrame, "Id-ul cartelei nu este disponibil!");
			break;
		case ServerMessage.minutesNotPassed:
			JOptionPane.showMessageDialog(cardManagementFrame, "Nu au trecut 15 minute!");
			break;
		case ServerMessage.validatedWithSuccess:
			JOptionPane.showMessageDialog(cardManagementFrame, "Validare cu succes!");
			break;
		case ServerMessage.subscriptionExpired:
			JOptionPane.showMessageDialog(cardManagementFrame, "Abonament expirat!");
			break;
		}
		
	}

	public void displayCardDetails(CardDetails message) {
		// TODO Auto-generated method stub
		JFrame cardDetailsFrame = new JFrame();
		cardDetailsFrame.setSize(cardDetailsFrameWidth, cardDetailsFrameHeight);
		cardDetailsFrame.setLayout(new GridBagLayout());
		
		JLabel type = new JLabel("TIP: ");
		JLabel numberTicketsAvailable = new JLabel("NUMAR CALATORII DISPONIBILE: ");
		JLabel lastValidation = new JLabel("ULTIMA VALIDARE: ");
		JLabel validUntil = new JLabel("VALABIL PANA LA: ");
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		cardDetailsFrame.add(type,gbc);
		
		gbc.gridy++;
		
		cardDetailsFrame.add(numberTicketsAvailable, gbc);
		
		gbc.gridy++;
		
		cardDetailsFrame.add(lastValidation, gbc);
		
		gbc.gridy++;
		
		cardDetailsFrame.add(validUntil, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		
		JTextField[] textFields = new JTextField[5];
	
		textFields[0] = new JTextField(message.getType());
		textFields[1] = new JTextField(Integer.toString(message.getNumberTicketsLeft()));
		textFields[2] = new JTextField(message.getLastCheckTime() != null ? message.getLastCheckTime() : "N\\A");
		textFields[3] = new JTextField(message.getDateOfExpiration() != null ? message.getDateOfExpiration() : "N\\A");
			
		for(int i = 0; i < 4; ++i, ++gbc.gridy)
		{
			textFields[i].setPreferredSize(new Dimension(textFieldWidth, textFieldHeight));
			textFields[i].setEditable(false);
			cardDetailsFrame.add(textFields[i], gbc);
		}
			
		
		cardDetailsFrame.setVisible(true);
		
	}

	public void displaySeriesDetails(SeriesServerMessage message) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setLayout(new GridBagLayout());
		
		
		JTable table = new JTable(message.getData(), columnNames);
		
		JScrollPane jsp = new JScrollPane(table);

		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		
		frame.add(jsp, gbc);
		
		frame.setVisible(true);
		
	}


	
	
}
