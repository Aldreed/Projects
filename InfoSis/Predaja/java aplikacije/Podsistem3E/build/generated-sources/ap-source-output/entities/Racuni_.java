package entities;

import entities.Komitenti;
import entities.Transakcije;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-07-06T09:06:53")
@StaticMetamodel(Racuni.class)
public class Racuni_ { 

    public static volatile SingularAttribute<Racuni, Integer> brojTransakcija;
    public static volatile SingularAttribute<Racuni, Integer> stanje;
    public static volatile SingularAttribute<Racuni, Date> datumOtvaranja;
    public static volatile SingularAttribute<Racuni, Komitenti> komitent;
    public static volatile SingularAttribute<Racuni, Integer> dozvoljeniMinus;
    public static volatile ListAttribute<Racuni, Transakcije> transakcijeList;
    public static volatile SingularAttribute<Racuni, Integer> idracuni;

}