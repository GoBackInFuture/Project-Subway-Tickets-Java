package metroServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import metroClient.AddCardsMessage;
import metroClient.DetailsMessage;
import metroClient.MessageToServer;
import metroClient.SeriesMessage;
import metroClient.ValidateMetroCard;

public class ServerThread extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "12345678";
	
	private boolean isOpen = true;
	
	private static final int numberOfColumns = 5;

	public ServerThread(ObjectInputStream ois, ObjectOutputStream oos){
		this.ois = ois;
		this.oos = oos;
	}

	private void addMetroCardsToDatabase(AddCardsMessage message) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", DB_USERNAME,DB_PASSWORD);){
			int from, to, numberTickets;

			from = message.getFromBatch();
			to = message.getToBatch();
			numberTickets = message.getNumberOfTravels();

			String type = message.getType();
			PreparedStatement ps = null;

			if(type.equals("2 calatorii") || type.equals("10 calatorii"))
			{
				ps = con.prepareStatement("INSERT INTO metrocards(idmetrocard, type, numberTicketsLeft, "
						+ "dateOfExpiration)" + " VALUES(?, ?, ?, SYSDATE() + INTERVAL 180 DAY)");
				// Must create date
			}
			else {
				ps = con.prepareStatement("INSERT INTO metrocards(idmetrocard, type, numberTicketsLeft)"
						+ " VALUES(?, ?, ?)");
			}

			ps.setString(2, type.toLowerCase());
			ps.setInt(3, numberTickets);		

			for(int i = from; i <= to; ++i) {
				ps.setInt(1, i);
				ps.executeUpdate();
			}

			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		while(isOpen == true) {
			try {
				MessageToServer message = (MessageToServer) ois.readObject();
				switch(message.getCode()) {
				case MessageToServer.addMetroCardsToDatabase:
					addMetroCardsToDatabase((AddCardsMessage)message);
					break;
				case MessageToServer.validateMetroCard:
					validateCard((ValidateMetroCard)message);
					break;
				case MessageToServer.sendCardDetails:
					sendCardDetails((DetailsMessage)message);
					break;
				case MessageToServer.sendSeriesDetails:
					sendSeriesMessage((SeriesMessage)message);
					break;
				case MessageToServer.closeApp:
					isOpen = false;
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void sendSeriesMessage(SeriesMessage message) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", DB_USERNAME,DB_PASSWORD);){

			PreparedStatement ps = con.prepareStatement("Select * from metrocards where idMetroCard >= ? AND idMetroCard <= ?");

			int max = Integer.max(message.getFrom(), message.getTo());
			int min = Integer.min(message.getFrom(), message.getTo());

			ps.setInt(1, min);
			ps.setInt(2, max);

			ResultSet rs = ps.executeQuery();

			int numberOfRows = 0;

			while(rs.next()) {
				++numberOfRows;
			}

			rs.close();

			Object[][] data = new Object[numberOfRows][numberOfColumns];

			rs = ps.executeQuery();

			numberOfRows = 0;

			while(rs.next()) {

				for(int i = 0; i < numberOfColumns; ++i)
				{
					data[numberOfRows][i] = rs.getObject(i + 1);
				}

				++numberOfRows;
			}

			SeriesServerMessage sm = new SeriesServerMessage(data);

			oos.writeObject(sm);
			oos.flush();

			rs.close();
			ps.close();


		} catch (SQLException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendCardDetails(DetailsMessage message) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", DB_USERNAME,DB_PASSWORD);){
			int id = message.getId();

			PreparedStatement ps = con.prepareStatement("SELECT * from metrocards where idMetroCard = ?");
			ps.setInt(1, id);

			//System.out.println(id);

			ResultSet rs = ps.executeQuery();

			CardDetails cd = null;

			if(rs.next()) {

				cd = new CardDetails(rs.getInt("numberTicketsLeft"),rs.getString("type"),
						rs.getDate("lastCheckTime") != null ? rs.getDate("lastCheckTime") .toString(): "N\\A", 
								rs.getDate("dateOfExpiration") != null ? rs.getDate("dateOfExpiration").toString() : "N\\A");

				oos.writeObject(cd);
				oos.flush();

			}
			else
			{
				oos.writeObject(new ServerMessage(ServerMessage.idNotFound));
				oos.flush();
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void validateCard(ValidateMetroCard message) {
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", DB_USERNAME,DB_PASSWORD);){

			int id = message.getCardCode();

			PreparedStatement ps4 = con.prepareStatement("SELECT TIMESTAMPDIFF(DAY, dateOfExpiration, NOW()) as 'DayDifference'\r\n" + 
					"FROM metrocards\r\n" + 
					"where idmetrocard = ?");

			ps4.setInt(1,id);

			ResultSet rs4 = ps4.executeQuery();

			int days = -1;
			if(rs4.next()) {
				days = rs4.getInt("DayDifference");	
			}

			rs4.close();
			ps4.close();

			if((days >= 1))
			{
				ServerMessage sm = new ServerMessage(ServerMessage.subscriptionExpired);
				oos.writeObject(sm);
				oos.flush();
			}
			else
			{

				PreparedStatement ps = con.prepareStatement("SELECT * from metrocards where idMetroCard = ?");
				ps.setInt(1, id);

				ResultSet rs = ps.executeQuery();

				String type = null;
				int remainingTripsLeft = -1;

				if(rs.next()) {
					type = rs.getString("type");
					remainingTripsLeft = rs.getInt("numberTicketsLeft");

					if(type.equals("2 calatorii") || type.equals("10 calatorii")) {
						if(remainingTripsLeft == 0)
						{
							ServerMessage serverMessage = new ServerMessage(ServerMessage.noRemainingTripsLeft);
							oos.writeObject(serverMessage);
							oos.flush();
						}
						else
						{
							PreparedStatement ps2 = con.prepareStatement("UPDATE metrocards\r\n" + 
									"SET numberTicketsLeft = ?, lastCheckTime = SYSDATE()\r\n" + 
									"WHERE idMetroCard = ?");

							ps2.setInt(1, (remainingTripsLeft - 1));
							ps2.setInt(2, id);

							ps2.executeUpdate();

							ServerMessage serverMessage = new ServerMessage(ServerMessage.validatedWithSuccess);
							oos.writeObject(serverMessage);
							oos.flush();

							ps2.close();

						}
					}
					else {
						Date lastCheckTime = rs.getDate("lastCheckTime");
						Date dateOfExpiration = rs.getDate("dateOfExpiration");

						if(lastCheckTime == null && dateOfExpiration == null) {

							PreparedStatement ps2 = null;

							if(type.equals("abonament de o luna")) {
								ps2 = con.prepareStatement("UPDATE metrocards\r\n" + 
										"SET lastCheckTime = SYSDATE(), dateOfExpiration = SYSDATE() + INTERVAL 30 DAY \r\n" + 
										"WHERE idmetroCard = ?");
							}
							else
								if(type.equals("abonament de o zi")) {
									ps2 = con.prepareStatement("UPDATE metrocards\r\n" + 
											"SET lastCheckTime = SYSDATE(), dateOfExpiration = SYSDATE() + INTERVAL 1 DAY \r\n" + 
											"WHERE idmetroCard = ?");
								}

							ps2.setInt(1, id);
							ps2.executeUpdate();

							ServerMessage sm = new ServerMessage(ServerMessage.validatedWithSuccess);
							oos.writeObject(sm);
							oos.flush();

							ps2.close();
						}
						else {
								PreparedStatement ps2 = con.prepareStatement("SELECT TIMESTAMPDIFF(MINUTE,lastCheckTime,NOW()) as 'ultimaValidare'\r\n" + 
										"FROM metrocards\r\n" + 
										"Where idMetroCard = ?");
								ps2.setInt(1, id);

								ResultSet rs2 = ps2.executeQuery();

								if(rs2.next()) {
									int timp = rs2.getInt("ultimaValidare");

									if(timp < 15) {
										ServerMessage sm = new ServerMessage(ServerMessage.minutesNotPassed);
										oos.writeObject(sm);
										oos.flush();
									}
									else
									{
										PreparedStatement ps3 = con.prepareStatement("UPDATE metrocards\r\n" + 
												"SET lastCheckTime = NOW()\r\n" + 
												"WHERE idmetrocard = ?");
										ps3.setInt(1, id);

										ps3.executeUpdate();

										ServerMessage sm = new ServerMessage(ServerMessage.validatedWithSuccess);
										oos.writeObject(sm);
										oos.flush();

										ps3.close();
									}
								}
								rs2.close();
								ps2.close();
							}
						}
				}
				else {
					ServerMessage serverMessage = new ServerMessage(ServerMessage.idNotFound);
					oos.writeObject(serverMessage);
					oos.flush();
				}

				rs.close();
				ps.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
