package rs.ac.bg.fon.ai.npserver.operation.impl.obrisi;

import domain.Eksperiment;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class ObrisiEkperiment extends OpstaSO {
    
    boolean result = false;
    
    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof Eksperiment)) {
            throw new Exception("Preduslovi za brisanje eksperimenta nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        repository.delete((Eksperiment)param);
        result = true;
    }

    public boolean getResult() {
        return result;
    }
}
