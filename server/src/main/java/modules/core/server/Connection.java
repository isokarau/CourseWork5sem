package modules.core.server;

import java.io.*;
import java.net.Socket;

public class Connection {
    public ObjectOutputStream out = null;
    public ObjectInputStream in = null;
    public Socket clientSocket;

    public void setClientSocket(Socket clienSocket) {
        this.clientSocket = clienSocket;
    }

    public void init() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            System.out.println("Client disconnected " + clientSocket.getInetAddress().getHostAddress());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendMessageToClient(String message) {
        try {
            out.writeObject(message);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String GetMessageFromClient() {
        try {
            String line = (String)in.readObject();
            return line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}