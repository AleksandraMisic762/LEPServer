package rs.ac.bg.fon.ai.npserver.operation.impl.ucitajSve;

import domain.Eksperiment;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

import java.util.List;

public class UcitajListuEksperimenata extends OpstaSO {

    private List<Eksperiment> eksperiment;

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        eksperiment = repository.getAll((Eksperiment) param);
    }

    public List<Eksperiment> getEksperimenti() {
        return eksperiment;
    }
}
