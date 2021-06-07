package rs.ac.bg.fon.ai.npserver.operation.impl.ucitajSve;

import domain.Eksperiment;
import domain.ListaStudenata;
import domain.OpstiDomenskiObjekat;
import domain.SE;
import domain.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import repository.db.impl.RepositoryDBOpsta;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class UcescaNaEks extends OpstaSO {

    
    List<Student> studenti;

    public UcescaNaEks() {
        studenti = new ArrayList<>();
    }

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof Eksperiment)) {
            throw new Exception("Preduslovi za kreiranje studenta nisu ispunjeni!");
        }

    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        List<SE> ucesca = new ArrayList<>();

        List<OpstiDomenskiObjekat> odoL = ((RepositoryDBOpsta) repository).getAll(new SE());
        for(OpstiDomenskiObjekat odo : odoL){
            
            SE se = (SE)odo;
                     
            if(Objects.equals(se.getEksperiment().getSifra(), ((Eksperiment)param).getSifra())){
                studenti.add(se.getStudent());
            }
                
            
        }
    }

    public List<Student> getStudenti() {
        return studenti;
    }
}
