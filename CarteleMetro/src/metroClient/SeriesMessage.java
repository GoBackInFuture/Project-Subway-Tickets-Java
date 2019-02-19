package metroClient;

public class SeriesMessage extends MessageToServer {
	private int from;
	private int to;
	
	SeriesMessage(int from, int to){
		super(MessageToServer.sendSeriesDetails);
		this.setFrom(from);
		this.setTo(to);
	}


	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
}
