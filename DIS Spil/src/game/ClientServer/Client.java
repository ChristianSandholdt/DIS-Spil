package game.ClientServer;

import java.net.Socket;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            Socket serverSocket = new Socket(SERVER_IP, SERVER_PORT);
            clientThread clientThread = new clientThread(serverSocket);
            clientThread.start();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ClientThread extends Thread {

    private Socket serverSocket;

    public ClientThread(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            // Handle server communication
            // Read and process messages from the server
            // Update the game state
            // Send messages to the server
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        // Send a message to the server
    }
}





