package entities;

import entities.Racuni;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-06T09:05:35")
@StaticMetamodel(Komitenti.class)
public class Komitenti_ { 

    public static volatile SingularAttribute<Komitenti, Integer> idKomitenti;
    public static volatile SingularAttribute<Komitenti, Integer> sediste;
    public static volatile SingularAttribute<Komitenti, String> naziv;
    public static volatile SingularAttribute<Komitenti, String> adresa;
    public static volatile ListAttribute<Komitenti, Racuni> racuniList;

}