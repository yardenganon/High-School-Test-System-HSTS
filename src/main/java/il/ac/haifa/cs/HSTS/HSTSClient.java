package il.ac.haifa.cs.HSTS;

import java.io.IOException;
import java.util.logging.Logger;

import il.ac.haifa.cs.HSTS.ocsf.client.AbstractClient;

public class HSTSClient extends AbstractClient {
	private static final Logger LOGGER =
			Logger.getLogger(HSTSClient.class.getName());
	
	private HSTSClientInterface hstsClientInterface;
	public HSTSClient(String host, int port) {
		super(host, port);
		this.hstsClientInterface = new HSTSClientInterface(this);
	}
	
	@Override
	protected void connectionEstablished() {
		// TODO Auto-generated method stub
		super.connectionEstablished();
		LOGGER.info("Connected to server.");
		
		try {
			hstsClientInterface.loop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		hstsClientInterface.displayMessage(msg);
	}
	
	@Override
	protected void connectionClosed() {
		// TODO Auto-generated method stub
		super.connectionClosed();
		hstsClientInterface.closeConnection();
	}
}
