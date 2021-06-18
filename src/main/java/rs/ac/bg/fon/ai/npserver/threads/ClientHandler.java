package rs.ac.bg.fon.ai.npserver.threads;

import java.net.Socket;
import rs.ac.bg.fon.ai.npcommon.communication.*;
import rs.ac.bg.fon.ai.npserver.controller.KontrolerPL;
import rs.ac.bg.fon.ai.npcommon.domain.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rs.ac.bg.fon.ai.npserver.server.Server;

public class ClientHandler extends Thread {

    Server server;
    Socket socket;
    Sender sender;
    Receiver receiver;

    Korisnik korisnik;
    Date vremePridruzivanja;

    public ClientHandler(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        vremePridruzivanja = new Date(System.currentTimeMillis());
        try {
            while (true) {

                Request request = (Request) receiver.receive();
                Response response = new Response();
                switch (request.getOperation()) {
                    case LOGIN:
                        Korisnik user = (Korisnik) request.getArgument();
                        korisnik = user;
                        server.loggedIn(this);
                        response.setResult(KontrolerPL.getInstance().login(user.getUsername(), user.getPassword()));
                        break;
                    case GET_ALL_PREDMET:
                        List<Predmet> sviPredmeti = new ArrayList<>();
                        if (KontrolerPL.getInstance().ucitajListuPredmeta(sviPredmeti)) {
                            response.setResult(sviPredmeti);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case GET_ALL_EKSPERIMENTATOR:
                        List<Eksperimentator> sviEksperimentatori = new ArrayList<>();
                        if (KontrolerPL.getInstance().ucitajListuEksperimentator(sviEksperimentatori)) {
                            response.setResult(sviEksperimentatori);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case GET_ALL_EKSPERIMENT:
                        List<Eksperiment> sviEksperimenti = new ArrayList<>();
                        if (KontrolerPL.getInstance().ucitajListuEksperimenata(sviEksperimenti)) {
                            response.setResult(sviEksperimenti);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case KREIRAJ_STUDENTA:
                        Student student = (Student) request.getArgument();
                        if (KontrolerPL.getInstance().kreirajStudenta(student)) {
                            response.setResult(student);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case KREIRAJ_EKSPERIMENT:
                        Eksperiment eksperiment = (Eksperiment) request.getArgument();
                        if (KontrolerPL.getInstance().kreirajEksperiment(eksperiment)) {
                            response.setResult(eksperiment);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case KREIRAJ_EKSPERIMENTATORA:
                        Eksperimentator eksperimentator = (Eksperimentator) request.getArgument();
                        if (KontrolerPL.getInstance().kreirajEksperimentatora(eksperimentator)) {
                            response.setResult(eksperimentator);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case ZAPAMTI_STUDENTA:
                        Student studentEdit = (Student) request.getArgument();
                        if (KontrolerPL.getInstance().zapamtiStudenta(studentEdit)) {
                            response.setResult(studentEdit);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case ZAPAMTI_EKSPERIMENT:
                        Eksperiment eksperimentEdit = (Eksperiment) request.getArgument();
                        if (KontrolerPL.getInstance().zapamtiEksperiment(eksperimentEdit)) {
                            response.setResult(eksperimentEdit);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case ZAPAMTI_EKSPERIMENTATORA:
                        Eksperimentator eksperimentatorEdit = (Eksperimentator) request.getArgument();
                        if (KontrolerPL.getInstance().zapamtiEksperimentatora(eksperimentatorEdit)) {
                            response.setResult(eksperimentatorEdit);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case OBRISI_EKSPERIMENT:
                        Eksperiment eksperimentDelete = (Eksperiment) request.getArgument();
                        response.setResult(KontrolerPL.getInstance().obrisiEksperiment(eksperimentDelete));
                        break;
                    case PRETRAZI_STUDENTA:
                        Student studentFind = (Student) request.getArgument();
                        if (KontrolerPL.getInstance().pronadjiStudenta(studentFind)) {
                            response.setResult(studentFind);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case PRETRAZI_EKSPERIMENT:
                        Eksperiment eksperimentFind = (Eksperiment) request.getArgument();
                        if (KontrolerPL.getInstance().pronadjiEksperiment(eksperimentFind)) {
                            response.setResult(eksperimentFind);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case GET_ALL_STUDENT_USLOV:
                        ListaStudenata listaU = (ListaStudenata) request.getArgument();
                        List<Student> studenti = new ArrayList<>();
                        if (KontrolerPL.getInstance().ucitajListuStudenataSaUslovom(listaU, studenti)) {
                            response.setResult(studenti);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case ZAPAMTI_LISTU_STUDENTA:
                        Map mapa = (HashMap) request.getArgument();
                        if (KontrolerPL.getInstance().zapamtiListuStudenata(mapa)) {
                            response.setResult(mapa.get("Lista"));
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case KREIRAJ_RASPORED_EKSPERIMENATA:
                        RasporedEksperimenata reInsert = (RasporedEksperimenata) request.getArgument();
                        List<Eksperiment> listaEksperimenata = new ArrayList<>();
                        if (KontrolerPL.getInstance().zapamtiRasporedEksperimenata(reInsert, listaEksperimenata)) {
                            response.setResult(listaEksperimenata);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case ZAPAMTI_UCESCE:
                        SE ucesce = (SE) request.getArgument();
                        if (KontrolerPL.getInstance().zapamtiUcesce(ucesce)) {
                            response.setResult(ucesce);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case GET_ALL_UCESCA_NA_EKSPERIMENTU:
                        Map<String, Object> m = (HashMap) request.getArgument();
                        if (KontrolerPL.getInstance().ucescaNaEks(m)) {
                            response.setResult(m);
                        } else {
                            response.setException(new Exception());
                        }
                        break;
                    case IZVESTAJ_STUDENTI_SA_USLOVOM:
                        Map<String, Object> mapaStSaUslovom = (HashMap) request.getArgument();
                        if (KontrolerPL.getInstance().izvestajOStudentimaSaUslovom(mapaStSaUslovom)) {
                            response.setResult(mapaStSaUslovom);
                        } else {
                            response.setException(new Exception());
                        }
                    	break;
                    case IZVESTAJ_UCESNICI_NA_EKSPERIMENTU:
                    	Map<String, Object> mapaUcesnici = (HashMap) request.getArgument();
                        if (KontrolerPL.getInstance().izvestajOUcescuNaEksperimentu(mapaUcesnici)) {
                            response.setResult(mapaUcesnici);
                        } else {
                            response.setException(new Exception());
                        }
                    	break;
                    case IZVESTAJ_SPROVEDENI_EKSPERIMENTI:
                    	List<Eksperiment> sprovedniE = new ArrayList<>();
                    	if (KontrolerPL.getInstance().izvestajOSprovedenimEksperimentima(sprovedniE)) {
                            response.setResult(sprovedniE);
                        } else {
                            response.setException(new Exception());
                        }
                    	break;
                        
                }
                sender.send(response);
            }
        } catch (Exception ex) {
            server.stopClient(this);
        }
    }

    public Date getVremePridruzivanja() {
        return vremePridruzivanja;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
