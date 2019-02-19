package metroServer;

public class CardDetails extends ServerMessage {
	
	private int numberTicketsLeft;
	private String dateOfExpiration;
	private String type;
	private String lastCheckTime;
	
	public CardDetails(int nrTickets, String type,String lct, String doe) {
		super(ServerMessage.cardDetails);
		
		setNumberTicketsLeft(nrTickets);
		setDateOfExpiration(doe);
		this.setType(type);
		setLastCheckTime(lct);
	}

	public String getLastCheckTime() {
		return lastCheckTime;
	}

	public void setLastCheckTime(String lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}

	public String getDateOfExpiration() {
		return dateOfExpiration;
	}

	public void setDateOfExpiration(String dateOfExpiration) {
		this.dateOfExpiration = dateOfExpiration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberTicketsLeft() {
		return numberTicketsLeft;
	}

	public void setNumberTicketsLeft(int numberTicketsLeft) {
		this.numberTicketsLeft = numberTicketsLeft;
	}
}
