package rs.ac.bg.fon.ai.npserver.operation.impl.ucitajSve;

import domain.Predmet;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

import java.util.List;

public class UcitajListuPredmeta extends OpstaSO{
    private List<Predmet> predmet;

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        predmet = repository.getAll((Predmet) param);
    }

    public List<Predmet> getPredmeti() {
        return predmet;
    }

}
