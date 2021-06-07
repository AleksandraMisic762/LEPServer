package rs.ac.bg.fon.ai.npserver.threads;

import rs.ac.bg.fon.ai.npserver.server.Server;
import rs.ac.bg.fon.ai.npserver.server.view.controller.ServerViewController;

public class ServerGUIThread extends Thread{
    
    Server server;
    ServerViewController cntrl;

    public ServerGUIThread(Server server) {
        this.server = server;
    }

    public void addClient(ClientHandler clientHandler) {
        cntrl.addClient(clientHandler);
    }
    
    public void removeClient(ClientHandler clientHandler) {
        cntrl.removeClient(clientHandler);
    }
    
    public void editClient(ClientHandler clientHandler){
        cntrl.editClient(clientHandler);
    }

    @Override
    public void run() {
        cntrl = new ServerViewController(server);
        try {
            while(true){
                Thread.sleep(5000);
                cntrl.updateView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
