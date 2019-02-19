package metroClient;

public class AddCardsMessage extends MessageToServer {
	
	private int fromBatch;
	private int toBatch;
	private String type;
	private int numberOfTravels;  
	
	private static final int numarCalatoriiCartela2 = 2;
	private static final int numarCalatoriiCartela10 = 10;
	private static final int numarCalatoriiAbonament = 99999999;
	
	private static final String douaCalatorii = "2 calatorii";
	private static final String zeceCalatorii = "10 calatorii";
	private static final String abonamentZi = "abonament de o zi";
	private static final String abonamentLunar = "abonament de o luna";
	
	AddCardsMessage(int fromBatch, int toBatch, String type){
		super(addMetroCardsToDatabase);
		
		this.fromBatch = fromBatch;
		this.toBatch = toBatch;
		
		this.type = type;
		
		switch(type.toLowerCase()) {
		case douaCalatorii:
			numberOfTravels = numarCalatoriiCartela2;
			break;
			
		case zeceCalatorii:
			numberOfTravels = numarCalatoriiCartela10;
			break;
			
		case abonamentZi:
		case abonamentLunar:
			numberOfTravels = numarCalatoriiAbonament;
			
			break;
			
		}
	}

	public int getFromBatch() {
		return fromBatch;
	}

	public void setFromBatch(int fromBatch) {
		this.fromBatch = fromBatch;
	}

	public int getToBatch() {
		return toBatch;
	}

	public void setToBatch(int toBatch) {
		this.toBatch = toBatch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberOfTravels() {
		return numberOfTravels;
	}

	public void setNumberOfTravels(int numberOfTravels) {
		this.numberOfTravels = numberOfTravels;
	}
	
}
