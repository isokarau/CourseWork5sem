package modules.core;

import modules.core.server.Server;


public class App {
    public static void main(String[] args) {
        Server srv = new Server();
        srv.start();
    }
}
