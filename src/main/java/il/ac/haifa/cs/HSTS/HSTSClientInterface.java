package il.ac.haifa.cs.HSTS;

import il.ac.haifa.cs.HSTS.ocsf.server.Entities.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// That was CLIChatClient
public class HSTSClientInterface {

	private HSTSClient client;
	private boolean isRunning;
	private static final String SHELL_STRING = "Enter command (or exit to quit)> ";
	private Thread loopThread;

	public HSTSClientInterface(HSTSClient client) {
		this.client = client;
		this.isRunning = false;
	}

	public void loop() throws IOException {
		loopThread = new Thread(new Runnable() {

			@Override
			public void run() {
				/* Here we have to write Client logic (in while->try scope)
				* Open login window interface, asking the user to write details.
				* Then, send request to the server for obtaining User according to the details
				* Etc.....		*/

				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String message;
				Command command;
				while (client.isConnected()) {
					System.out.print(SHELL_STRING);

					try {
						message = reader.readLine();
						StringTokenizer stringTokenizer = new StringTokenizer(message);
						String[] tokens = new String[stringTokenizer.countTokens()];
						int i = 0;
						while (stringTokenizer.hasMoreTokens()) {
							tokens[i] = stringTokenizer.nextToken();
							i++;
						}
						command = null;
						if (i == 9) {
						command = new Command(tokens[1],tokens[0],new Question(tokens[2]
						,tokens[3],tokens[4],tokens[5],tokens[6],Integer.parseInt(tokens[7]),tokens[8]));
						}
						if (message.isBlank())
							continue;
						if (message.equalsIgnoreCase("help")) {
							System.out.println("Enter [push] [questions] ['Question'] [Ans1] [Ans2] [Ans3] [Ans4] [Correct answer] [Writer]");
						}
						else if (message.equalsIgnoreCase("exit")) {
							System.out.println("Closing connection.");
								client.closeConnection();
						} else {
							client.sendToServer(command);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		loopThread.start();
		this.isRunning = true;

	}

	public void displayMessage(Object message) {
		if (isRunning) {
			System.out.print("(Interrupted)\n");
		}
		System.out.println("Received message from server: " + message.toString());
		if (isRunning)
			System.out.print(SHELL_STRING);
	}

	public void closeConnection() {
		System.out.println("Connection closed.");
		System.exit(0);
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Required arguments: <host> <port>");
		} else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);

			HSTSClient chatClient = new HSTSClient(host, port);
			chatClient.openConnection();
		}
	}
}
