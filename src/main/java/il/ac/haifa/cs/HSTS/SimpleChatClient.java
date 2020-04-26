package il.ac.haifa.cs.HSTS;

import java.io.IOException;
import java.util.logging.Logger;

import il.ac.haifa.cs.HSTS.ocsf.client.AbstractClient;

public class SimpleChatClient extends AbstractClient {
	private static final Logger LOGGER =
			Logger.getLogger(SimpleChatClient.class.getName());
	
	private HSTSClient hstsClient;
	public SimpleChatClient(String host, int port) {
		super(host, port);
		this.hstsClient = new HSTSClient(this);
	}
	
	@Override
	protected void connectionEstablished() {
		// TODO Auto-generated method stub
		super.connectionEstablished();
		LOGGER.info("Connected to server.");
		
		try {
			hstsClient.loop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		hstsClient.displayMessage(msg);
	}
	
	@Override
	protected void connectionClosed() {
		// TODO Auto-generated method stub
		super.connectionClosed();
		hstsClient.closeConnection();
	}
}
