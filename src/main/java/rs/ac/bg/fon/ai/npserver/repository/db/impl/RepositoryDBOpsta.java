package rs.ac.bg.fon.ai.npserver.repository.db.impl;

import rs.ac.bg.fon.ai.npcommon.domain.Eksperiment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.ai.npcommon.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ai.npcommon.domain.Predmet;
import rs.ac.bg.fon.ai.npcommon.domain.SE;
import rs.ac.bg.fon.ai.npcommon.domain.Student;
import rs.ac.bg.fon.ai.npserver.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ai.npserver.repository.db.DBRepository;

import java.sql.PreparedStatement;
import java.util.ArrayList;

public class RepositoryDBOpsta implements DBRepository<OpstiDomenskiObjekat> {

    @Override
    public OpstiDomenskiObjekat add(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(entity.vratiNazivTabele())
                    .append(" (").append(entity.vratiImenaKolonaBezSife()).append(")")
                    .append(" VALUES (").append(entity.vratiVrednostiBezSifre()).append(")");
            String query = entity.vratiUpitZaInsert();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = statement.getGeneratedKeys();
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                entity.setSifra(id);
            }
            statement.close();
            rsKey.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return entity;
    }

    @Override
    public OpstiDomenskiObjekat edit(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(entity.vratiNazivTabele()).append(" SET ");
            sb.append(entity.vratiVrednostiZaUpdate());
            sb.append(" WHERE sifra = ").append(entity.getSifra());
            String query = sb.toString();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
            return entity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void delete(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ")
                    .append(entity.vratiNazivTabele().split(" ")[0]).append(" WHERE sifra = ").append(entity.getSifra());

            String query = sb.toString();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public OpstiDomenskiObjekat get(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(entity.vratiSvaImenaKolona()).append(" FROM ").append(entity.vratiNazivTabele());
            if (entity.vratiJoinKlauzulu() != null) {
                sb.append(entity.vratiJoinKlauzulu());
            }
            sb.append(entity.vratiUslovZaSelect());
            String query = sb.toString();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            OpstiDomenskiObjekat result = null;
            if (rs.next()) {
                result = Konverter.konvertuj(entity, rs);
            }
            statement.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> getAll(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(entity.vratiSvaImenaKolona()).append(" FROM ").append(entity.vratiNazivTabele());
            if (entity.vratiJoinKlauzulu() != null) {

                sb.append(entity.vratiJoinKlauzulu());

            }
            String query = sb.toString();
            if (entity instanceof SE) {
                query = "SELECT s.sifra, s.brojIndeksa, s.ime, s.prezime, s.polozio, s.predmet, e1.sifra, e1.naziv, e1.datumOdrzavanja FROM SE join student s on (s.sifra = SE.student) JOIN eksperiment e1 on (e1.sifra = eksperiment)";
            }
            
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<OpstiDomenskiObjekat> result = new ArrayList<>();
            while (rs.next()) {
                if (entity instanceof SE) {
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
                    e.setEksperimenatator(null);
                    e.setRaspored(null);

                    SE ucesce = new SE(s, e);
                    
                     result.add(ucesce);
                } else {
                    result.add(Konverter.konvertuj(entity, rs));
                }
            }
            statement.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<OpstiDomenskiObjekat> getWhere(OpstiDomenskiObjekat entity, String where) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(entity.vratiSvaImenaKolona()).append(" FROM ").append(entity.vratiNazivTabele());
            if (entity.vratiJoinKlauzulu() != null) {
                sb.append(entity.vratiJoinKlauzulu());
            }
            sb.append(where);
            String query = sb.toString();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<OpstiDomenskiObjekat> result = new ArrayList<>();
            while (rs.next()) {
                result.add(Konverter.konvertuj(entity, rs));
            }
            statement.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
