package entities;

import entities.Filijala;
import entities.Komitenti;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-02T12:55:24")
@StaticMetamodel(Mesto.class)
public class Mesto_ { 

    public static volatile SingularAttribute<Mesto, Integer> idMesto;
    public static volatile SingularAttribute<Mesto, String> poštanskiBroj;
    public static volatile SingularAttribute<Mesto, String> naziv;
    public static volatile ListAttribute<Mesto, Filijala> filijalaList;
    public static volatile ListAttribute<Mesto, Komitenti> komitentiList;

}