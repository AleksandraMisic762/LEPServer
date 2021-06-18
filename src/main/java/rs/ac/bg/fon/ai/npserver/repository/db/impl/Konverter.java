package rs.ac.bg.fon.ai.npserver.repository.db.impl;

import rs.ac.bg.fon.ai.npcommon.domain.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Konverter {

    public static OpstiDomenskiObjekat konvertuj(OpstiDomenskiObjekat entity, ResultSet rs) throws SQLException {

        if (entity instanceof Student) {
            Predmet p = new Predmet();

            p.setSifra(rs.getLong("s.predmet"));
            p.setNaziv(rs.getString("p.naziv"));
            p.setUslov(rs.getInt("p.uslov"));

            Student s = new Student();

            s.setSifra(rs.getLong("s.sifra"));
            s.setBrojIndeksa(rs.getString("s.brojIndeksa"));
            s.setIme(rs.getString("s.ime"));
            s.setPrezime(rs.getString("s.prezime"));
            s.setPolozio(rs.getInt("s.polozio") == 1);
            s.setPredmet(p);

            return s;
        } else if (entity instanceof Eksperiment) {
            Long sifraRasporeda = rs.getLong("e1.raspored");
            RasporedEksperimenata raspored = new RasporedEksperimenata(0l);

            if (sifraRasporeda != 0) {
                raspored.setSifra(sifraRasporeda);
                raspored.setDatumOd(rs.getDate("r.DatumOd"));
                raspored.setDatumDo(rs.getDate("r.DatumDo"));
            }

            Eksperimentator ekperimentator = new Eksperimentator();

            ekperimentator.setSifra(rs.getLong("e1.eksperimentator"));
            ekperimentator.setIme(rs.getString("e2.ime"));
            ekperimentator.setPrezime(rs.getString("e2.prezime"));

            Eksperiment e = new Eksperiment();

            e.setSifra(rs.getLong("e1.sifra"));
            e.setNaziv(rs.getString("e1.naziv"));
            e.setDatumOdrzavanja(rs.getDate("e1.datumOdrzavanja"));
            e.setBodovi(rs.getInt("e1.bodovi"));
            e.setEksperimenatator(ekperimentator);
            if (sifraRasporeda != 0) {
                e.setRaspored(raspored);
            }

            return e;
        } else if (entity instanceof Eksperimentator) {
            Eksperimentator e = new Eksperimentator(rs.getLong("e2.Sifra"));
            e.setIme(rs.getString("e2.ime"));
            e.setPrezime(rs.getString("e2.prezime"));
            return e;
        } else if (entity instanceof Predmet) {
            Predmet p = new Predmet(0l);

            p.setSifra(rs.getLong("sifra"));
            p.setNaziv(rs.getString("naziv"));
            p.setUslov(rs.getInt("uslov"));
            return p;
        } else if (entity instanceof SE) {

            Student s = new Student();

            s.setSifra(rs.getLong("s.sifra"));
            s.setBrojIndeksa(rs.getString("s.brojIndeksa"));
            s.setIme(rs.getString("s.ime"));
            s.setPrezime(rs.getString("s.prezime"));
            s.setPolozio(rs.getInt("s.polozio") == 1);
            s.setPredmet(new Predmet(rs.getLong("s.predmet")));

            Eksperiment e = new Eksperiment();

            e.setSifra(rs.getLong("e1.sifra"));
            e.setNaziv(rs.getString("e1.naziv"));
            e.setDatumOdrzavanja(rs.getDate("e1.datumOdrzavanja"));
            e.setBodovi(rs.getInt("uk_bodova"));
            e.setEksperimenatator(null);
            e.setRaspored(null);

            SE ucesce = new SE(s, e);

            return ucesce;
        } else if (entity instanceof RasporedEksperimenata) {
            RasporedEksperimenata rasporedEksperimenata = new RasporedEksperimenata(rs.getLong("r.sifra"));

            rasporedEksperimenata.setDatumOd(rs.getDate("r.datumOd"));
            rasporedEksperimenata.setDatumDo(rs.getDate("r.datumDo"));

            return rasporedEksperimenata;
        }

        return null;
    }
}
