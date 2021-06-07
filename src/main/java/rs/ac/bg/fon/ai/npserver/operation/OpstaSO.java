package rs.ac.bg.fon.ai.npserver.operation;

import rs.ac.bg.fon.ai.npserver.repository.Repository;
import rs.ac.bg.fon.ai.npserver.repository.db.DBRepository;
import rs.ac.bg.fon.ai.npserver.repository.db.impl.RepositoryDBOpsta;

public abstract class OpstaSO {

    protected final Repository repository;
    protected boolean signal = true;

    public OpstaSO() {
        this.repository = new RepositoryDBOpsta();
    }

    public final boolean izvrsiOperaciju(Object param) throws Exception {
        try {
            proveriPreduslov(param);
            zapocniTransakciju();
            izvrsi(param);
            potvrdiTransakciju();
        } catch (Exception ex) {
            ex.printStackTrace();
            signal = false;
            ponistiTransakciju();
        } finally {
            prekiniVezu();
        }
        return signal;
    }

    protected abstract void proveriPreduslov(Object param) throws Exception;

    private void zapocniTransakciju() throws Exception {
        ((DBRepository) repository).uspostaviKonekcijuSaBazom();
    }

    protected abstract void izvrsi(Object param) throws Exception;

    private void potvrdiTransakciju() throws Exception {
        ((DBRepository) repository).potvrdiTransakciju();
    }

    private void ponistiTransakciju() throws Exception {
        ((DBRepository) repository).ponistiTransakciju();
    }

    private void prekiniVezu() throws Exception {
        ((DBRepository) repository).raskiniKonekcijuSaBazom();
    }

}
