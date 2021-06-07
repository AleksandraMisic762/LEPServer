package rs.ac.bg.fon.ai.npserver.operation.impl.ucitajSve;

import domain.ListaStudenata;
import domain.OpstiDomenskiObjekat;
import domain.SE;
import domain.Student;
import java.util.ArrayList;
import java.util.List;

import repository.db.impl.RepositoryDBOpsta;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class UcitajListuStudenataUslov extends OpstaSO {

    
    List<Student> studenti;

    public UcitajListuStudenataUslov() {
        studenti = new ArrayList<>();
    }

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof ListaStudenata)) {
            throw new Exception("Preduslovi za kreiranje studenta nisu ispunjeni!");
        }

    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        ListaStudenata ls = (ListaStudenata) param;

        List<OpstiDomenskiObjekat> odoL = ((RepositoryDBOpsta) repository).getWhere(new SE(),
                " HAVING s.predmet = " + ((ListaStudenata) param).getPredmet().getSifra()
                + " AND s.polozio = " + 0);
        for(OpstiDomenskiObjekat odo : odoL){
            
            SE se = (SE)odo;
                     
            if(se.getEksperiment().getBodovi() >= ls.getPredmet().getUslov()){
                studenti.add(se.getStudent());
            }
        }
    }

    public List<Student> getStudenti() {
        return studenti;
    }
}
