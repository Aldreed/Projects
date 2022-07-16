/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bogdan
 */
@Entity
@Table(name = "komitenti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Komitenti.findAll", query = "SELECT k FROM Komitenti k"),
    @NamedQuery(name = "Komitenti.findByIdKomitenti", query = "SELECT k FROM Komitenti k WHERE k.idKomitenti = :idKomitenti"),
    @NamedQuery(name = "Komitenti.findByNaziv", query = "SELECT k FROM Komitenti k WHERE k.naziv = :naziv"),
    @NamedQuery(name = "Komitenti.findByAdresa", query = "SELECT k FROM Komitenti k WHERE k.adresa = :adresa")})
public class Komitenti implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKomitenti")
    private Integer idKomitenti;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Adresa")
    private String adresa;
    @JoinColumn(name = "Sediste", referencedColumnName = "idMesto")
    @ManyToOne(optional = false)
    private Mesto sediste;

    public Komitenti() {
    }

    public Komitenti(Integer idKomitenti) {
        this.idKomitenti = idKomitenti;
    }

    public Komitenti(Integer idKomitenti, String naziv, String adresa) {
        this.idKomitenti = idKomitenti;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    public Integer getIdKomitenti() {
        return idKomitenti;
    }

    public void setIdKomitenti(Integer idKomitenti) {
        this.idKomitenti = idKomitenti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Mesto getSediste() {
        return sediste;
    }

    public void setSediste(Mesto sediste) {
        this.sediste = sediste;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKomitenti != null ? idKomitenti.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komitenti)) {
            return false;
        }
        Komitenti other = (Komitenti) object;
        if ((this.idKomitenti == null && other.idKomitenti != null) || (this.idKomitenti != null && !this.idKomitenti.equals(other.idKomitenti))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Komitenti[ idKomitenti=" + idKomitenti + " ]";
    }
    
}
