package metroClient;

public class ValidateMetroCard extends MessageToServer {

	private int cardCode;
	
	ValidateMetroCard(int code) {
		super(MessageToServer.validateMetroCard);
		setCardCode(code);

	}

	public int getCardCode() {
		return cardCode;
	}

	public void setCardCode(int cardCode) {
		this.cardCode = cardCode;
	}

	
}
