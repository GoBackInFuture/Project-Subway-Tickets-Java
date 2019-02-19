package metroClient;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CardManagementFrame extends JFrame{
	
	public CardManagementFrame() {
		
		setSize(MenuPanel.newFrameWidth, MenuPanel.newFrameHeight);
		setLayout(new GridBagLayout());
		
		JPanel topPanel = new JPanel();
		
		topPanel.setLayout(new GridBagLayout());
		
		JLabel idCard = new JLabel("Id cartela:");
		JTextField idCardField = new JTextField();
		
		idCardField.setPreferredSize(new Dimension(MenuPanel.textFieldWidth, MenuPanel.textFieldHeight));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		
		topPanel.add(idCard, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		
		topPanel.add(idCardField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		
		add(topPanel, gbc);
		
		JPanel bottomPanel = new JPanel();
		
		JButton validate = new JButton("Validare");
		
		validate.addActionListener(al2 ->{
			if(!idCardField.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "Campul introdus nu este un numar!");
				idCardField.setText("");
			}
			else
			{
				try {
					Launcher.oos.writeObject(new ValidateMetroCard(Integer.parseInt(idCardField.getText())));
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	
		gbc.gridy = 1;
		
		JButton detalii = new JButton("Detalii cartela");
		
		detalii.addActionListener(al2 ->{
			if(!idCardField.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "Campul introdus nu este un numar!");
				idCardField.setText("");
			}
			else
			{
				try {
					Launcher.oos.writeObject(new DetailsMessage(Integer.parseInt(idCardField.getText())));
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		bottomPanel.add(validate);
		bottomPanel.add(detalii);
		
		add(bottomPanel, gbc);
		
	}
	
	public void displayFrame() {
		setVisible(true);
	}
}