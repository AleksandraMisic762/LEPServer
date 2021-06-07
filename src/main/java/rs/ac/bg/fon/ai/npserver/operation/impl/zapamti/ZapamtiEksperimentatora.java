package rs.ac.bg.fon.ai.npserver.operation.impl.zapamti;

import domain.Eksperimentator;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class ZapamtiEksperimentatora extends OpstaSO {

    Eksperimentator eksperimentator;

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof Eksperimentator)) {
            throw new Exception("Preduslovi za kreiranje eksperimentatora nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        eksperimentator = (Eksperimentator) repository.edit((Eksperimentator) param);
    }

    public Eksperimentator getEksperimentator() {
        return eksperimentator;
    }

}
