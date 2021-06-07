package rs.ac.bg.fon.ai.npserver.server.view.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import rs.ac.bg.fon.ai.npserver.threads.ClientHandler;

public class ServerTableModel extends AbstractTableModel{

    List<ClientHandler> korisnici;
    String[] kolone = new String[]{"Redni broj", "Korisničko ime","Vreme pridruživanja"};
            
    public ServerTableModel() {
        korisnici = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return korisnici.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientHandler klijent = korisnici.get(rowIndex);
        DateFormat df = new SimpleDateFormat("hh:mm dd.MM.yyyy.");
        switch(columnIndex){
            default:
                return "n/a";
            case 0:
                return rowIndex + 1;
            case 1:
                return klijent.getKorisnik()==null?null:klijent.getKorisnik().getUsername();
            case 2:
                return df.format(klijent.getVremePridruzivanja());
        }
    }
    
    public void update(List<ClientHandler> korisnici){
        this.korisnici = korisnici;
        fireTableDataChanged();
    }
}
