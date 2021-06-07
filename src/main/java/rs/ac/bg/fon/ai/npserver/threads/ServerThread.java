package rs.ac.bg.fon.ai.npserver.threads;

import java.net.ServerSocket;
import java.net.Socket;

import rs.ac.bg.fon.ai.npserver.server.Server;

public class ServerThread extends Thread {

    Server server;
    ServerSocket serverSocket;

    public ServerThread(Server server) {
        this.server = server;
        serverSocket = server.getServerSocket();
    }

    @Override
    public void run() {
        startServer();
        System.out.println("server zaustavljen");
    }

    public void startServer() {
        try {
            while (server.isSignal()) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                server.handleClient(socket);
            }
        } catch (Exception ex) {
            
        }
    }
}
