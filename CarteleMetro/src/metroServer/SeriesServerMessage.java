package metroServer;

public class SeriesServerMessage extends ServerMessage {

	private Object[][] data;
	
	public SeriesServerMessage(Object[][] data){
		super(ServerMessage.seriesDetails);
		
		this.data = data;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
}
