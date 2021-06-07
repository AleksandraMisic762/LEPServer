package repository.db.impl;

import domain.Korisnik;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import repository.db.DBConnectionFactory;
import repository.db.DBRepository;

public class RepositoryDBKorisnik implements DBRepository<Korisnik> {



    @Override
    public Korisnik add(Korisnik korisnik) throws Exception {
       try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO Korisnik")
                    .append(" (").append(korisnik.vratiImenaKolonaBezSife()).append(")")
                    .append(" VALUES (").append(korisnik.vratiVrednostiBezSifre()).append(")");
            String query = korisnik.vratiUpitZaInsert();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rsKey = statement.getGeneratedKeys();
            if (rsKey.next()) {
                Long id = rsKey.getLong(1);
                korisnik.setSifra(id);
            }
            statement.close();
            rsKey.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return korisnik;
    }

    @Override
    public Korisnik edit(Korisnik korisnik) throws Exception {
       try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(korisnik.vratiNazivTabele()).append(" SET ");

            sb.append(korisnik.vratiVrednostiZaUpdate());

            sb.append(" WHERE sifra = ").append(korisnik.getSifra());

            String query = sb.toString();
            System.out.println(query);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.executeUpdate();
            statement.close();
            return korisnik;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void delete(Korisnik korisnik) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(korisnik.vratiNazivTabele().split(" ")[0]).append(" WHERE sifra = ").append(korisnik.getSifra());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Korisnik get(Korisnik korisnik) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(korisnik.vratiSvaImenaKolona()).append(" FROM ")
                    .append(korisnik.vratiNazivTabele());
            if (korisnik.vratiJoinKlauzulu() != null) {
                sb.append(korisnik.vratiJoinKlauzulu());
            }
            sb.append(korisnik.vratiUslovZaSelect());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Korisnik result = null;
            if (rs.next()) {
                result = new Korisnik(rs.getLong("sifra"), rs.getString("username"), rs.getString("password"));
            }
            statement.close();
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<Korisnik> getAll(Korisnik korisnik) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ").append(korisnik.vratiSvaImenaKolona()).append(" FROM ")
                    .append(korisnik.vratiNazivTabele());
            if (korisnik.vratiJoinKlauzulu() != null) {
                sb.append(korisnik.vratiJoinKlauzulu());
            }
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Korisnik> listaKorisnik = new ArrayList<>();
            while (rs.next()) {
                listaKorisnik.add(new Korisnik(rs.getLong("sifra"), rs.getString("username"), rs.getString("password")));
            }
            statement.close();
            return listaKorisnik;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<Korisnik> getWhere(Korisnik korisnik, String where) throws Exception {
         try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ")
                    .append(korisnik.vratiSvaImenaKolona())
                    .append(" FROM ").append(korisnik.vratiNazivTabele());
            if (korisnik.vratiJoinKlauzulu() != null) {
                sb.append(korisnik.vratiJoinKlauzulu());
            }
            sb.append(where);
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Korisnik> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Korisnik(rs.getLong("sifra"), rs.getString("username"), rs.getString("password")));
            }
            statement.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
