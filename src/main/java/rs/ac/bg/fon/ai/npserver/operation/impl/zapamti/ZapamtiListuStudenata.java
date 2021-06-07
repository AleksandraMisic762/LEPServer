package rs.ac.bg.fon.ai.npserver.operation.impl.zapamti;

import domain.LSS;
import domain.ListaStudenata;
import domain.Student;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import repository.db.impl.RepositoryDBOpsta;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class ZapamtiListuStudenata extends OpstaSO {

    ListaStudenata listaStudenata = null;
    
    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof HashMap)) {
            throw new Exception("Preduslovi za kreiranje studenta nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        Map mapa = (HashMap) param;
        ListaStudenata listaS = (ListaStudenata) mapa.get("Lista");  
        List<Student> studenti = (List<Student>) mapa.get("Studenti");
        try {
            ((RepositoryDBOpsta) repository).add(listaS);
            for(Student s : studenti){
                s.setPolozio(true);
                ((RepositoryDBOpsta) repository).add(new LSS(s, listaS));
                ((RepositoryDBOpsta) repository).edit(s);
            }
            listaStudenata = listaS;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public ListaStudenata getListaStudenata() {
        return listaStudenata;
    }
}
