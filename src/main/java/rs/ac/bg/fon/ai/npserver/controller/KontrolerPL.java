package rs.ac.bg.fon.ai.npserver.controller;

import rs.ac.bg.fon.ai.npcommon.domain.*;
import rs.ac.bg.fon.ai.npserver.operation.OpstaSO;
import rs.ac.bg.fon.ai.npserver.operation.impl.kreiraj.*;
import rs.ac.bg.fon.ai.npserver.operation.impl.kreirajIzvestaj.IzvestajOSprovedenimEksperimentima;
import rs.ac.bg.fon.ai.npserver.operation.impl.kreirajIzvestaj.IzvestajStudentiNaEksperimentu;
import rs.ac.bg.fon.ai.npserver.operation.impl.kreirajIzvestaj.IzvestajStudentiUslovom;
import rs.ac.bg.fon.ai.npserver.operation.impl.obrisi.*;
import rs.ac.bg.fon.ai.npserver.operation.impl.pretrazi.*;
import rs.ac.bg.fon.ai.npserver.operation.impl.ucitajSve.*;
import rs.ac.bg.fon.ai.npserver.operation.impl.zapamti.*;
import rs.ac.bg.fon.ai.npserver.repository.Repository;
import rs.ac.bg.fon.ai.npserver.repository.db.impl.RepositoryDBKorisnik;

import java.util.List;
import java.util.Map;


public class KontrolerPL {

    private static KontrolerPL instance;

    private final Repository korisnikRepository;

    private KontrolerPL() {
        korisnikRepository = new RepositoryDBKorisnik();
    }

    public static KontrolerPL getInstance() {
        if (instance == null) {
            instance = new KontrolerPL();
        }
        return instance;
    }

    public Korisnik login(String username, String password) throws Exception {
        List<Korisnik> users = ((RepositoryDBKorisnik) korisnikRepository).getAll(new Korisnik());
        for (Korisnik user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean ucitajListuEksperimenata(List<Eksperiment> listaEksperimenata) {
        try {
            OpstaSO operation = new UcitajListuEksperimenata();
            boolean signal = operation.izvrsiOperaciju(new Eksperiment());
            listaEksperimenata.addAll(((UcitajListuEksperimenata) operation).getEksperimenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean ucitajListuEksperimentator(List<Eksperimentator> listaEkperimentatora) {
        try {
            OpstaSO operation = new UcitajListuEksperimentatora();
            boolean signal = operation.izvrsiOperaciju(new Eksperimentator(0l));
            listaEkperimentatora.addAll(((UcitajListuEksperimentatora) operation).getEksperimentatori());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean ucitajListuPredmeta(List<Predmet> listaPredmeta) {
        try {
            OpstaSO operation = new UcitajListuPredmeta();
            boolean signal = operation.izvrsiOperaciju(new Predmet(0l));
            listaPredmeta.addAll(((UcitajListuPredmeta) operation).getPredmeti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean kreirajStudenta(Student student) {
        try {
            OpstaSO operation = new KreirajStudenta();
            boolean signal = operation.izvrsiOperaciju(student);
            Student res = ((KreirajStudenta) operation).getStudent();
            student.setSifra(res.getSifra());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean kreirajEksperiment(Eksperiment eksperiment) {
        try {
            OpstaSO operation = new KreirajEksperiment();
            boolean signal = operation.izvrsiOperaciju(eksperiment);
            Eksperiment res = ((KreirajEksperiment) operation).getEksperiment();
            eksperiment.setSifra(res.getSifra());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean kreirajEksperimentatora(Eksperimentator eksperimentator) {
        try {
            OpstaSO operation = new KreirajEksperimentatora();
            boolean signal = operation.izvrsiOperaciju(eksperimentator);
            Eksperimentator res = ((KreirajEksperimentatora) operation).getEksperimentator();
            eksperimentator.setSifra(res.getSifra());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean zapamtiStudenta(Student student) {
        try {
            OpstaSO operation = new ZapamtiStudenta();
            return operation.izvrsiOperaciju(student);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean zapamtiEksperiment(Eksperiment ekperiment) {
        try {
            OpstaSO operation = new ZapamtiEksperiment();
            return operation.izvrsiOperaciju(ekperiment);
        } catch (Exception ex) {
            ekperiment.setSifra(0l);
            return false;
        }
    }

    public boolean zapamtiEksperimentatora(Eksperimentator eksperimentator) {
        try {
            OpstaSO operation = new ZapamtiEksperimentatora();
            return operation.izvrsiOperaciju(eksperimentator);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean obrisiEksperiment(Eksperiment eksperiment) {
        try {
            OpstaSO operation = new ObrisiEkperiment();
            operation.izvrsiOperaciju(eksperiment);
            return ((ObrisiEkperiment) operation).getResult();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean pronadjiStudenta(Student student) {
        try {
            OpstaSO operation = new PretraziStudenta();
            boolean signal = operation.izvrsiOperaciju(student);
            Student res = ((PretraziStudenta) operation).getStudent();
            student.setSifra(res.getSifra());
            student.setBrojIndeksa(res.getBrojIndeksa());
            student.setIme(res.getIme());
            student.setPrezime(res.getPrezime());
            student.setPolozio(res.isPolozio());
            student.setPredmet(res.getPredmet());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean pronadjiEksperiment(Eksperiment eksperiment) {
        try {
            OpstaSO operation = new PretraziEksperiment();
            boolean signal = operation.izvrsiOperaciju(eksperiment);
            Eksperiment res = ((PretraziEksperiment) operation).getEksperiment();
            eksperiment.setSifra(res.getSifra());
            eksperiment.setNaziv(res.getNaziv());
            eksperiment.setDatumOdrzavanja(res.getDatumOdrzavanja());
            eksperiment.setBodovi(res.getBodovi());
            eksperiment.setEksperimenatator(res.getEksperimenatator());
            eksperiment.setRaspored(res.getRaspored());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean ucitajListuStudenataSaUslovom(ListaStudenata listaStudenata, List<Student> studenti) {
        try {
            OpstaSO operation = new UcitajListuStudenataUslov();
            boolean signal = operation.izvrsiOperaciju(listaStudenata);
            studenti.addAll(((UcitajListuStudenataUslov) operation).getStudenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean zapamtiListuStudenata(Map mapa) {
        try {
            OpstaSO operation = new ZapamtiListuStudenata();
            boolean signal = operation.izvrsiOperaciju(mapa);
            mapa.replace("Lista", ((ZapamtiListuStudenata) operation).getListaStudenata());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean zapamtiRasporedEksperimenata(RasporedEksperimenata raspored, List<Eksperiment> listaEksperimenata) {
        try {
            OpstaSO operation = new ZapamtiRasporedEksperimenata();
            boolean signal = operation.izvrsiOperaciju(raspored);
            if (((ZapamtiRasporedEksperimenata) operation).getRe() == null) {
                throw new Exception();
            }
            listaEksperimenata.addAll(((ZapamtiRasporedEksperimenata) operation).getEksperimenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean zapamtiUcesce(SE ucesce) {
        try {
            OpstaSO operation = new ZapamtiUcesce();
            return operation.izvrsiOperaciju(ucesce);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean ucescaNaEks(Map m) {
        try {
            OpstaSO operation = new UcescaNaEks();
            boolean signal = operation.izvrsiOperaciju((Eksperiment)m.get("E"));
            m.replace("LS", ((UcescaNaEks)operation).getStudenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean izvestajOStudentimaSaUslovom(Map mapa) {
    	try {
            OpstaSO operation = new IzvestajStudentiUslovom();
            boolean signal = operation.izvrsiOperaciju(mapa);
            mapa.replace("Studenti", ((IzvestajStudentiUslovom) operation).getStudenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean izvestajOSprovedenimEksperimentima(List<Eksperiment> lista) {
    	try {
            OpstaSO operation = new IzvestajOSprovedenimEksperimentima();
            boolean signal = operation.izvrsiOperaciju(null);
            lista.addAll(((IzvestajOSprovedenimEksperimentima) operation).getEksperimenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean izvestajOUcescuNaEksperimentu(Map mapa) {
    	try {
            OpstaSO operation = new IzvestajStudentiNaEksperimentu();
            boolean signal = operation.izvrsiOperaciju(mapa);
            mapa.replace("Studenti", ((IzvestajStudentiNaEksperimentu) operation).getStudenti());
            return signal;
        } catch (Exception ex) {
            return false;
        }
    }
    
    
}
