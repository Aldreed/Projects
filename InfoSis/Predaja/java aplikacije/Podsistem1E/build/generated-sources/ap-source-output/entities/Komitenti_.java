package entities;

import entities.Mesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-02T12:55:24")
@StaticMetamodel(Komitenti.class)
public class Komitenti_ { 

    public static volatile SingularAttribute<Komitenti, Integer> idKomitenti;
    public static volatile SingularAttribute<Komitenti, Mesto> sediste;
    public static volatile SingularAttribute<Komitenti, String> naziv;
    public static volatile SingularAttribute<Komitenti, String> adresa;

}