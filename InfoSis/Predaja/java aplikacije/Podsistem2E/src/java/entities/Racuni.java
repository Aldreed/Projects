/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "racuni")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racuni.findAll", query = "SELECT r FROM Racuni r"),
    @NamedQuery(name = "Racuni.findByIdracuni", query = "SELECT r FROM Racuni r WHERE r.idracuni = :idracuni"),
    @NamedQuery(name = "Racuni.findByStanje", query = "SELECT r FROM Racuni r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racuni.findByDozvoljeniMinus", query = "SELECT r FROM Racuni r WHERE r.dozvoljeniMinus = :dozvoljeniMinus"),
    @NamedQuery(name = "Racuni.findByDatumOtvaranja", query = "SELECT r FROM Racuni r WHERE r.datumOtvaranja = :datumOtvaranja"),
    @NamedQuery(name = "Racuni.findByBrojTransakcija", query = "SELECT r FROM Racuni r WHERE r.brojTransakcija = :brojTransakcija")})
public class Racuni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idracuni")
    private Integer idracuni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private int stanje;
    @Column(name = "DozvoljeniMinus")
    private Integer dozvoljeniMinus;
    @Column(name = "DatumOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumOtvaranja;
    @Column(name = "BrojTransakcija")
    private Integer brojTransakcija;
    @JoinColumn(name = "Komitent", referencedColumnName = "idKomitenti")
    @ManyToOne(optional = false)
    private Komitenti komitent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "racun")
    private List<Transakcije> transakcijeList;

    public Racuni() {
    }

    public Racuni(Integer idracuni) {
        this.idracuni = idracuni;
    }

    public Racuni(Integer idracuni, int stanje) {
        this.idracuni = idracuni;
        this.stanje = stanje;
    }

    public Integer getIdracuni() {
        return idracuni;
    }

    public void setIdracuni(Integer idracuni) {
        this.idracuni = idracuni;
    }

    public int getStanje() {
        return stanje;
    }

    public void setStanje(int stanje) {
        this.stanje = stanje;
    }

    public Integer getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(Integer dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public Date getDatumOtvaranja() {
        return datumOtvaranja;
    }

    public void setDatumOtvaranja(Date datumOtvaranja) {
        this.datumOtvaranja = datumOtvaranja;
    }

    public Integer getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(Integer brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    public Komitenti getKomitent() {
        return komitent;
    }

    public void setKomitent(Komitenti komitent) {
        this.komitent = komitent;
    }

    @XmlTransient
    public List<Transakcije> getTransakcijeList() {
        return transakcijeList;
    }

    public void setTransakcijeList(List<Transakcije> transakcijeList) {
        this.transakcijeList = transakcijeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idracuni != null ? idracuni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racuni)) {
            return false;
        }
        Racuni other = (Racuni) object;
        if ((this.idracuni == null && other.idracuni != null) || (this.idracuni != null && !this.idracuni.equals(other.idracuni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Racuni[ idracuni=" + idracuni + " ]";
    }
    
}
