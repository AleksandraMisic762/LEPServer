package rs.ac.bg.fon.ai.npserver.operation.impl.kreirajIzvestaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import rs.ac.bg.fon.ai.npcommon.domain.Eksperiment;
import rs.ac.bg.fon.ai.npcommon.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ai.npcommon.domain.SE;
import rs.ac.bg.fon.ai.npcommon.domain.Student;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;
import rs.ac.bg.fon.ai.npserver.repository.db.impl.RepositoryDBOpsta;

public class IzvestajStudentiNaEksperimentu extends OpstaSO {

	List<Student> studenti;

	@Override
	protected void proveriPreduslov(Object param) throws Exception {
		if (param == null || !(param instanceof Map)) {
			throw new Exception("Preduslovi za kreiranje studenta nisu ispunjeni!");
		}

	}

	@Override
	protected void izvrsi(Object param) throws Exception {
		studenti = new ArrayList<>();
		Map mapa = (HashMap) param;

		List<OpstiDomenskiObjekat> odoL = ((RepositoryDBOpsta) repository).getAll(new SE());
		for (OpstiDomenskiObjekat odo : odoL) {

			SE se = (SE) odo;

			if (Objects.equals(se.getEksperiment().getSifra(),
					((Eksperiment) ((HashMap) param).get("Eksperiment")).getSifra())) {
				
				studenti.add(se.getStudent());
			}

		}
		
	}

	public List<Student> getStudenti() {
		return studenti;
	}
}
