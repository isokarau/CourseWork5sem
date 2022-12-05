package modules.core.session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modules.core.models.Manager;

public class Session {
    public static Socket socket;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;

    public static Manager obj;

    public static void start() {
        String host = "127.0.0.1";
        int port = 8080;
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            // String line = "abcde";
            // out.println(line);
            // out.flush();
            // System.out.println("Server replied " + in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // stop();
        
    }

    public static void stop() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public static void SendMessageToServer(String message) {
        try {
            out.writeObject(message);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String GetMessageFromServer() {
        try {
            String line = (String)in.readObject();
            return line;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error";
    }
}
