package rs.ac.bg.fon.ai.npserver.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.npserver.threads.ClientHandler;
import rs.ac.bg.fon.ai.npserver.threads.ServerGUIThread;
import rs.ac.bg.fon.ai.npserver.threads.ServerThread;

public class Server {

    ServerSocket serverSocket;
    ServerGUIThread serverGUIThread;
    List<ClientHandler> handlerList;
    boolean signal; 

    public Server() {
        handlerList = new ArrayList<>();
        signal = true;
        
        serverGUIThread = new ServerGUIThread(this);
        serverGUIThread.start();
    }
    
    public void startServer() {
        try {
            serverSocket = new ServerSocket(9000);
            signal = true;
            new ServerThread(this).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void handleClient(Socket socket) throws Exception {
        ClientHandler clientHandler = new ClientHandler(this, socket);
        handlerList.add(clientHandler);
        serverGUIThread.addClient(clientHandler);
        clientHandler.start();
    }

    public void stopClient(ClientHandler clientHandler) {
        handlerList.remove(clientHandler);
        serverGUIThread.removeClient(clientHandler);
    }

    public void loggedIn(ClientHandler clientHandler) {
        for(int i = 0; i < handlerList.size(); i++){
            if(handlerList.get(i).getVremePridruzivanja() == clientHandler.getVremePridruzivanja()){
                handlerList.remove(i);
                handlerList.add(i, clientHandler);
            }
        }
        serverGUIThread.editClient(clientHandler);
    }
    
    public void stopSocket(){
        try {
            signal = false;
            for(ClientHandler ch : handlerList){
                ch.getSocket().close();
            }
            handlerList.removeAll(handlerList);
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ClientHandler> getHandlerList() {
        return handlerList;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public boolean isSignal() {
        return signal;
    }
}
