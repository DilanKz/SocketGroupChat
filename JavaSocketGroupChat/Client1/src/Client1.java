import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        final String serverIP = "SERVER_IP_ADDRESS"; // Replace with the server's IP address
        final int port = 8000; // Specify the port number you want to use

        try{

            Socket socket = new Socket("localhost",port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            Scanner scanner=new Scanner(System.in);

            System.out.print("Enter your username: ");
            String name = scanner.nextLine();
            dataOutputStream.writeUTF(name);
            dataOutputStream.flush();



            while (true){
                System.out.print("Enter Message: ");
                String msg = scanner.nextLine();

                if (msg.equalsIgnoreCase("CLOSE")){
                    System.out.println("Chat Closed");
                    break;
                }

                dataOutputStream.writeUTF(msg);
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());


            }

            dataOutputStream.close();
            socket.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

