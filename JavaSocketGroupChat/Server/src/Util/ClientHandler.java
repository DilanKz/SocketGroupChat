package Util;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    DataOutputStream dataOutputStream;
    String name;

    public ClientHandler(Socket socket,String name) {
        this.clientSocket = socket;
        this.name=name;
    }

    public void run() {
        try {
            dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));

            while (true){
                String message = dataInputStream.readUTF();

                if (message.equalsIgnoreCase("CLOSE")){
                    System.out.println("Chat Closed");
                    break;
                }

                String received=this.name+" : "+message;

                System.out.println();

                MultiClientServer.broadcastMessage(this.name,received);

            }

            // Close the client socket
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendMessage(String reply) {
        try {
            dataOutputStream.writeUTF(reply);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
