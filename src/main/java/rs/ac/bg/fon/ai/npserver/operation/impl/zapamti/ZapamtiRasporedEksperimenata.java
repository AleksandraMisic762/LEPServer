package rs.ac.bg.fon.ai.npserver.operation.impl.zapamti;

import rs.ac.bg.fon.ai.npcommon.domain.Eksperiment;
import rs.ac.bg.fon.ai.npcommon.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ai.npcommon.domain.RasporedEksperimenata;
import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;
import rs.ac.bg.fon.ai.npserver.repository.db.impl.RepositoryDBOpsta;

public class ZapamtiRasporedEksperimenata  extends OpstaSO {
    
    RasporedEksperimenata re;
    List<Eksperiment> eksperiment = new ArrayList<>();

    @Override
    protected void proveriPreduslov(Object param) throws Exception {
        if (param == null || !(param instanceof RasporedEksperimenata)) {
            throw new Exception("Preduslovi za kreiranje eksperimenta nisu ispunjeni!");
        }
        RasporedEksperimenata re = (RasporedEksperimenata)param;
        
        if(re.getDatumDo().before(re.getDatumOd())){
            re = null;
            throw new Exception("Datumi nisu ispravno uneti!");
        }
        
        List<RasporedEksperimenata> sviRasporedi =  repository.getAll(re);
        
        for(RasporedEksperimenata raspored : sviRasporedi){
            if((raspored.getDatumDo().after(re.getDatumOd()) && raspored.getDatumDo().before(re.getDatumDo())) ||
                    (raspored.getDatumOd().after(re.getDatumOd()) && raspored.getDatumOd().before(re.getDatumDo())) ||
                    (raspored.getDatumDo().before(re.getDatumDo()) && raspored.getDatumOd().after(re.getDatumOd())) ||
                    (raspored.getDatumOd().before(re.getDatumOd()) && raspored.getDatumDo().after(re.getDatumDo()))){
                re = null;
                throw new Exception("Rasporedi se preklapaju!");
            }
        }
    }

    @Override
    protected void izvrsi(Object param) throws Exception {
        re = (RasporedEksperimenata) repository.add((RasporedEksperimenata)param);
        
        List<OpstiDomenskiObjekat> listE = ((RepositoryDBOpsta) repository).getWhere(new Eksperiment(), " WHERE e1.datumOdrzavanja >= '" + re.getDatumOd() + "' AND e1.datumOdrzavanja <= '" + re.getDatumDo() + "'");
        
        for(OpstiDomenskiObjekat e : listE){
            Eksperiment ek = (Eksperiment)e;
            ek.setRaspored(re);
            eksperiment.add(ek);
            repository.edit(ek);
        }
    }

    public List<Eksperiment> getEksperimenti() {
        return eksperiment;
    }

    public RasporedEksperimenata getRe() {
        return re;
    }
}