/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3e;

import entities.Filijala;
import entities.Komitenti;
import entities.Mesto;
import entities.Racuni;
import entities.Transakcije;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import jobs.Job;
import jobs.JobResponse;

/**
 *
 * @author Bogdan
 */
public class Podsistem3E {

    @Resource(lookup = "myConnFactory")
    public static ConnectionFactory connFact;

    @Resource(lookup = "Podsistem1_JobReciveQueue")
    public static Queue podsistem1JobQueue;

    @Resource(lookup = "Podsistem2_JobReciveQueue")
    public static Queue podsistem2JobQueue;

    @Resource(lookup = "Podsitem2_InterSystemReciveQueue")
    public static Queue podsistem2ResponseReciveQueue;

    @Resource(lookup = "Podsistem3_JobReciveQueue")
    public static Queue myJobReciveQueue;

    @Resource(lookup = "CentralniServerResponseQueue")
    public static Queue myResponseQueue;
    
    @Resource(lookup="Podsistem13Interface")
    public static Queue Podsistem13Interface;
    
    @Resource(lookup="Podsistem23Interface")
    public static Queue Podsistem23Interface;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Podsistem3E sis3 = new Podsistem3E();

        JMSContext context = connFact.createContext();
        JMSConsumer consumer = context.createConsumer(myJobReciveQueue);
        JMSProducer producer = context.createProducer();
        
        UpdateClass up = new UpdateClass();
        
//       JobResponse jr = new JobResponse();
//        jr.setMessage("debug");
//        ObjectMessage omd = context.createObjectMessage();
//        try {
//            omd.setObject(jr);
//        } catch (JMSException ex) {
//            Logger.getLogger(Podsistem3E.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        producer.send(myResponseQueue, omd);
        
        up.start();
        
        
        while (true) {

            ObjectMessage om = (ObjectMessage) consumer.receive();

            try {
                //om.getObject();
                Job j = (Job) om.getObject();
                JobResponse jr = new JobResponse();
                JobResponse parseJobResponse = sis3.parseJob(j);

                if(j.getJobType()==Job.JOB.DohvatiBackup){
                ObjectMessage res = (ObjectMessage) context.createObjectMessage();

                res.setObject(parseJobResponse);
                producer.send(myResponseQueue, res);
                }
                

                //System.out.println(parseJobResponse.getMessage());
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem3E.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public JobResponse parseJob(Job j) {

        String[] split = j.getArgs().split(";-");

        JobResponse r = null;

        switch (j.getJobType()) {
            case CreateMesto:
                r = kreiranjeMesta(split[0], split[1]);
                break;
            case CreateFilijala:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = kreiranjeFilijala(split[0], split[1], Integer.decode(split[2]));
                break;
            case CreateKomitent:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = kreiranjeKomitenta(split[0], split[1], Integer.decode(split[2]));
                break;
            case PromeniSediste:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = promenaSedista(Integer.decode(split[0]), Integer.decode(split[1]));
                break;
            case OtvoriRacun:
                r = otvoriRacun(Integer.decode(split[0]),Long.decode(split[1]));
                System.out.println("otvoren racun");
                break;
            case ZatvoriRacun:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = zatvoriRacun(Integer.decode(split[0]));
                break;
            case TransakcijaSaRacunaNaRacun:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = TransferRacunToRacun(Integer.decode(split[0]), Integer.decode(split[1]), Integer.decode(split[2]), split[3],Long.decode(split[4]));
                System.out.println("transakcija");
                break;
            case TransakcijaUplata:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = TransferUplata(Integer.decode(split[0]), Integer.decode(split[1]), split[2], Integer.decode(split[3]), Long.decode(split[4]));
                System.out.println("uplata");
                break;
            case TransakcijaIsplata:
                r = TransferIsplata(Integer.decode(split[0]), Integer.decode(split[1]), split[2], Integer.decode(split[3]), Long.decode(split[4]));
                break;
            case DohvatiBackup:
                r = getBackup();
                break;
        }

        if (r == null) {
            r = new JobResponse();
            r.setMessage("Invalid Job");
        }

        return r;

    }

    public JobResponse getBackup(){
        
        JobResponse resp1 =dohvatiSvaMesta();
        JobResponse resp2 = dohvatiSveFilijale();
        JobResponse resp3 = dohvatiSveKomitente();
        JobResponse resp4 = dohvatiSveRacune();
        JobResponse resp5 = dohvatiSveTransakcije();
        
        JobResponse resp = new JobResponse();
        
        resp.setMessage(
                resp1.getMessage() + ";-" +
                resp2.getMessage() + ";-" +
                resp3.getMessage() + ";-" +
                resp4.getMessage() + ";-" +
                resp5.getMessage() + ";-"
                        );
        
        return resp;
        
    
    }
    
    public JobResponse kreiranjeMesta(String Naziv, String PostanskiBroj) {

        createMesto(Naziv, PostanskiBroj);

        JobResponse r = new JobResponse();
        r.setMessage("Napravljeno Mesto: " + Naziv + " Postanski Broj: " + PostanskiBroj);

        return r;

    }

    public JobResponse kreiranjeFilijala(String Naziv, String Adresa, int IdMesto) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();
        
        Mesto m = em.find(Mesto.class, IdMesto);

        JobResponse r = new JobResponse();

        if (m == null) {
            r.setMessage("Nije uspelo kreiranje Filijale:" + Naziv + " Adresa: " + Adresa + " Ne postoji Mesto:" + IdMesto);
            r.setRetValue(-1);
            return r;
        }

        createFilijala(Naziv, Adresa, m);

        r.setMessage("Napravljena Filijala:" + Naziv + " Adresa: " + Adresa + " Mesto:" + IdMesto);
        return r;

    }

    public JobResponse kreiranjeKomitenta(String Naziv, String Adresa, int IdSediste) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
       EntityManager em = emf.createEntityManager();
        
        Mesto m = em.find(Mesto.class, IdSediste);

        JobResponse r = new JobResponse();

        createKomitent(Naziv, Adresa, m);

        r.setMessage("Napravljen Komitent:" + Naziv + " Adresa: " + Adresa + " Mesto:" + IdSediste);
        return r;

    }

    public JobResponse promenaSedista(int Sediste, int Komitent) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();
        
        Mesto m = em.find(Mesto.class, Sediste);

        JobResponse r = new JobResponse();

        Komitenti k = em.find(Komitenti.class, Komitent);

        changeSediste(m, k);
        r.setMessage("Uspela promena Sedista:" + Sediste + "za komitenta: " + Komitent);
        return r;
    }

    private void createMesto(String Naziv, String PostanskiBroj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Mesto m = new Mesto();
        m.setNaziv(Naziv);
        m.setPoštanskiBroj(PostanskiBroj);

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

    }

    //Proveri da li je mesto ok
    // public static boolean checkCreateMesto(){}
    private void createFilijala(String Naziv, String Adresa, Mesto MestoId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Filijala m = new Filijala();
        m.setNaziv(Naziv);
        m.setMestoFilijale(MestoId);
        m.setAdresa(Adresa);

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

    }

    //Proveri da li je Filijala ok
    //public static boolean checkCreateFilijala(){}
    private void createKomitent(String Naziv, String Adresa, Mesto Sediste) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Komitenti m = new Komitenti();
        m.setNaziv(Naziv);
        //check
        m.setSediste(Sediste);
        m.setAdresa(Adresa);

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

    }

    private void changeSediste(Mesto Sediste, Komitenti k) {
        //Check Mesto Exists
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Komitenti m = k;

        m.setSediste(Sediste);

        em.getTransaction().begin();

        m = em.find(Komitenti.class, k.getIdKomitenti());
        m.setSediste(Sediste);

        //em.persist(m);
        em.getTransaction().commit();

    }

    
    private JobResponse otvoriRacun(int komitentId, long time) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        JobResponse r = new JobResponse();

        Komitenti k = em.find(Komitenti.class, komitentId);

        if (k == null) {
            r.setMessage("Nije otvoren racun, ne postoji komitent: " + komitentId);
            r.setRetValue(-1);
            return r;
        }

        createRacun(k, 0, -100, Date.valueOf(LocalDate.now()), 0,time);

        r.setMessage("Napravljen racun");

        return r;
    }

    private JobResponse zatvoriRacun(int racunId) {
        Racuni r = checkRacunExists(racunId);
        JobResponse res = new JobResponse();

        if (r == null) {
            res.setMessage("Nije zatvoren racun, ne postoji racun: " + racunId);
            res.setRetValue(-1);
            return res;
        }

        if (r.getStanje() != 0) {
            res.setMessage("Nije zatvoren racun, stanje nije 0");
            res.setRetValue(-1);
            return res;
        }

        deleteRacun(racunId);

        res.setMessage("Zatvoren racun: " + racunId);
        return res;
    }

    private JobResponse TransferRacunToRacun(int racunOd, int racunDo, int sum, String Svrha, long time) {
        JobResponse r = new JobResponse();
        Racuni rOd = checkRacunExists(racunOd);
        Racuni rDo = checkRacunExists(racunDo);

        if (rOd == null) {
            r.setMessage("Nije obavljena transakcija, ne postoji racun: " + racunOd);
            r.setRetValue(-1);
            return r;
        } else if (rDo == null) {
            r.setMessage("Nije obavljena transakcija, ne postoji racun: " + racunOd);
            r.setRetValue(-1);
            return r;
        }

        if (!checkBalanceToTransfer(rOd, sum)) {
            r.setMessage("Racun nema dovoljno sredstava da obavi transakciju");
            r.setRetValue(-1);
            return r;
        } else if (!checkStatusToTransfer(rOd)) {
            r.setMessage("Racun je blokiran");
            r.setRetValue(-1);
            return r;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();
        
        rOd = em.find(Racuni.class, racunOd);
        rDo = em.find(Racuni.class, racunDo);

        int rOdStaroStanje = rOd.getStanje();
        int rOdStariBrojTransakcia = rOd.getBrojTransakcija();

        int rDoStaroStanje = rDo.getStanje();
        int rDoStariBrojTransakcia = rDo.getBrojTransakcija();

        em.getTransaction().begin();
        rOd = em.find(Racuni.class, rOd.getIdracuni());
        rDo = em.find(Racuni.class, rDo.getIdracuni());

        rOd.setStanje(rOdStaroStanje - sum);
        rOd.setBrojTransakcija(rOdStariBrojTransakcia + 1);

        rDo.setStanje(rDoStaroStanje + sum);
        rDo.setBrojTransakcija(rDoStariBrojTransakcia + 1);

        em.getTransaction().commit();

        // Atomic ? neka vracaju transakciju pa je onda persistuj gore ?
        createTransaction(rOd, 0, Date.valueOf(LocalDate.now()), sum, Svrha, -1,time);
        createTransaction(rDo, 1, Date.valueOf(LocalDate.now()), sum, Svrha, -1,time);

        r.setMessage("Transakcija izmedju racuna: " + racunOd + " i racuna: " + racunDo + "obavljena");
        return r;

    }

    public JobResponse TransferUplata(int racunId, int sum, String Svrha, int filijalaId, long time) {
        JobResponse r = new JobResponse();
        Racuni rOd = checkRacunExists(racunId);

        if (rOd == null) {
            r.setMessage("Nije obavljena transakcija, ne postoji racun: " + racunId);
            r.setRetValue(-1);
            return r;
        }

        if (!checkFilijalaExists(filijalaId)) {
            r.setMessage("Nije obavljena transakcija, ne postoji filijala: " + filijalaId);
            r.setRetValue(-1);
            return r;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        rOd = em.find(Racuni.class, racunId);

        int rOdStaroStanje = rOd.getStanje();
        int rOdStariBrojTransakcia = rOd.getBrojTransakcija();

        em.getTransaction().begin();
        //TypedQuery<Racuni> typedQuery = em.createQuery("update Racuni set Stanje = " + rOd.getStanje() + ", BrojTransakcija = "+ rOd.getBrojTransakcija() + " where idracuni= "+ rOd.getIdracuni(), Racuni.class);
        //Mora jer razlozi...?
        rOd = em.find(Racuni.class, rOd.getIdracuni());

        rOd.setStanje(rOdStaroStanje + sum);
        rOd.setBrojTransakcija(rOdStariBrojTransakcia + 1);

        em.getTransaction().commit();

        // Atomic ? neka vracaju transakciju pa je onda persistuj gore ?
        createTransaction(rOd, 0, Date.valueOf(LocalDate.now()), sum, Svrha, filijalaId,time);

        r.setMessage("Izvrsena uplata na racun: " + racunId);
        return r;
    }

    private JobResponse TransferIsplata(int racunId, int sum, String Svrha, int filijalaId, long time) {
        JobResponse r = new JobResponse();
        Racuni rOd = checkRacunExists(racunId);

        if (rOd == null) {
            r.setMessage("Nije obavljena transakcija, ne postoji racun: " + racunId);
            r.setRetValue(-1);
            return r;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        //Ne znam
        rOd = em.find(Racuni.class, racunId);

        if (!checkBalanceToTransfer(rOd, sum)) {
            r.setMessage("Racun nema dovoljno sredstava da obavi transakciju");
            r.setRetValue(-1);
            return r;
        } else if (!checkStatusToTransfer(rOd)) {
            r.setMessage("Racun je blokiran");
            r.setRetValue(-1);
            return r;
        }

        if (!checkFilijalaExists(filijalaId)) {
            r.setMessage("Nije obavljena transakcija, ne postoji filijala: " + filijalaId);
            r.setRetValue(-1);
            return r;
        }

        int rOdStaroStanje = rOd.getStanje();
        int rOdStariBrojTransakcia = rOd.getBrojTransakcija();

        em.getTransaction().begin();
        rOd = em.find(Racuni.class, rOd.getIdracuni());

        rOd.setStanje(rOdStaroStanje - sum);
        rOd.setBrojTransakcija(rOdStariBrojTransakcia + 1);
        em.getTransaction().commit();

        // Atomic ? neka vracaju transakciju pa je onda persistuj gore ?
        createTransaction(rOd, 3, Date.valueOf(LocalDate.now()), sum, Svrha, -1, time);

        r.setMessage("Obavljena isplata za racun: " + racunId);
        return r;
    }
    
    
    private void createRacun(Komitenti k, int stanje, int DozvoljenMinus, Date DatumOtvaranja, int brojTransakcija,long time) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Racuni m = new Racuni();
        m.setBrojTransakcija(brojTransakcija);
        m.setDatumOtvaranja(new Timestamp(time));
        m.setDozvoljeniMinus(DozvoljenMinus);
        m.setKomitent(k);
        m.setStanje(stanje);

        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();

    }

    //Proveri pre pokretanja da li postoji idRacun i da li je 0; Brise i transakcije
    private void deleteRacun(int idRacun) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Racuni r = em.find(Racuni.class, idRacun);

        TypedQuery<Transakcije> createNamedQuery = em.createNamedQuery("Transakcije.findAll", Transakcije.class);
        //createNamedQuery.setParameter(1, idRacun);
        List<Transakcije> resultList = createNamedQuery.getResultList();

        if (r != null) {
            em.getTransaction().begin();
            for (Transakcije transakcije : resultList) {
                if (transakcije.getRacun().getIdracuni() == idRacun) {
                    em.remove(em);
                }
            }
            em.remove(r);
            em.getTransaction().commit();
        }
    }

    //Tip
    //0 - Racun to Racun rOd
    //1 - Racun to Racun rDo
    //2 - Uplata
    //3 - Isplata
    private void createTransaction(Racuni r, int Tip, Date Datum, int Iznos, String Svrha, int Filijala, long time) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Transakcije t = new Transakcije();

        em.getTransaction().begin();
        t.setRacun(r);
        t.setBrojStavke(r.getBrojTransakcija());
        t.setTip(Tip);
        t.setDatum(new Timestamp(time));
        t.setIznos(Iznos);
        t.setSvrha(Svrha);
        if (Filijala == -1) {
            t.setIdFilijala(null);
        } else {
            t.setIdFilijala(Filijala);
        }
        em.persist(t);

        em.getTransaction().commit();
    }

    private Racuni checkRacunExists(int idRacun) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        Racuni r = em.find(Racuni.class, idRacun);

        return r;
    }

    private boolean checkBalanceToTransfer(Racuni r, int transfer) {
        return true;
    }

    private boolean checkStatusToTransfer(Racuni r) {

        return true;

    }

    private boolean checkFilijalaExists(int filijalaId) {

        return true;
    }
    
    public JobResponse dohvatiSvaMesta() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Mesto> res = em.createNamedQuery("Mesto.findAll", Mesto.class);
        List<Mesto> resultList = res.getResultList();

        String s = resultList.size() + ";-";

        for (Mesto mesto : resultList) {
            s = s + " Id: " + mesto.getIdMesto() + " Naziv: " + mesto.getNaziv() + " Postanski Broj: " + mesto.getPoštanskiBroj() + ";-";
        }

        JobResponse r = new JobResponse();
        r.setMessage(s);

        return r;
    }

    public JobResponse dohvatiSveFilijale() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Filijala> res = em.createNamedQuery("Filijala.findAll", Filijala.class);
        List<Filijala> resultList = res.getResultList();

        String s = resultList.size() + ";-";

        for (Filijala fili : resultList) {
            s = s + " Id: " + fili.getIdFilijala() + " Naziv: " + fili.getNaziv() + " Adresa: " + fili.getAdresa() + " Mesto ID: " + fili.getMestoFilijale().getIdMesto()
                    + " Mesto Naziv: " + fili.getMestoFilijale().getNaziv() + ";-";
        }

        JobResponse r = new JobResponse();
        r.setMessage(s);

        return r;
    }

    public JobResponse dohvatiSveKomitente() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Komitenti> res = em.createNamedQuery("Komitenti.findAll", Komitenti.class);
        List<Komitenti> resultList = res.getResultList();

        String s = resultList.size() + ";-";

        for (Komitenti komitent : resultList) {
            s = s + " Id: " + komitent.getIdKomitenti() + " Naziv: " + komitent.getNaziv() + " Adresa: " + komitent.getAdresa() + " Sediste ID: " + komitent.getSediste().getIdMesto()
                    + " Mesto Naziv: " + komitent.getSediste().getNaziv() + ";-";
        }

        JobResponse r = new JobResponse();
        r.setMessage(s);

        return r;
    }
    
    
    private JobResponse dohvatiSveRacune() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        JobResponse r = new JobResponse();

        //TypedQuery<Racuni> res = em.createQuery("SELECT r FROM Racuni r WHERE r.Komitent = ?1", Racuni.class);
        //res.setParameter(1, idkomitent);
        //List<Racuni> resultList = res.getResultList();
        TypedQuery<Racuni> res = em.createNamedQuery("Racuni.findAll", Racuni.class);
        //createNamedQuery.setParameter(1, idRacun);
        List<Racuni> resultList = res.getResultList();

        String s = "Lista: " + ";-";

        for (Racuni racun : resultList) {
                s = s + " Id: " + racun.getIdracuni() + " Komitent ID: " + racun.getKomitent().getIdKomitenti()+ "Komitenti Naziv: " + racun.getKomitent().getNaziv()
                        + " Stanje: " + racun.getStanje() + " Dozvoljen minus: " + racun.getDozvoljeniMinus() + " DatumOtvaranja: " + racun.getDatumOtvaranja()
                        + " BrojTransakcija: " + racun.getBrojTransakcija() + ";-";
        }
        r.setMessage(s);

        return r;

    }

    private JobResponse dohvatiSveTransakcije() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3EPU");
        EntityManager em = emf.createEntityManager();

        JobResponse r = new JobResponse();

        TypedQuery<Transakcije> createNamedQuery = em.createNamedQuery("Transakcije.findAll", Transakcije.class);
        //createNamedQuery.setParameter(1, idRacun);
        List<Transakcije> resultList = createNamedQuery.getResultList();

        String s = "Lista: " + ";-";

        for (Transakcije transakcija : resultList) {
            
                s = s + " Id: " + transakcija.getIdtransakcije() + " Broj Stavke: " + transakcija.getBrojStavke() + " Tip: " + transakcija.getTip()
                        + " Datum: " + transakcija.getDatum() + " Iznos: " + transakcija.getIznos() + " Svrha: " + transakcija.getSvrha() + "Filijala: " + transakcija.getIdFilijala()
                        + " Racun: " + transakcija.getRacun() + ";-";

        }
        r.setMessage(s);

        return r;

    }

    
    
}
