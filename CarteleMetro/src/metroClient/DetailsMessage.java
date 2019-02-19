package metroClient;

public class DetailsMessage extends MessageToServer {
	private int id;
	
	public DetailsMessage(int id) {
		
		super(MessageToServer.sendCardDetails);
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
