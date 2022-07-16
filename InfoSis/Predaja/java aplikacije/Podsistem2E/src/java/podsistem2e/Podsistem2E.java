/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2e;

import entities.Komitenti;
import entities.Racuni;
import entities.Transakcije;
import entities.Transakcije_;
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
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jobs.Job;

import jobs.JobResponse;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author Bogdan
 */
public class Podsistem2E {

    @Resource(lookup = "myConnFactory")
    static ConnectionFactory connFact;

    @Resource(lookup = "Podsistem1_JobReciveQueue")
    static Queue podsistem1JobQueue;

    @Resource(lookup = "Podsitem2_InterSystemReciveQueue")
    static Queue myResponseReciveQueue;

    @Resource(lookup = "Podsistem2_JobReciveQueue")
    static Queue myJobReciveQueue;

    @Resource(lookup = "CentralniServerResponseQueue")
    static Queue myResponseQueue;

    @Resource(lookup = "Podsistem23Interface")
    static Queue P3Interface;

    private static List<Job> changes = new LinkedList<Job>();
    private static List<JobResponse> changesText = new LinkedList<JobResponse>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Podsistem2E sis2 = new Podsistem2E();

        //JobResponse r = sis2.TransferUplata(1, 100, "dd", 1);
        //System.err.println(r.getMessage());
        
        //Job tempJob1 = new Job();
        //Job tempJob2 = new Job();
        //Job tempJob3 = new Job();
        //Job tempJob4 = new Job();
        //Job tempJob5 = new Job();
        //Job tempJob6 = new Job();
        
        //long tempTime = System.currentTimeMillis();
        
        //String tempJob1Args="1;-"+tempTime;
        //String tempJob2Args="1;-"+tempTime;
        //String tempJob3Args="2;-"+tempTime;
        //String tempJob4Args="1;-100;-dd;-1;-"+tempTime;
        //String tempJob5Args="2";
        //String tempJob6Args="1;-3;-100;-dd;-"+tempTime;
        
        //tempJob1.setJobType(Job.JOB.OtvoriRacun);
        //tempJob2.setJobType(Job.JOB.OtvoriRacun);
        //tempJob3.setJobType(Job.JOB.OtvoriRacun);
        //tempJob4.setJobType(Job.JOB.TransakcijaUplata);
        //tempJob5.setJobType(Job.JOB.ZatvoriRacun);
        //tempJob6.setJobType(Job.JOB.TransakcijaSaRacunaNaRacun);
        
        //tempJob1.setArgs(tempJob1Args);
        //tempJob2.setArgs(tempJob2Args);
        //tempJob3.setArgs(tempJob3Args);
        //tempJob4.setArgs(tempJob4Args);
        //tempJob5.setArgs(tempJob5Args);
        //tempJob6.setArgs(tempJob6Args);
        
        //changes.add(tempJob1);
        //changes.add(tempJob2);
        //changes.add(tempJob3);
        //changes.add(tempJob4);
        //changes.add(tempJob5);
        //changes.add(tempJob6);
        
        JMSContext context = connFact.createContext();
        JMSConsumer consumer = context.createConsumer(myJobReciveQueue);
        JMSProducer producer = context.createProducer();
        
        
        changes.clear();
        changesText.clear();
        
        while (true) {

            ObjectMessage om = (ObjectMessage) consumer.receive();

            try {
                Job j = (Job) om.getObject();
                JobResponse parseJobResponse = sis2.parseJob(j);

                ObjectMessage res = (ObjectMessage) context.createObjectMessage();

                res.setObject(parseJobResponse);

                if (j.getJobType()==Job.JOB.UpdatePodsistem3) {
                    producer.send(P3Interface, res);
                }
                else if(j.getJobType()==Job.JOB.CreateKomitent){
                    continue;
                }
                else {
                    producer.send(myResponseQueue, res);
                }

                //System.out.println(parseJobResponse.getMessage());
            } catch (JMSException ex) {
                Logger.getLogger(Podsistem2E.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //Testing ... 
    }

    public JobResponse parseJob(Job j) {
        String[] split = j.getArgs().split(";-");

        JobResponse r = null;
        long time = System.currentTimeMillis();
        String newArgs = "";

        switch (j.getJobType()) {
            case OtvoriRacun:
                r = otvoriRacun(Integer.decode(split[0]), time);
                if (r.getRetValue() != -1) {
                    j.setArgs(j.getArgs() + ";-" + time);
                    Podsistem2E.changes.add(j);
                    Podsistem2E.changesText.add(r);
                }
                break;
            case ZatvoriRacun:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = zatvoriRacun(Integer.decode(split[0]));
                if (r.getRetValue() != -1) {
                    Podsistem2E.changes.add(j);
                    Podsistem2E.changesText.add(r);
                }
                break;
            case TransakcijaSaRacunaNaRacun:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = TransferRacunToRacun(Integer.decode(split[0]), Integer.decode(split[1]), Integer.decode(split[2]), split[3], time);
                if (r.getRetValue() != -1) {
                    j.setArgs(j.getArgs() + ";-" + time);
                    Podsistem2E.changes.add(j);
                    Podsistem2E.changesText.add(r);
                }
                break;
            case TransakcijaUplata:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = TransferUplata(Integer.decode(split[0]), Integer.decode(split[1]), split[2], Integer.decode(split[3]), time);
                if (r.getRetValue() != -1) {
                    j.setArgs(j.getArgs() + ";-" + time);
                    Podsistem2E.changes.add(j);
                    Podsistem2E.changesText.add(r);
                }
                break;
            case TransakcijaIsplata:
                r = TransferIsplata(Integer.decode(split[0]), Integer.decode(split[1]), split[2], Integer.decode(split[3]), time);
                if (r.getRetValue() != -1) {
                    j.setArgs(j.getArgs() + ";-" + time);
                    Podsistem2E.changes.add(j);
                    Podsistem2E.changesText.add(r);
                }
                break;
            case DohvatiSveRacune:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = dohvatiSveRacune(Integer.decode(split[0]));
                break;
            case DohvatiSveTransakcije:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = dohvatiSveTransakcije(Integer.decode(split[0]));
                break;
            case UpdatePodsistem3:
                r = new JobResponse();
                r.setChanges(new LinkedList<>(changes));
                changes.clear();
                changesText.clear();
                break;
            case CreateKomitent:
                r = kreiranjeKomitenta(split[0], split[1], Integer.decode(split[2]));
            break;
            case GetRazlike:
                r = new JobResponse();
                r.setMessage("Sistem2;-");
                for (JobResponse jobResponse : changesText) {
                    r.setMessage(r.getMessage()+jobResponse.getMessage()+";-");
                }
                break;
        }

        if (r == null) {
            r = new JobResponse();
            r.setMessage("Invalid Job");
        }

        return r;

    }


    public JobResponse kreiranjeKomitenta(String Naziv, String Adresa, int IdSediste) {
        
        JobResponse r = new JobResponse();

        createKomitent(Naziv, Adresa, IdSediste);

        r.setMessage("Napravljen Komitent:" + Naziv + " Adresa: " + Adresa + " Mesto:" + IdSediste);
        return r;

    }
    
    private JobResponse otvoriRacun(int komitentId, long time) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
        EntityManager em = emf.createEntityManager();

        JobResponse r = new JobResponse();

        Komitenti k = em.find(Komitenti.class, komitentId);

        if (k == null) {
            r.setMessage("Nije otvoren racun, ne postoji komitent: " + komitentId);
            r.setRetValue(-1);
            return r;
        }

        createRacun(k, 0, -100, Date.valueOf(LocalDate.now()), 0, time);

        r.setMessage("Napravljen racun za" + komitentId);

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
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
        EntityManager em = emf.createEntityManager();
        
        rOd = em.find(Racuni.class, racunOd);
        rDo = em.find(Racuni.class, racunDo);
        

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

        rOd = em.find(Racuni.class, rOd.getIdracuni());
        rDo = em.find(Racuni.class, rDo.getIdracuni());

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
        createTransaction(rOd, 0, Date.valueOf(LocalDate.now()), sum, Svrha, -1, time);
        createTransaction(rDo, 1, Date.valueOf(LocalDate.now()), sum, Svrha, -1, time);

        r.setMessage("Transakcija izmedju racuna: " + racunOd + " i racuna: " + racunDo + "obavljena suma:" + sum);
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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
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
        createTransaction(rOd, 0, Date.valueOf(LocalDate.now()), sum, Svrha, filijalaId, time);

        r.setMessage("Izvrsena uplata"+sum+ " na racun: " + racunId);
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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
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

        r.setMessage("Obavljena isplata " + sum +  " za racun: " + racunId);
        return r;
    }

    private JobResponse dohvatiSveRacune(int idkomitent) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
        EntityManager em = emf.createEntityManager();

        JobResponse r = new JobResponse();

        Komitenti k = em.find(Komitenti.class, idkomitent);

        if (k == null) {
            r.setMessage("Nisu dohvaceni racuni, ne postoji komitent: " + idkomitent);
            return r;
        }

        //TypedQuery<Racuni> res = em.createQuery("SELECT r FROM Racuni r WHERE r.Komitent = ?1", Racuni.class);
        //res.setParameter(1, idkomitent);
        //List<Racuni> resultList = res.getResultList();
        TypedQuery<Racuni> res = em.createNamedQuery("Racuni.findAll", Racuni.class);
        //createNamedQuery.setParameter(1, idRacun);
        List<Racuni> resultList = res.getResultList();

        String s = "Lista: " + ";-";

        for (Racuni racun : resultList) {
            if (racun.getKomitent().getIdKomitenti() == idkomitent) {
                s = s + " Id: " + racun.getIdracuni() + " Komitent ID: " + racun.getKomitent().getIdKomitenti() + "Komitenti Naziv: " + racun.getKomitent().getNaziv()
                        + " Stanje: " + racun.getStanje() + " Dozvoljen minus: " + racun.getDozvoljeniMinus() + " DatumOtvaranja: " + racun.getDatumOtvaranja()
                        + " BrojTransakcija: " + racun.getBrojTransakcija() + ";-";
            }
        }
        r.setMessage(s);

        return r;

    }

    private JobResponse dohvatiSveTransakcije(int idRacun) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
        EntityManager em = emf.createEntityManager();

        JobResponse r = new JobResponse();

        Racuni rac = em.find(Racuni.class, idRacun);

        if (rac == null) {
            r.setMessage("Nisu dohvacene transakcije, ne postoji racun: " + idRacun);
            return r;
        }

        TypedQuery<Transakcije> createNamedQuery = em.createNamedQuery("Transakcije.findAll", Transakcije.class);
        //createNamedQuery.setParameter(1, idRacun);
        List<Transakcije> resultList = createNamedQuery.getResultList();

        String s = "Lista: " + ";-";

        for (Transakcije transakcija : resultList) {
            if (transakcija.getRacun().getIdracuni() == idRacun) {
                s = s + " Id: " + transakcija.getIdtransakcije() + " Broj Stavke: " + transakcija.getBrojStavke() + " Tip: " + transakcija.getTip()
                        + " Datum: " + transakcija.getDatum() + " Iznos: " + transakcija.getIznos() + " Svrha: " + transakcija.getSvrha() + "Filijala: " + transakcija.getIdFilijala()
                        + " Racun: " + transakcija.getRacun() + ";-";
            }
        }
        r.setMessage(s);

        return r;

    }

    private void createRacun(Komitenti k, int stanje, int DozvoljenMinus, Date DatumOtvaranja, int brojTransakcija, long time) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
        EntityManager em = emf.createEntityManager();

        Racuni r = em.find(Racuni.class, idRacun);

        return r;
    }

    private boolean checkBalanceToTransfer(Racuni r, int transfer) {

        if (r.getStanje() > transfer) {
            return true;
        }
        return false;
    }

    private boolean checkStatusToTransfer(Racuni r) {

        if (r.getStanje() > r.getDozvoljeniMinus()) {
            return true;
        }
        return false;

    }

    private boolean checkFilijalaExists(int filijalaId) {

        JMSContext context = connFact.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(myResponseReciveQueue);

        jobs.Job j = new Job();
        j.setJobType(Job.JOB.ProveriFilijaluZaPodsistem2);
        j.setArgs(filijalaId + "");

        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(podsistem1JobQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(Podsistem2E.class.getName()).log(Level.SEVERE, null, ex);
        }

        om = (ObjectMessage) consumer.receive();

        JobResponse r;
        try {
            r = (JobResponse) om.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(Podsistem2E.class.getName()).log(Level.SEVERE, null, ex);
            r = null;
        }

        if (r == null) {
            return false;
        }

        if (Integer.decode(r.getMessage()).intValue() == 0) {
            return false;
        } else if (Integer.decode(r.getMessage()).intValue() == 1) {
            return true;
        }

        return false;
    }

    private void createKomitent(String Naziv, String Adresa, int Sediste) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2EPU");
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
    
}
