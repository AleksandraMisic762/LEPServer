package rs.ac.bg.fon.ai.npserver.operation.impl.kreiraj;

import rs.ac.bg.fon.ai.npcommon.domain.Eksperiment;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class KreirajEksperiment extends OpstaSO {
    
    Eksperiment eksperiment;

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof Eksperiment)) {
            throw new Exception("Preduslovi za kreiranje eksperimenta nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        eksperiment = (Eksperiment) repository.add((Eksperiment)param);
    }

    public Eksperiment getEksperiment() {
        return eksperiment;
    }

}
