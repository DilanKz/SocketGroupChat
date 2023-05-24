package Util;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiClientServer {
    private static List<ClientHandler> clients = new ArrayList<>();
    public static void main(String[] args) {
        final int port = 8000; // Specify the port number you want to use

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);

            for (int i = 0; i < 5; i++) {
                Socket clientSocket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                String clientName = dataInputStream.readUTF();
                System.out.println("Client connected: " + clientName);

                ClientHandler clientHandler = new ClientHandler(clientSocket,clientName);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void broadcastMessage(String name,String message) {
        for (ClientHandler client : clients) {
            if (!client.name.equals(name)){
                client.sendMessage(message);
            }
        }
    }
}
