package sock;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Server {
	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(6969);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			(new ServerThread(connectionSocket)).start();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			String clientSentence = inFromClient.readLine();
			System.out.println(clientSentence);
			if (clientSentence.isBlank() != true) {
				outToClient.writeBytes("GameIsGo" + '\n' );
				while (true) {
					clientSentence = inFromClient.readLine();
					System.out.println(clientSentence);
				}
			}
			else {
				outToClient.writeBytes("Du er en idiot" + '\n' );
			}
		}
	}
}
