package game.ClientServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final int PORT = 1234;
    private static List<clientThread> clients = new ArrayList<clientThread>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientThread clientThread = new clientThread(clientSocket);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void broadcast(String message, clientThread sender) {
        for (clientThread client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}

class clientThread extends Thread {

    private Socket clientSocket;
    private String username;

    public clientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // Handle client communication
            // Read and process messages from the client
            // Update the game state
            // Send messages to all other clients
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        // Send a message to the client
    }
}
