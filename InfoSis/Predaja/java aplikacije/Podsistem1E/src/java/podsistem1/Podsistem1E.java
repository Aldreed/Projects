/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entities.Filijala;
import entities.Komitenti;
import entities.Mesto;
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
import jobs.Job.JOB;
import jobs.JobResponse;

/**
 *
 * @author Bogdan
 */
public class Podsistem1E {

    @Resource(lookup = "myConnFactory")
    static ConnectionFactory myConFac;

    
    @Resource(lookup = "Podsistem1_JobReciveQueue")
    static Queue myJobReciveQueue;

    @Resource(lookup = "Podsitem2_InterSystemReciveQueue")
    static Queue responseQueueP1toP2;

    @Resource(lookup = "CentralniServerResponseQueue")
    static Queue myResponseQueue;
    
    @Resource(lookup="Podsistem13Interface")
    static Queue P3Interface;
    
    private static List<Job> changes = new LinkedList<Job>();
    private static List<JobResponse> changesText = new LinkedList<JobResponse>();
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
        //EntityManager em = emf.createEntityManager();

        //Mesto m = new Mesto();
        //m.setNaziv("Dingo");
        //m.setPoštanskiBroj("1000-1000-1000");
        //em.getTransaction().begin();
        // em.persist(m);
        // em.getTransaction().commit();
        //Mesto fil = em.find(Mesto.class, 3);
        //System.out.println("Korisnik: naziv: " + fil.getNaziv() + " Poštanski broj: " + fil.getPoštanskiBroj());
        // em.getTransaction().begin();
        //  em.remove(fil);
        // em.getTransaction().commit();
        Podsistem1E sis1 = new Podsistem1E();

        //Create Mesto Test
        /*
        Job j = new Job();
        j.setJobType(Job.JOB.CreateMesto);
        j.setArgs("NovoMesto;-1123-1123");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
         */
        //Create Filijala Tests
        /*
        Job j = new Job();
        j.setJobType(Job.JOB.CreateFilijala);
        j.setArgs("NovoMesto;-AdresaRandom;-1");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
        //Wrong Id
        Job j = new Job();
        j.setJobType(Job.JOB.CreateFilijala);
        j.setArgs("NovoMesto;-AdresaRandom;--1");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
         */
        //Create Komitent Tests
        /*
        Job j = new Job();
        j.setJobType(Job.JOB.CreateKomitent);
        j.setArgs("Komi-chan;-JapanStr;-2");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
        Job j = new Job();
        j.setJobType(Job.JOB.CreateKomitent);
        j.setArgs("Komi-chan;-JapanStr;--2");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
         */
        //Promeni Sediste Tests
        /*
        Job j = new Job();
        j.setJobType(Job.JOB.PromeniSediste);
        j.setArgs("1;-1");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
        Job j = new Job();
        j.setJobType(Job.JOB.PromeniSediste);
        j.setArgs("1;--1");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
         */
        //Test Dohvati
        /*
        Job j = new Job();
        j.setJobType(Job.JOB.DohvatiSvaMesta);
        j.setArgs("1;--1");
        Response r = sis1.parseJob(j);
        String[] split = r.getMessage().split(";-");
        for (String string : split) {
        System.out.println(string);
        }
         */
        
        Job tempJob1 = new Job();
        Job tempJob2 = new Job();
        Job tempJob3 = new Job();
        Job tempJob4 = new Job();
        Job tempJob5 = new Job();
        Job tempJob6 = new Job();
        
        String tempJob1Args="Mesto1;-1111-1111";
        String tempJob2Args="Mesto2;-1111-1111";
        String tempJob3Args="Filijala1;-Ulica1;-1";
        String tempJob4Args="Komitent1;-Ulica2;-1";
        String tempJob5Args="Komitent2;-Ulica3;-1";
        String tempJob6Args="2;-1";
        
//        tempJob1.setJobType(JOB.CreateMesto);
//        tempJob2.setJobType(JOB.CreateMesto);
        //tempJob3.setJobType(JOB.CreateFilijala);
        //tempJob4.setJobType(JOB.CreateKomitent);
        //tempJob5.setJobType(JOB.CreateKomitent);
        //tempJob6.setJobType(JOB.PromeniSediste);
        
//        tempJob1.setArgs(tempJob1Args);
//        tempJob2.setArgs(tempJob2Args);
//        tempJob3.setArgs(tempJob3Args);
//        tempJob4.setArgs(tempJob4Args);
//        tempJob5.setArgs(tempJob5Args);
//        tempJob6.setArgs(tempJob6Args);
        
//        changes.add(tempJob1);
//        changes.add(tempJob2);
//        changes.add(tempJob3);
//        changes.add(tempJob4);
//        changes.add(tempJob5);
//        changes.add(tempJob6);
        
        
        JMSContext context = myConFac.createContext();
        JMSConsumer consumer = context.createConsumer(myJobReciveQueue);
        
        //changes.clear();
        //changesText.clear();

        while (true) {

            ObjectMessage om = (ObjectMessage) consumer.receive();

            try {
                Job j = (Job) om.getObject();
                JobResponse parseJobResponse = sis1.parseJob(j);
                JMSProducer prod = context.createProducer();

                ObjectMessage resp = context.createObjectMessage();
                resp.setObject(parseJobResponse);
                if (j.getJobType() == JOB.ProveriFilijaluZaPodsistem2) {
                    prod.send(responseQueueP1toP2, resp);
                } 
                else if(j.getJobType() == JOB.UpdatePodsistem3){
                    prod.send(P3Interface, resp);
                }
                else {
                    prod.send(myResponseQueue, resp);

                }

            } catch (JMSException ex) {
                Logger.getLogger(Podsistem1E.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public JobResponse parseJob(Job j) {

        String[] split = j.getArgs().split(";-");

        JobResponse r = null;

        switch (j.getJobType()) {
            case CreateMesto:
                r = kreiranjeMesta(split[0], split[1]);
                if(r.getRetValue()!=-1){
                    Podsistem1E.changes.add(j);
                    Podsistem1E.changesText.add(r);
                }
                break;
            case CreateFilijala:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = kreiranjeFilijala(split[0], split[1], Integer.decode(split[2]));
                if(r.getRetValue()!=-1){
                    Podsistem1E.changes.add(j);
                    Podsistem1E.changesText.add(r);
                }
                break;
            case CreateKomitent:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = kreiranjeKomitenta(split[0], split[1], Integer.decode(split[2]));
                if(r.getRetValue()!=-1){
                    Podsistem1E.changes.add(j);
                    Podsistem1E.changesText.add(r);
                }
                break;
            case PromeniSediste:
                //Proveri da li su dobri brojevi kod klijentske Strane
                r = promenaSedista(Integer.decode(split[0]), Integer.decode(split[1]));
                if(r.getRetValue()!=-1){
                    Podsistem1E.changes.add(j);
                    Podsistem1E.changesText.add(r);
                }
                break;
            case DohvatiSvaMesta:
                r = dohvatiSvaMesta();
                break;
            case DohvatiSveFilijale:
                r = dohvatiSveFilijale();
                break;
            case DohvatiSveKomitente:
                r = dohvatiSveKomitente();
                break;
            case ProveriFilijaluZaPodsistem2:
                r = proveriFilijalu(Integer.decode(split[0]));
                break;
            case UpdatePodsistem3:
                r = new JobResponse();
                r.setChanges(new LinkedList<Job>(changes));
                changes.clear();
                changesText.clear();
                break;
            case GetRazlike:
                r = new JobResponse();
                r.setMessage("Sistem1;-");
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

    public JobResponse proveriFilijalu(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
        EntityManager em = emf.createEntityManager();

        Filijala f = em.find(Filijala.class, id);

        JobResponse r = new JobResponse();
        if (f == null) {
            r.setMessage("0");
        } else {
            r.setMessage("1");
        }

        return r;

    }

    public JobResponse kreiranjeMesta(String Naziv, String PostanskiBroj) {

        createMesto(Naziv, PostanskiBroj);

        JobResponse r = new JobResponse();
        r.setMessage("Napravljeno Mesto: " + Naziv + " Postanski Broj: " + PostanskiBroj);
        
        return r;

    }

    public JobResponse kreiranjeFilijala(String Naziv, String Adresa, int IdMesto) {
        Mesto m = checkMestoExists(IdMesto);

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
        Mesto m = checkMestoExists(IdSediste);

        JobResponse r = new JobResponse();

        if (m == null) {
            r.setMessage("Nije uspelo kreiranje Komitenta:" + Naziv + "Adresa: " + Adresa + "Ne postoji Mesto:" + IdSediste);
            r.setRetValue(-1);
            return r;
        }

        createKomitent(Naziv, Adresa, m);

        r.setMessage("Napravljen Komitent:" + Naziv + " Adresa: " + Adresa + " Mesto:" + IdSediste);
        return r;

    }

    public JobResponse promenaSedista(int Sediste, int Komitent) {
        Mesto m = checkMestoExists(Sediste);

        JobResponse r = new JobResponse();

        if (m == null) {
            r.setMessage("Nije uspelo promena Sedista:" + Sediste + "za komitenta: " + Komitent + "Ne postoji Sediste");
            r.setRetValue(-1);
            return r;
        }

        Komitenti k = checkKomitentExists(Komitent);

        if (k == null) {
            r.setMessage("Nije uspelo promena Sedista:" + Sediste + "za komitenta: " + Komitent + "Ne postoji Komitent");
            r.setRetValue(-1);
            return r;
        }

        changeSediste(m, k);
        r.setMessage("Uspela promena Sedista:" + Sediste + "za komitenta: " + Komitent);
        return r;
    }

    public JobResponse dohvatiSvaMesta() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
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

    private void createMesto(String Naziv, String PostanskiBroj) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
        EntityManager em = emf.createEntityManager();

        Komitenti m = k;

        m.setSediste(Sediste);

        em.getTransaction().begin();

        m = em.find(Komitenti.class, k.getIdKomitenti());
        m.setSediste(Sediste);
        
        //em.persist(m);

        em.getTransaction().commit();

    }

    //Proveri da li postoji dato mesto
    private Mesto checkMestoExists(int mestoID) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
        EntityManager em = emf.createEntityManager();

        Mesto m = em.find(Mesto.class, mestoID);

        return m;
    }

    private Komitenti checkKomitentExists(int KomitentID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1EPU");
        EntityManager em = emf.createEntityManager();

        Komitenti m = em.find(Komitenti.class, KomitentID);

        return m;
    }

}
