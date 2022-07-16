/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobs;

import java.io.Serializable;

/**
 *
 * @author Bogdan
 */
public class Job implements Serializable{
    //Implemented 4/16 + 1 bonus
    public enum JOB{CreateMesto,CreateFilijala,CreateKomitent,PromeniSediste,DohvatiSvaMesta,DohvatiSveFilijale,DohvatiSveKomitente,ProveriFilijaluZaPodsistem2,
    OtvoriRacun,ZatvoriRacun,TransakcijaSaRacunaNaRacun,TransakcijaUplata,TransakcijaIsplata,DohvatiSveRacune,DohvatiSveTransakcije,UpdatePodsistem3,DohvatiBackup,
    GetRazlike}
    
    private JOB jobType;

    private String args;

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public JOB getJobType() {
        return jobType;
    }

    public void setJobType(JOB jobType) {
        this.jobType = jobType;
    }

    
}
