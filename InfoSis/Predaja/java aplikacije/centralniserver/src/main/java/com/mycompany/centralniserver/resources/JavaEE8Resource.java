package com.mycompany.centralniserver.resources;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import jobs.Job;
import jobs.JobResponse;

/**
 *
 * @author 
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    @Resource(lookup="myConnFactory")
    ConnectionFactory myConFac;
    
    @Resource(lookup="Podsistem1_JobReciveQueue")
    Queue Podsistem1_JobReciveQueue;
    
    @Resource(lookup="Podsistem2_JobReciveQueue")
    Queue Podsistem2_JobReciveQueue;
    
    
    @Resource(lookup="Podsistem3_JobReciveQueue")
    Queue Podsistem3_JobReciveQueue;
    
    @Resource(lookup="CentralniServerResponseQueue")
    Queue myResponseQueue;
    
    
    @GET
    @Path("Referesh")
    public Response refresh(){
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        
        
        return Response
                .ok("ping")
                .build();
    
    }
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
    
    @GET
    @Path("TestPost")
    public Response pingPost(
            @QueryParam("TempString") String tempString,
            @QueryParam("TempInt") int tempInt){
        return Response
                .ok(tempString + (tempInt + 1) + "")
                .build();
    }
    
    //Podsistem 1
    @GET
    @Path("KreirajMesto")
    public Response kreirajMesto(
            @QueryParam("Naziv") String Naziv,
            @QueryParam("PostanskiBroj") String PostanskiBroj ){
    
        String args = Naziv +";-" + PostanskiBroj;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.CreateMesto);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("|");
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    
    @GET
    @Path("KreirajFilijalu")
    public Response kreirajFilijalu(
            @QueryParam("Naziv") String Naziv,
            @QueryParam("Adresa") String Adresa,
            @QueryParam("IdMesto")int IdMesto){
    
        String args = Naziv +";-" + Adresa+";-"+IdMesto;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.CreateFilijala);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("kreirajKomitenta")
    public Response kreirajKomitenta(
            @QueryParam("Naziv") String Naziv,
            @QueryParam("Adresa") String Adresa,
            @QueryParam("Sediste")int Sediste){
    
        String args = Naziv +";-" + Adresa+";-"+Sediste;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.CreateKomitent);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("PromeniSediste")
    public Response PromeniSediste(
            @QueryParam("Sediste")int Sediste,
            @QueryParam("Komitent")int Komitent){
    
        String args = Sediste +";-" + Komitent;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.PromeniSediste);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    
    @GET
    @Path("DohvatiSvaMesta")
    public Response DohvatiSvaMesta(){
    
        String args = "";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.DohvatiSvaMesta);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("DohvatiSveFilijale")
    public Response DohvatiSveFilijale(){
    
        String args = "";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.DohvatiSveFilijale);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("DohvatiSveKomitente")
    public Response DohvatiSveKomitente(){
    
        String args = "";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.DohvatiSveKomitente);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    
    //Podsistem 2
    @GET
    @Path("OtvoriRacun")
    public Response OtvoriRacun(
            @QueryParam("Komitent") int Komitent){
    
        String args = Komitent +"";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.OtvoriRacun);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("ZatvoriRacun")
    public Response ZatvoriRacun(
            @QueryParam("Racun") int Racun){
    
        String args = Racun +"";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.ZatvoriRacun);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("TransakcijaSaRacunaNaRacun")
    public Response TransakcijaSaRacunaNaRacun(
            @QueryParam("RacunOD") int RacunOD,
            @QueryParam("RacunDO") int RacunDO,
            @QueryParam("SUM") int SUM,
            @QueryParam("Svrha") String Svrha){
    
        String args = RacunOD +";-"+ RacunDO +";-" +SUM +";-"+ Svrha;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.TransakcijaSaRacunaNaRacun);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("TransakcijaUplata")
    public Response TransakcijaUplata(
            @QueryParam("Racun") int Racun,
            @QueryParam("SUM") int SUM,
            @QueryParam("Svrha") String Svrha,
            @QueryParam("Filijala") int Filijala){
    
        String args = Racun +";-"+ SUM +";-" +Svrha +";-"+ Filijala;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.TransakcijaUplata);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("TransakcijaIsplata")
    public Response TransakcijaIsplata(
            @QueryParam("Racun") int Racun,
            @QueryParam("SUM") int SUM,
            @QueryParam("Svrha") String Svrha,
            @QueryParam("Filijala") int Filijala){
    
        String args = Racun +";-"+ SUM +";-" +Svrha +";-"+ Filijala;
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.TransakcijaIsplata);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }

    @GET
    @Path("DohvatiSveRacune")
    public Response DohvatiSveRacune(
            @QueryParam("Komitent") int Komitent){
    
        String args = Komitent +"";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.DohvatiSveRacune);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }

    
    @GET
    @Path("DohvatiSveTransakcije")
    public Response DohvatiSveTransakcije(
            @QueryParam("Racun") int Racun){
    
        String args = Racun +"";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.DohvatiSveTransakcije);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

    }
    
    @GET
    @Path("DohvatiBackup")
    public Response DohvatiBakup(){
    
        String args = "dud";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.DohvatiBackup);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem3_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage()).build();

        
    }
    
    @GET
    @Path("GetRazlike")
    public Response GetRazlike(){
    
        String args = "dud";
        
        Job j = new Job();
        j.setArgs(args);
        j.setJobType(Job.JOB.GetRazlike);
        
        JMSContext context = myConFac.createContext();
        JMSProducer producer = context.createProducer();
        
        //Sistem1
        ObjectMessage om = context.createObjectMessage();
        try {
            om.setObject(j);
            producer.send(Podsistem1_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        JMSConsumer consumer = context.createConsumer(myResponseQueue);
        ObjectMessage res = (ObjectMessage) consumer.receive();
        JobResponse r = null;
        try {
            r = (JobResponse) res.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Sistem2
        ObjectMessage om2 = context.createObjectMessage();
        try {
            om2.setObject(j);
            producer.send(Podsistem2_JobReciveQueue, om);
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObjectMessage res2 = (ObjectMessage) consumer.receive();
        JobResponse r2 = null;
        try {
            r2 = (JobResponse) res2.getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(r==null || r2==null){
            return Response.serverError().build();
        }
        
        return Response.ok(r.getMessage() + r2.getMessage()).build();

        
    }
}
