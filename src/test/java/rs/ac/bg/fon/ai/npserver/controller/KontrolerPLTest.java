package rs.ac.bg.fon.ai.npserver.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import rs.ac.bg.fon.ai.npcommon.domain.*;
import rs.ac.bg.fon.ai.npserver.repository.db.DBConnectionFactory;

class KontrolerPLTest {
	
	static Long timestamp = System.currentTimeMillis();
	private static Eksperimentator eksp1, eksp2;
	private static Eksperiment e1, e2, e3;
	private static Student s1, s2, s3;
	private static Predmet p1, p2;
	
	@BeforeAll
	static void setUp() {
		p1 = new Predmet(1l, "Pamcenje", 8);
		p2 = new Predmet(2l, "Ucenje", 9);
		
		DBConnectionFactory.getInstance().setScope("test");
		
		eksp1 = new Eksperimentator(1l, "Ime_Eksp1", "Prezime_Eksp1");
		KontrolerPL.getInstance().kreirajEksperimentatora(eksp1);
		KontrolerPL.getInstance().zapamtiEksperimentatora(eksp1);
		
		eksp2 = new Eksperimentator(1l, "Ime_Eksp2", "Prezime_Eksp2");
		KontrolerPL.getInstance().kreirajEksperimentatora(eksp2);
		KontrolerPL.getInstance().zapamtiEksperimentatora(eksp2);
		
		e1 = new Eksperiment(0l, "Eksp#101", new Date(timestamp + 15l * 86400000l), 16, eksp1, null);
		KontrolerPL.getInstance().kreirajEksperiment(e1);
		KontrolerPL.getInstance().zapamtiEksperiment(e1);
		
		e2 = new Eksperiment(0l, "Eksp#102", new Date(timestamp + 25l * 86400000l), 7, eksp2, null);
		KontrolerPL.getInstance().kreirajEksperiment(e2);
		KontrolerPL.getInstance().zapamtiEksperiment(e2);
		
		e3 = new Eksperiment(0l, "Eksp#103", new Date(timestamp + 35l * 86400000l), 8, eksp1, null);
		KontrolerPL.getInstance().kreirajEksperiment(e3);
		KontrolerPL.getInstance().zapamtiEksperiment(e3);
		
		s1 = new Student(0l, "2020/0001", "Ime1", "Prezime1", false, p1);
		KontrolerPL.getInstance().kreirajStudenta(s1);
		KontrolerPL.getInstance().zapamtiStudenta(s1);
		
		s2 = new Student(0l, "2020/0002", "Ime2", "Prezime2", true, p1);
		KontrolerPL.getInstance().kreirajStudenta(s2);
		KontrolerPL.getInstance().zapamtiStudenta(s2);
		
		s3 = new Student(0l, "2020/0003", "Ime3", "Prezime3", false, p2);
		KontrolerPL.getInstance().kreirajStudenta(s3);
		KontrolerPL.getInstance().zapamtiStudenta(s3);
		
		KontrolerPL.getInstance().zapamtiUcesce(new SE(s1, e1));
		KontrolerPL.getInstance().zapamtiUcesce(new SE(s2, e1));
		KontrolerPL.getInstance().zapamtiUcesce(new SE(s3, e1));
		
	}
	
	@AfterAll
	static void takeDown(){
		DBConnectionFactory.getInstance().setScope("production");
	}

	@ParameterizedTest
	@CsvSource({ "ksenijaM, ksenija123, true", "admin4332, admin123, false", "admin123, admin999, false",
			"admin654, admin999, false" })
	void testLogin(String username, String password, boolean equals) throws Exception {
		if (equals) {
			Korisnik res = KontrolerPL.getInstance().login(username, password);
			assertTrue(res.equals(new Korisnik(1l, username, password)) && res.getPassword().equals(password));
		} else
			assertNull(KontrolerPL.getInstance().login(username, password));
	}

	@Test
	void testUcitajListuEksperimenata() {
		List<Eksperiment> lista = new ArrayList<>();
		assertTrue(KontrolerPL.getInstance().ucitajListuEksperimenata(lista));
		assertNotEquals(0, lista.size());
	}

	@Test
	void testUcitajListuEksperimentator() {
		List<Eksperimentator> lista = new ArrayList<>();
		assertTrue(KontrolerPL.getInstance().ucitajListuEksperimentator(lista));
		assertNotEquals(0, lista.size());
	}

	@Test
	void testUcitajListuPredmeta() {
		List<Predmet> lista = new ArrayList<>();
		assertTrue(KontrolerPL.getInstance().ucitajListuPredmeta(lista));
		assertEquals(3, lista.size());
	}

	@Test
	void testKreirajStudenta() {
		Student s = new Student();
		assertTrue(KontrolerPL.getInstance().kreirajStudenta(s));
	}

	@Test
	void testKreirajEksperiment() {
		Eksperiment e = new Eksperiment();
		assertTrue(KontrolerPL.getInstance().kreirajEksperiment(e));
	}

	@Test
	void testKreirajEksperimentatora() {
		Eksperimentator e = new Eksperimentator();
		assertTrue(KontrolerPL.getInstance().kreirajEksperimentatora(e));
	}

	@Test
	void testZapamtiStudenta() {
		Student s = new Student();
		assertTrue(KontrolerPL.getInstance().kreirajStudenta(s));
		s.setBrojIndeksa("2020/0004");
		s.setIme("Iva");
		s.setPrezime("Saric");
		s.setPolozio(false);
		assertTrue(KontrolerPL.getInstance().zapamtiStudenta(s));
	}

	@Test
	void testZapamtiStudentaVecPostoji() {
		Student s = new Student();
		KontrolerPL.getInstance().kreirajStudenta(s);
		s.setBrojIndeksa(s1.getBrojIndeksa());
		s.setIme("Pera");
		s.setPrezime("Peric");
		s.setPolozio(false);
		assertFalse(KontrolerPL.getInstance().zapamtiStudenta(s));
	}

	@Test
	void testZapamtiEksperiment() {
		Eksperiment e = new Eksperiment();
		KontrolerPL.getInstance().kreirajEksperiment(e);
		e.setNaziv("Eks-ZapamtiEksperiment");
		e.setDatumOdrzavanja(new Date(timestamp + 3l * 86400000l));
		e.setBodovi(6);
		assertTrue(KontrolerPL.getInstance().zapamtiEksperiment(e));
	}
	
	@Test
	void testZapamtiEksperimentIstiDan() {
		Eksperiment e = new Eksperiment();
		KontrolerPL.getInstance().kreirajEksperiment(e);
		e.setNaziv("Eks#1");
		e.setDatumOdrzavanja(e2.getDatumOdrzavanja());
		e.setBodovi(6);
		assertTrue(KontrolerPL.getInstance().zapamtiEksperiment(e2));

	}

	@Test
	void testZapamtiEksperimentatora() {
		Eksperimentator e = new Eksperimentator();
		KontrolerPL.getInstance().kreirajEksperimentatora(e);
		e.setIme("Mira");
		e.setPrezime("Lazic");
		assertTrue(KontrolerPL.getInstance().zapamtiEksperimentatora(e));
	}

	@Test
	void testObrisiEksperiment() {
		Eksperiment e = new Eksperiment();
		assertTrue(KontrolerPL.getInstance().kreirajEksperiment(e));
		assertTrue(KontrolerPL.getInstance().obrisiEksperiment(e));
	}

	@Test
	void testObrisiEksperimentPostojiSE() {
		assertFalse(KontrolerPL.getInstance().obrisiEksperiment(e1));
	}

	@Test
	void testPronadjiStudenta() {
		Student pronadji = new Student(s2.getSifra());
		pronadji.setBrojIndeksa(s2.getBrojIndeksa());
		assertTrue(KontrolerPL.getInstance().pronadjiStudenta(pronadji));
		assertEquals(s2.getBrojIndeksa(), pronadji.getBrojIndeksa());
	}

	@Test
	void testPronadjiEksperiment() {
		Eksperiment pronadji = new Eksperiment(e1.getSifra());
		assertTrue(KontrolerPL.getInstance().pronadjiEksperiment(pronadji));
	}

	@Test
	void testUcitajListuStudenataSaUslovom() {		
		Eksperiment e = new Eksperiment(0l, "Eks#11", new Date(System.currentTimeMillis() + 14l*86400000l), 10, null, null);
		KontrolerPL.getInstance().kreirajEksperiment(e);
		assertTrue(KontrolerPL.getInstance().zapamtiEksperiment(e));
		
		assertTrue(KontrolerPL.getInstance().zapamtiUcesce(new SE(s1,e)));
		assertTrue(KontrolerPL.getInstance().zapamtiUcesce(new SE(s3,e)));
		
		List<Student> lista = new ArrayList<>();
		
		ListaStudenata ls = new ListaStudenata(0l, "Jun", p1);
		
		assertTrue(KontrolerPL.getInstance().ucitajListuStudenataSaUslovom(ls, lista));
		assertEquals(1, lista.size());
		assertEquals(s1.getBrojIndeksa(), lista.get(0).getBrojIndeksa());
		assertEquals(s1.getSifra(), lista.get(0).getSifra());
	}

	@Test
	void testZapamtiListuStudenata() {
		ListaStudenata ls = new ListaStudenata(1l, "Jun", new Predmet(2l, "Ucenje", 9));
		List<Student> studenti = new ArrayList<>();
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("Lista", ls);
		mapa.put("Studenti", studenti);
		assertTrue(KontrolerPL.getInstance().zapamtiListuStudenata(mapa));
	}

	@Test
	void testZapamtiRasporedEksperimenata() {
		RasporedEksperimenata r = new RasporedEksperimenata(0l, 
				new Date(timestamp + 20l * 86400000l), 
				new Date(timestamp + 30l * 86400000l));
		
		List<Eksperiment> lista = new ArrayList<>();
		
		assertTrue(KontrolerPL.getInstance().zapamtiRasporedEksperimenata(r, lista));
		
		assertEquals(1, lista.size());
		assertNotNull(lista.get(0).getRaspored());
	}
	
	@Test
	void testZapamtiRasporedEksperimenataPreklapanje() {
		RasporedEksperimenata r = new RasporedEksperimenata(0l, 
				new Date(timestamp + 20l * 86400000l), 
				new Date(timestamp + 25l * 86400000l));
		
		List<Eksperiment> lista = new ArrayList<>();
		
		assertFalse(KontrolerPL.getInstance().zapamtiRasporedEksperimenata(r, lista));
	}

	@Test
	void testZapamtiUcesce() {
		assertTrue(KontrolerPL.getInstance().zapamtiUcesce(new SE(s2, e2)));
	}

	@Test
	void testUcescaNaEks() {
		List<Student> lista = new ArrayList<>();
		
		Map<String, Object> mapa= new HashMap<>();
		
		mapa.put("E", e1);
		mapa.put("LS", lista);
		
		assertTrue(KontrolerPL.getInstance().ucescaNaEks(mapa));
		assertNotEquals(0, ((List<Student>)mapa.get("LS")).size());
	}
	

	@Test
	void testIzvestajOStudentimaSaUslovom() {
		List<Student> lista = new ArrayList<>();

		Map<String, Object> mapa = new HashMap<>();
		mapa.put("Studenti", lista);
		mapa.put("Predmet", p1);
		
		assertTrue(KontrolerPL.getInstance().izvestajOStudentimaSaUslovom(mapa));
		assertNotEquals(0, ((List<Student>)mapa.get("Studenti")).size());
		assertEquals(s1.getBrojIndeksa(), ((List<Student>)mapa.get("Studenti")).get(0).getBrojIndeksa());
		
	}

	@Test
	void testIzvestajOSprovedenimEksperimentima() {		
		Eksperiment e1 = new Eksperiment(0l, "Eksp#301", new Date(System.currentTimeMillis() - 15l * 86400000l), 13, eksp2, null);
		KontrolerPL.getInstance().kreirajEksperiment(e1);
		assertTrue(KontrolerPL.getInstance().zapamtiEksperiment(e1));
		
		List<Eksperiment> lista = new ArrayList<>();
		
		assertTrue(KontrolerPL.getInstance().izvestajOSprovedenimEksperimentima(lista));
		assertNotEquals(0, lista.size());
	}

	@Test
	void testIzvestajOUcescuNaEksperimentu() {
		Map<String,Object> mapa = new HashMap<>();
		List<Student> lista = new ArrayList<>();
		
		mapa.put("Eksperiment", e1);
		mapa.put("Studenti", lista);
		
		assertTrue(KontrolerPL.getInstance().izvestajOUcescuNaEksperimentu(mapa));
		assertNotEquals(0, ((List<Student>)mapa.get("Studenti")).size());
	}

}
