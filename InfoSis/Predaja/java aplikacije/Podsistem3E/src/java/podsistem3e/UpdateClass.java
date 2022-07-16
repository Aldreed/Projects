/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3e;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import jobs.Job;
import jobs.JobResponse;
import static podsistem3e.Podsistem3E.Podsistem13Interface;
import static podsistem3e.Podsistem3E.Podsistem23Interface;
import static podsistem3e.Podsistem3E.connFact;
import static podsistem3e.Podsistem3E.myJobReciveQueue;
import static podsistem3e.Podsistem3E.podsistem1JobQueue;
import static podsistem3e.Podsistem3E.podsistem2JobQueue;

/**
 *
 * @author Bogdan
 */
public class UpdateClass extends Thread{

    @Override
    public void run() {
        JMSContext context = connFact.createContext();
            
            JMSConsumer consumerP1 = context.createConsumer(Podsistem13Interface);
            JMSConsumer consumerP2 = context.createConsumer(Podsistem23Interface);
            
            JMSProducer producer = context.createProducer();
        while(true){
            try {
                Thread.sleep(30*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Job j = new Job();
            j.setJobType(Job.JOB.UpdatePodsistem3);
            j.setArgs("null");

            ObjectMessage omP1 = context.createObjectMessage();
            try {
                omP1.setObject(j);
            } catch (JMSException ex) {
                Logger.getLogger(UpdateClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            producer.send(podsistem1JobQueue, omP1);
            
            
            
            ObjectMessage reP1 = (ObjectMessage) consumerP1.receive();
            System.out.println("pod1");
            try {
                JobResponse listP1 = (JobResponse) reP1.getObject();
                List<Job> changes = listP1.getChanges();
                if(changes.size()==0){
                    System.out.println("No changes");
                }
                for (Job change : changes) {
                    ObjectMessage om1 = context.createObjectMessage();
                    om1.setObject(change);
                    producer.send(myJobReciveQueue, om1);
                    
                    if(change.getJobType()==Job.JOB.CreateKomitent){
                        ObjectMessage om2 = context.createObjectMessage();
                        om2.setObject(change);
                        producer.send(podsistem2JobQueue, om2);
                    }
                }
                
            } catch (JMSException ex) {
                Logger.getLogger(UpdateClass.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("ErrorD");
            }
         
            
            ObjectMessage omP2 = context.createObjectMessage();
            
            try {
                omP2.setObject(j);
            } catch (JMSException ex) {
                Logger.getLogger(UpdateClass.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("ErrorD");
            }
            
            producer.send(podsistem2JobQueue, omP2);
            
            ObjectMessage reP2 = (ObjectMessage) consumerP2.receive();
            System.out.println("pod2");
            try {
                JobResponse listP2 = (JobResponse) reP2.getObject();
                List<Job> changes = listP2.getChanges();
                if(changes.size()==0){
                    System.out.println("No changes");
                }
                for (Job change : changes) {
                    ObjectMessage om1 = context.createObjectMessage();
                    om1.setObject(change);
                    producer.send(myJobReciveQueue, om1);
                }
                
            } catch (JMSException ex) {
                Logger.getLogger(UpdateClass.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("ErrorD");
            }
            
            System.out.println("Done Update at: " + new Timestamp(System.currentTimeMillis()));
        }
    }
    
    
    
}
