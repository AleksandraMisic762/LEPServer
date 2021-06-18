package rs.ac.bg.fon.ai.npserver.operation.impl.kreirajIzvestaj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;

import rs.ac.bg.fon.ai.npcommon.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ai.npcommon.domain.Predmet;
import rs.ac.bg.fon.ai.npcommon.domain.SE;
import rs.ac.bg.fon.ai.npcommon.domain.Student;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;
import rs.ac.bg.fon.ai.npserver.repository.db.impl.RepositoryDBOpsta;

public class IzvestajStudentiUslovom extends OpstaSO {

    List<Student> studenti;

    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null) {
            throw new Exception("Preduslovi za kreiranje izvestaja nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        studenti = new ArrayList<>();
        Map mapa = (HashMap) param;
        Predmet predmet = (Predmet) mapa.get("Predmet");

        List<OpstiDomenskiObjekat> odoL = ((RepositoryDBOpsta) repository).getWhere(new SE(),
                " HAVING e.predmet = " + predmet.getSifra()
                + " AND s.polozio = " + 0);
        for (OpstiDomenskiObjekat odo : odoL) {

            SE se = (SE) odo;

            if (se.getEksperiment().getBodovi() >= predmet.getUslov()) {
                se.getStudent().setPredmet(predmet);
                studenti.add(se.getStudent());
            }
        }
    }

    public List<Student> getStudenti() {
        return studenti;
    }
}
