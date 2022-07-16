/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "transakcije")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcije.findAll", query = "SELECT t FROM Transakcije t"),
    @NamedQuery(name = "Transakcije.findByIdtransakcije", query = "SELECT t FROM Transakcije t WHERE t.idtransakcije = :idtransakcije"),
    @NamedQuery(name = "Transakcije.findByBrojStavke", query = "SELECT t FROM Transakcije t WHERE t.brojStavke = :brojStavke"),
    @NamedQuery(name = "Transakcije.findByTip", query = "SELECT t FROM Transakcije t WHERE t.tip = :tip"),
    @NamedQuery(name = "Transakcije.findByDatum", query = "SELECT t FROM Transakcije t WHERE t.datum = :datum"),
    @NamedQuery(name = "Transakcije.findByIznos", query = "SELECT t FROM Transakcije t WHERE t.iznos = :iznos"),
    @NamedQuery(name = "Transakcije.findBySvrha", query = "SELECT t FROM Transakcije t WHERE t.svrha = :svrha"),
    @NamedQuery(name = "Transakcije.findByIdFilijala", query = "SELECT t FROM Transakcije t WHERE t.idFilijala = :idFilijala")})
public class Transakcije implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransakcije")
    private Integer idtransakcije;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brojStavke")
    private int brojStavke;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Tip")
    private int tip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Iznos")
    private int iznos;
    @Size(max = 200)
    @Column(name = "Svrha")
    private String svrha;
    @Column(name = "idFilijala")
    private Integer idFilijala;
    @JoinColumn(name = "Racun", referencedColumnName = "idracuni")
    @ManyToOne(optional = false)
    private Racuni racun;

    public Transakcije() {
    }

    public Transakcije(Integer idtransakcije) {
        this.idtransakcije = idtransakcije;
    }

    public Transakcije(Integer idtransakcije, int brojStavke, int tip, Date datum, int iznos) {
        this.idtransakcije = idtransakcije;
        this.brojStavke = brojStavke;
        this.tip = tip;
        this.datum = datum;
        this.iznos = iznos;
    }

    public Integer getIdtransakcije() {
        return idtransakcije;
    }

    public void setIdtransakcije(Integer idtransakcije) {
        this.idtransakcije = idtransakcije;
    }

    public int getBrojStavke() {
        return brojStavke;
    }

    public void setBrojStavke(int brojStavke) {
        this.brojStavke = brojStavke;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getIznos() {
        return iznos;
    }

    public void setIznos(int iznos) {
        this.iznos = iznos;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public Integer getIdFilijala() {
        return idFilijala;
    }

    public void setIdFilijala(Integer idFilijala) {
        this.idFilijala = idFilijala;
    }

    public Racuni getRacun() {
        return racun;
    }

    public void setRacun(Racuni racun) {
        this.racun = racun;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransakcije != null ? idtransakcije.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcije)) {
            return false;
        }
        Transakcije other = (Transakcije) object;
        if ((this.idtransakcije == null && other.idtransakcije != null) || (this.idtransakcije != null && !this.idtransakcije.equals(other.idtransakcije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transakcije[ idtransakcije=" + idtransakcije + " ]";
    }
    
}
