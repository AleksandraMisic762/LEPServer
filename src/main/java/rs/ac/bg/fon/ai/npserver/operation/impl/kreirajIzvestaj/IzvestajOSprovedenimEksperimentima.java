package rs.ac.bg.fon.ai.npserver.operation.impl.kreirajIzvestaj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.bg.fon.ai.npcommon.domain.Eksperiment;
import rs.ac.bg.fon.ai.npcommon.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;
import rs.ac.bg.fon.ai.npserver.repository.db.impl.RepositoryDBOpsta;

public class IzvestajOSprovedenimEksperimentima extends OpstaSO {
	List<Eksperiment> eksperimenti;
	

	@Override
	protected void proveriPreduslov(Object param) throws Exception {

	}

	@Override
	protected void izvrsi(Object param) throws Exception {
		eksperimenti = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		List<OpstiDomenskiObjekat> odoL = ((RepositoryDBOpsta) repository).getWhere(new Eksperiment(),
				"WHERE e1.datumOdrzavanja < \" " + df.format(new Date(System.currentTimeMillis())) + " \"");


		for (OpstiDomenskiObjekat odo : odoL) {

			Eksperiment e = (Eksperiment) odo;

			eksperimenti.add(e);
		}
	}

	public List<Eksperiment> getEksperimenti() {
		return eksperimenti;
	}
}
