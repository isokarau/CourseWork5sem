package modules.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import modules.core.actions.Actions;

public class Server {
    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            serverSocket.setReuseAddress(true);

            System.out.println("Server is running");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler client = new ClientHandler(clientSocket);

                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ClientHandler implements Runnable {
        Connection con = new Connection();
        
        public ClientHandler(Socket socket) {
            this.con.setClientSocket(socket);
        }

        @Override
        public void run() {
            try {
                con.init();
                Actions act = new Actions(con);
                act.go();
            } finally {
                con.close();
            }
        }
    }
}
