package il.ac.haifa.cs.HSTS;

import java.io.IOException;

import il.ac.haifa.cs.HSTS.ocsf.server.AbstractServer;
import il.ac.haifa.cs.HSTS.ocsf.server.ConnectionToClient;

public class HSTSServer extends AbstractServer {

	public HSTSServer(int port) {
		super(port);
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		/* Here we have to write controller
		 * Getting user details from login window, send back The object or msg
		 * by "client.sendToClient("msg object");
		 * Etc.....		*/
		System.out.println("Received Message: " + msg.toString());
		sendToAllClients(msg);


	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Required argument: <port>");
		} else {
			HSTSServer server = new HSTSServer(Integer.parseInt(args[0]));
			server.listen();
		}
	}
}
