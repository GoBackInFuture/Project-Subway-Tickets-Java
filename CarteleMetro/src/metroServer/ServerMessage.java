package metroServer;

import java.io.Serializable;

public class ServerMessage implements Serializable {
	
	public static final int noRemainingTripsLeft = 0;
	public static final int idNotFound = 1;
	public static final int validatedWithSuccess = 2;
	public static final int minutesNotPassed = 3;
	public static final int subscriptionExpired = 4;
	public static final int cardDetails = 5;
	public static final int seriesDetails = 6;
	
	private int code;
	
	public ServerMessage(int code) {
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
