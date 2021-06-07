package rs.ac.bg.fon.ai.npserver.operation.impl.zapamti;

import rs.ac.bg.fon.ai.npcommon.domain.SE;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;

public class ZapamtiUcesce extends OpstaSO {
    
    SE ucesce;
    
    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof SE)) {
            throw new Exception("Preduslovi za kreiranje studenta nisu ispunjeni!");
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        ucesce = (SE) repository.add((SE)param);
    }

    public SE getUcesce() {
        return ucesce;
    }
}