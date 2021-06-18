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

/**
 * Klasa koja sadrži poslovni logiku i kontroliše pozive sistemskih operacija.
 * Sadrži refencu na klasu preko koje se komunicira sa skladištem podataka.
 * 
 * <b>KontrolerPL</b> je Singleton.
 */
public class KontrolerPL {

	/**
	 * Jedinstvena instanca klase <b>KontrolerPL</b>.
	 */
	private static KontrolerPL instance;

	/**
	 * Referenca na klasu tipa <b>Repository</b> preko koje se komunicira sa
	 * skladištem podataka.
	 */
	private final Repository korisnikRepository;

	/**
	 * Privatni konstruktor klase <b>KontrolerPL</b>, bez parametara.
	 */
	private KontrolerPL() {
		korisnikRepository = new RepositoryDBKorisnik();
	}

	/**
	 * Vraća jedinstvenu instancu klase <b>KontrolerPL</b>.
	 * 
	 * @return Jedinstvena instanca klase <b>KontrolerPL</b>.
	 */
	public static KontrolerPL getInstance() {
		if (instance == null) {
			instance = new KontrolerPL();
		}
		return instance;
	}

	/**
	 * Preko korisničkog imena i lozinke koji su prosleđeni pronalazi
	 * <b>Korisnika</b> i proverava da li je ispravna lozinka koja je prosleđena da
	 * bi se korisniku odobrio pristup sistemu.
	 * 
	 * @param username
	 *            Korisničko ime preko koga se pronalazi korisnik u bazi podatak,
	 *            tipa <b>String</b>.
	 * @param password
	 *            Lozinka za pristupanje korisnika aplikaciji, koja se poredi sa
	 *            lozinkom koja je u sistemu, kako bi se ovlastio pristup, tipa
	 *            <b>String</b>.
	 * @return <b>Korisnik</b> koji postoji u bazi i za koga je poslata ispravna
	 *         lozinka ili <b>null</b> ako korisnik ne postoji u bazi ili je lozinka
	 *         neispravna.
	 * 
	 * @throws java.lang.Exception
	 *             Ako je došlo do greške prilikom povezivanja na bazu ili izvršenja
	 *             operacije.
	 */
	public Korisnik login(String username, String password) throws Exception {
		List<Korisnik> users = ((RepositoryDBKorisnik) korisnikRepository).getAll(new Korisnik());
		for (Korisnik user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * Poziva sistemsku operaciju <b>UcitajListuEksperimenata</b> i tako učitava sve
	 * eksperimente iz skladišta podataka.
	 * 
	 * @param listaEksperimenata
	 *            Kolekcija tipa <b>java.util.List</b> u koju se dodaju eksperimenti
	 *            koje vraća sistemska operacija <b>UcitajListuEksperimenata</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>UcitajListuEksperimentatora</b> i tako učitava
	 * sve eksperimentatore iz skladišta podataka.
	 * 
	 * @param listaEkperimentatora
	 *            Kolekcija tipa <b>java.util.List</b> u koju se dodaju
	 *            eksperimentatori koje vraća sistemska operacija
	 *            <b>UcitajListuEksperimentatora</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>UcitajListuPredmeta</b> i tako učitava sve
	 * predmete iz skladišta podataka.
	 * 
	 * @param listaPredmeta
	 *            Kolekcija tipa <b>java.util.List</b> u koju se dodaju predmeti
	 *            koje vraća sistemska operacija <b>UcitajListuPredmeta</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>KreirajStudenta</b> i kreira novi rekord za
	 * studenta u skladištu podataka.
	 * 
	 * @param student
	 *            Objekat klase <b>Student</b> preko koga se vraća povratna vrednost
	 *            sistemske operacije <b>KreirajStudenta</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         student je uspešno kreiran ili <b>false</b> ako je došlo do izuzetka
	 *         i student nije uspešno kreiran.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>KreirajEksperiment</b> i kreira novi rekord za
	 * eksperiment u skladištu podataka.
	 * 
	 * @param eksperiment
	 *            Objekat klase <b>Eksperiment</b> preko koga se vraća povratna
	 *            vrednost sistemske operacije <b>KreirajEksperiment</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         eksperiment je uspešno kreiran ili <b>false</b> ako je došlo do
	 *         izuzetka i eksperiment nije uspešno kreiran.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>KreirajEksperimentatora</b> i kreira novi
	 * rekord za eksperimentatora u skladištu podataka.
	 * 
	 * @param eksperimentator
	 *            Objekat klase <b>Eksperimentator</b> preko koga se vraća povratna
	 *            vrednost sistemske operacije <b>KreirajEksperimentatora</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         eksperimentator je uspešno kreiran ili <b>false</b> ako je došlo do
	 *         izuzetka i eksperimentator nije uspešno kreiran.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>ZapamtiStudenta</b> i ažurira rekord za
	 * studenta za koga je kriterijum pretrage prosleđen kroz argument.
	 * 
	 * @param student
	 *            Objekat klase <b>Student</b> preko koga se prosleđuje vrednost
	 *            preko koje se pronalazi rekord studenta koji se ažurira i vraća
	 *            povratna vrednost sistemske operacije <b>ZapamtiStudenta</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         student je uspešno ažuriran ili <b>false</b> ako je došlo do izuzetka
	 *         ili neke od vrednosti nisu mogle da se ažuriraju na prosleđene
	 *         vrednosti.
	 */
	public boolean zapamtiStudenta(Student student) {
		try {
			OpstaSO operation = new ZapamtiStudenta();
			return operation.izvrsiOperaciju(student);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Poziva sistemsku operaciju <b>ZapamtiEksperiment</b> i ažurira rekord za
	 * ekperiment za koga je kriterijum pretrage prosleđen kroz argument.
	 * 
	 * @param ekperiment
	 *            Objekat klase <b>Eksperiment</b> preko koga se prosleđuje vrednost
	 *            preko koje se pronalazi rekord studenta koji se ažurira i vraća
	 *            povratna vrednost sistemske operacije <b>ZapamtiEksperiment</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetakai
	 *         eksperiment je uspešno ažuriran ili <b>false</b> ako je došlo do
	 *         izuzetka ili eksperiment ne može da se ažurira na neku od ili sve
	 *         prosleđene vrednosti.
	 */
	public boolean zapamtiEksperiment(Eksperiment ekperiment) {
		try {
			OpstaSO operation = new ZapamtiEksperiment();
			return operation.izvrsiOperaciju(ekperiment);
		} catch (Exception ex) {
			ekperiment.setSifra(0l);
			return false;
		}
	}

	/**
	 * Poziva sistemsku operaciju <b>ZapamtiEksperimentatora</b> i ažurira rekord za
	 * eksperimentatora za koga je kriterijum pretrage prosleđen kroz argument.
	 * 
	 * @param eksperimentator
	 *            Objekat klase <b>Eksperimentator</b> preko koga se prosleđuje
	 *            vrednost preko koje se pronalazi rekord studenta koji se ažurira i
	 *            vraća povratna vrednost sistemske operacije
	 *            <b>ZapamtiEksperimentatora</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         eksperimentator je uspešno ažuriran ili <b>false</b> ako je došlo do
	 *         izuzetka ili eksperimentator ne može da se ažurira.
	 */
	public boolean zapamtiEksperimentatora(Eksperimentator eksperimentator) {
		try {
			OpstaSO operation = new ZapamtiEksperimentatora();
			return operation.izvrsiOperaciju(eksperimentator);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Poziva sistemsku operaciju <b>ObrisiEkperiment</b> i briše eksperiment iz
	 * skladišta podatka. Ako za eksperiment postoje uneta učešća, nije moguće
	 * obrisati eksperiment.
	 * 
	 * @param eksperiment
	 *            Objekat klase <b>Eksperiment</b> preko koga se prosleđuje vrednost
	 *            preko koje se pronalazi rekord studenta koji se ažurira.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         eksperiment je uspešno obrisan ili <b>false</b> ako je došlo do
	 *         izuzetka ili eksperiment ne može da se obriše zbog veza sa drugim
	 *         rekordima u skladištu podataka.
	 */
	public boolean obrisiEksperiment(Eksperiment eksperiment) {
		try {
			OpstaSO operation = new ObrisiEkperiment();
			operation.izvrsiOperaciju(eksperiment);
			return ((ObrisiEkperiment) operation).getResult();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Poziva sistemsku operaciju <b>PretraziStudenta</b> i vraća ili studenta sa
	 * brojem indeksa koji je prosleđen preko argumenta ili <b>null</b> ako student
	 * ne postoji u sistemu.
	 * 
	 * @param student
	 *            Objekat klase <b>Student</b> preko koga se prosleđuje vrednost
	 *            broja indeksa studenta i vraća povratna vrednost sistemske
	 *            operacije <b>PretraziStudenta</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>PretraziEksperiment</b> i vraća ili eksperiment
	 * sa šifrom prosleđenom preko argumenta ili <b>null</b> ako eksperiment ne
	 * postoji u sistemu.
	 * 
	 * @param eksperiment
	 *            Objekat klase <b>Eksperiment</b> preko koga se prosleđuje šifra
	 *            eksperimenta koji se traži i vraća povratna vrednost sistemske
	 *            operacije <b>PretraziEksperiment</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>UcitajListuStudenataUslov</b> kojom pronalazi
	 * sve studente koji su ostvatrili uslov da polazu ispit datom u roku i za
	 * predmet koji je prosleđen.
	 * 
	 * @param listaStudenata
	 *            Objekat klase <b>ListaStudenata</b> koji sadrži podatke o roku i
	 *            predmetu na koji se lista odnosi.
	 * @param studenti
	 *            Parametar preko koga se vraćaju svi studenti koji su ostvatrili
	 *            uslov da polazu ispit u roku i za predmet koji je prosleđen, tipa
	 *            <b>java.util.List</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         uspešno su pronađeni svi studenti ili <b>false</b> ako je došlo do
	 *         izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>ZapamtiListuStudenata</b> koja čuva objekat
	 * klase <b>ListaStudenata</b> u skladištu podatka i pripadnost svih studenata,
	 * kao objekat klase <b>LSS</b>, listi za dati rok i predmet.
	 * 
	 * @param mapa
	 *            Parametar preko koga se prosleđuju lista koja se čuva, kao
	 *            parametar mape "Lista", i svi studenti koji su ostvarili uslov za
	 *            polaganje ispita, kao parametar mape "Studenti", kao objekat tipa
	 *            <b>java.util.Map</b>.
	 * 
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         uspešno je sačuvana lista i pripadnost svih studenata toj listi ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>ZapamtiRasporedEksperimenata</b> koja čuva
	 * objekat klase <b>RasporedEksperimenata</b> u skladištu podatka i ažurira sve
	 * eksperimente njihovom pripadnošću novounetom rasporedu.
	 * 
	 * @param raspored
	 *            Raspored eksperimenata koji se čuva u skladištu podatka, objekat
	 *            klase <b>RasporedEksperimenata</b>.
	 * @param listaEksperimenata
	 *            Parametar preko koga se vraćaju svi eksperimenti koji su u datom
	 *            rasporedu, tipa <b>java.util.List</b>
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         uspešno je sačuvan raspored i ažurirani svih eksepimeti tom rasporedu
	 *         ili <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>ZapamtiUcesce</b> koja čuva učešće studenta u
	 * eksperimentu, kao objekat klase <b>SE</b>, u skladištu podatka.
	 * 
	 * @param ucesce
	 *            Parametar klase <b>SE</b> koji predstavlja učešće studenta u nekom
	 *            eksperimentu.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka i
	 *         uspešno je sačuvano je učešće ili <b>false</b> ako je došlo do
	 *         izuzetka.
	 */
	public boolean zapamtiUcesce(SE ucesce) {
		try {
			OpstaSO operation = new ZapamtiUcesce();
			return operation.izvrsiOperaciju(ucesce);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Poziva sistemsku operaciju <b>UcescaNaEks</b> koja vraća sve studente koji su
	 * učestvovali na ekseprimentu koji se prosleđuje metodi.
	 * 
	 * @param m
	 *            Parametar preko koga se vraća lista svih studenata koji su
	 *            učestvovali u eksperimentu, kao parametar mape "LS", i eksperiment
	 *            za koji se traže učešća, kao parametar mape "E", kao objekat tipa
	 *            <b>java.util.Map</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
	public boolean ucescaNaEks(Map m) {
		try {
			OpstaSO operation = new UcescaNaEks();
			boolean signal = operation.izvrsiOperaciju((Eksperiment) m.get("E"));
			m.replace("LS", ((UcescaNaEks) operation).getStudenti());
			return signal;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Poziva sistemsku operaciju <b>IzvestajStudentiUslovom</b> koja vraća sve
	 * studente koji su ostvarili uslov da polažu ispit na predmetu koji se
	 * prosleđuje metodi kroz mapu.
	 * 
	 * @param mapa
	 *            Parametar koji sadrži predmet za koji se traži lista, kao
	 *            parametar mape "Predmet", i lista, tipa <b>java.util.List</b>,
	 *            kroz koju se vraćaju svi studenti dobijeni iz skladišta podataka,
	 *            kao parametar mape "Studenti". Objekat tipa <b>java.util.Map</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>IzvestajOSprovedenimEksperimentima</b> koja
	 * vraća sve eksperimente koji su sprovedeni u laboratoriji.
	 * 
	 * @param lista
	 *            Lista kroz koju se vraćaju svi sprovedeni eksperimenti, tipa
	 *            <b>java.util.List</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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

	/**
	 * Poziva sistemsku operaciju <b>IzvestajStudentiNaEksperimentu</b> koja vraća
	 * sve studente koji su učestvovali u eksperimentu prosleđenom metodi kroz mapu.
	 * 
	 * @param mapa
	 *            Parametar koji sadrži eksperiment za koji se traži lista učesnika,
	 *            kao parametar mape "Eksperiment", i lista, tipa
	 *            <b>java.util.List</b>, kroz koju se vraćaju svi studenti dobijeni
	 *            iz skladišta podataka, kao parametar mape "Studenti". Objekat tipa
	 *            <b>java.util.Map</b>.
	 * @return <b>true</b> ako je operacija izvršena bez nastanka izuzetaka ili
	 *         <b>false</b> ako je došlo do izuzetka.
	 */
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
