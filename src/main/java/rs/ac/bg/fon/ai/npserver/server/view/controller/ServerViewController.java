package rs.ac.bg.fon.ai.npserver.server.view.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.npserver.server.Server;
import rs.ac.bg.fon.ai.npserver.server.view.ServerView;
import rs.ac.bg.fon.ai.npserver.server.view.util.ServerTableModel;
import rs.ac.bg.fon.ai.npserver.threads.ClientHandler;

public class ServerViewController {

    Server server;
    ServerView frame;
    List<ClientHandler> handlerList;

    public ServerViewController(Server server) {
        this.server = server;
        frame = new ServerView();
        handlerList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("hh:mm dd.MM.yyyy.");
        frame.getLblVremePokretanja().setText(df.format(System.currentTimeMillis()));
        df = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy.");
        frame.getLblVremeAzuriranja().setText(df.format(System.currentTimeMillis()));
        frame.getTblKorisnici().setModel(new ServerTableModel());
        frame.getLblTrenutnoStanje().setText("Server nije pokrenut");
        frame.getLblTrenutnoStanje().setForeground(Color.red);
        addActionListeners();
        frame.setVisible(true);
    }

    public void addClient(ClientHandler clientHandler) {
        handlerList.add(clientHandler);
    }

    public void updateView() {
        ((ServerTableModel) frame.getTblKorisnici().getModel()).update(handlerList);
        DateFormat df = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy.");
        frame.getLblVremeAzuriranja().setText(df.format(System.currentTimeMillis()));
    }

    public void removeClient(ClientHandler clientHandler) {
        handlerList.remove(clientHandler);
    }

    public void editClient(ClientHandler clientHandler) {
        for (int i = 0; i < handlerList.size(); i++) {
            if (handlerList.get(i).getVremePridruzivanja() == clientHandler.getVremePridruzivanja()) {
                handlerList.remove(i);
                handlerList.add(i, clientHandler);
            }
        }
    }

    private void addActionListeners() {
        frame.getBtnPokreniS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.startServer();
                frame.getLblTrenutnoStanje().setText("Server je pokrenut");
                frame.getLblTrenutnoStanje().setForeground(Color.green);
                handlerList.addAll(server.getHandlerList());
                ((ServerTableModel) frame.getTblKorisnici().getModel()).update(handlerList);
            }
        });
        frame.getBtnZaustavi().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopSocket();
                frame.getLblTrenutnoStanje().setText("Server nije pokrenut");
                frame.getLblTrenutnoStanje().setForeground(Color.red);
                handlerList.removeAll(handlerList);
                ((ServerTableModel) frame.getTblKorisnici().getModel()).update(handlerList);
            }
        });
    }
}
