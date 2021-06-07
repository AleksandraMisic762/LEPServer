package rs.ac.bg.fon.ai.npserver.operation.impl.ucitajSve;

import java.util.List;
import domain.Eksperimentator;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class UcitajListuEksperimentatora extends OpstaSO {

    private List<Eksperimentator> eksperimentatori;

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        eksperimentatori = repository.getAll((Eksperimentator) param);
    }

    public List<Eksperimentator> getEksperimentatori() {
        return eksperimentatori;
    }
}
