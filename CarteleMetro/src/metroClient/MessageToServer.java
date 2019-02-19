package metroClient;

import java.io.Serializable;

public class MessageToServer implements Serializable {
	public static final int addMetroCardsToDatabase = 0;
	public static final int validateMetroCard = 1;
	public static final int sendCardDetails = 2;
	public static final int sendSeriesDetails = 3;
	public static final int closeApp = 4;
	
	private int code;

	MessageToServer(int code){
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
	
}
