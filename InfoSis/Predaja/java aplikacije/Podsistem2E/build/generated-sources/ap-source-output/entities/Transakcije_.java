package entities;

import entities.Racuni;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-06T09:05:35")
@StaticMetamodel(Transakcije.class)
public class Transakcije_ { 

    public static volatile SingularAttribute<Transakcije, Date> datum;
    public static volatile SingularAttribute<Transakcije, Integer> iznos;
    public static volatile SingularAttribute<Transakcije, String> svrha;
    public static volatile SingularAttribute<Transakcije, Integer> idtransakcije;
    public static volatile SingularAttribute<Transakcije, Integer> idFilijala;
    public static volatile SingularAttribute<Transakcije, Racuni> racun;
    public static volatile SingularAttribute<Transakcije, Integer> tip;
    public static volatile SingularAttribute<Transakcije, Integer> brojStavke;

}