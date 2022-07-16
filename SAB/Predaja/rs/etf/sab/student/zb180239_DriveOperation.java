/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import com.sun.istack.internal.NotNull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.DriveOperation;

/**
 *
 * @author Bogdan
 */
class zb180239_DriveOperation implements DriveOperation{
    public boolean planingDrive​(java.lang.String courierUsername){
    
        
    Connection con = DB.getInstance().getConnection();
    
    int PocetanIdVozila = -1;
    double PocetnaNosivost = 0;
    int PocetanIdMag = -1;
    int PocetanGrad = -1;
    int NeedThisX = -1;
    int NeedThisY = -1;
    int Voznja = -1;
    
    //Dohvati IdVozilo IdMag IdGrad X Y za pocetnu lokaciju
    String query ="Select top (1) v.IdVozilo,v.Nosivost,p.IdMag,a.IdGrad,a.XCord,a.YCord from Vozilo v join Parkiran p on v.IdVozilo = p.IdVozilo \n" +
                  "join LokacijaMagacina lm on p.IdMag = lm.IdMag \n" +
                  "join Adresa a on a.IdAdresa = lm.IdAdresa";
    
    try (PreparedStatement ps = con.prepareStatement(query)){
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   PocetanIdVozila = rs.getInt(1);
                   PocetnaNosivost = rs.getDouble(2);
                   PocetanIdMag = rs.getInt(3);
                   PocetanGrad = rs.getInt(4);
                   NeedThisX = rs.getInt(5);
                   NeedThisY = rs.getInt(6);
               }
               else{
                   return false;
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    //Napravi novu voznju
    String query2 ="insert into Voznja(IdKurir,PocetniGrad,IdVozilo,Stavka,XCord,YCord) values ((select IdKorisnik from Korisnik where KorisnickoIme = ? and IdKorisnik in (select IdKurir from Kurir where Status = 0)),?,?,1,?,?)";
    
    try (PreparedStatement ps = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, courierUsername);
            ps.setInt(2, PocetanGrad);
            ps.setInt(3, PocetanIdVozila);
            ps.setInt(4, NeedThisX);
            ps.setInt(5, NeedThisY);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
              if(rs.next()){
                  Voznja = rs.getInt(1);
              }
              else{
                  return false;
              }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    
    //Pokupi Vozilo
    String query3 ="delete from Parkiran where IdVozilo = ?";
    
    try (PreparedStatement ps = con.prepareStatement(query3)){
           ps.setInt(1, PocetanIdVozila);
           if(ps.executeUpdate()==0)
           {
               return false;
           }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    //Zaposli kurira
    String query4 ="update Kurir set Status = 1, IdVozilo = ? where IdKurir = (select IdKorisnik from Korisnik where KorisnickoIme = ?)";
    
    try (PreparedStatement ps = con.prepareStatement(query4)){
           ps.setInt(1, PocetanIdVozila);
           ps.setString(2, courierUsername);
           if(ps.executeUpdate()==0)
           {
               return false;
           }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    //Pokupi pakete iz Grada
    
       List<Paket> PaketiZaIsporuku = pickUp(Voznja, PocetanGrad, PocetnaNosivost,1);
       int BrojStavki = getStavka(Voznja) + 1;
       
       //Set Start X Y
       int startX =-1;
       int startY = -1;
       if (PaketiZaIsporuku.size() > 0){
           startX =  PaketiZaIsporuku.get(PaketiZaIsporuku.size() - 1).ToXCord;
           startY =  PaketiZaIsporuku.get(PaketiZaIsporuku.size() - 1).ToYCord;
       }
       else {
           return false;
       }
       
       //Azuriraj lokalnu nosivost
       for (Paket paket : PaketiZaIsporuku) {
           PocetnaNosivost -=paket.Tezina;
       }
    
       //Azuriraj X i Y za svaki paket tako da pokazuju na ToAdresa
       PaketiZaIsporuku = changeListToUpdate(PaketiZaIsporuku);
       
       //Poslednji Paket ce da se ubaci kao idMagacina
       int idNearest = -1;
//       DRUGI DEO
       //Isporuci Svaki Paket
       while(!PaketiZaIsporuku.isEmpty()){
       //Nadji sledeci najblizi Paket    
       idNearest = getNearestDeliveryPointForList(startX, startY, PaketiZaIsporuku);
       Paket Nearest = null;

       //Izaberi Grad Tog Paketa i Isporuci sve pakete u njemu
       int CurrentGrad = getIDCityForDeliveryAdress(idNearest);
       List<Paket> CityBatch = createBatchCity(CurrentGrad, PaketiZaIsporuku);
       
       //       Izdvoji kao funk koja vraca paket
        for (int i = 0; i < PaketiZaIsporuku.size(); i++) {
            Paket paket = PaketiZaIsporuku.get(i);
            if(paket.Id == idNearest){
                Nearest = paket;
                PaketiZaIsporuku.remove(i);
                break;
            }
        }
       
        while (!CityBatch.isEmpty()) {            
            idNearest = getNearestDeliveryPointForList(startX, startY, CityBatch);
            
            PocetnaNosivost += Nearest.Tezina;
            
            for (int i = 0; i < CityBatch.size(); i++) {
            Paket paket = CityBatch.get(i);
            if(paket.Id == idNearest){
                Nearest = paket;
                CityBatch.remove(i);
                break;
                }
            }
            
            updatePlan(Voznja, idNearest, Nearest.ToXCord, Nearest.ToYCord, BrojStavki++, 2);
            startX = Nearest.ToXCord;
            startY = Nearest.ToYCord;
            
            if(CityBatch.isEmpty()){
                break;
            }
        }
        //Pokupi nove pakete
        List<Paket> newlyPickedUp = pickUp(Voznja, CurrentGrad, PocetnaNosivost, BrojStavki);
        if(!newlyPickedUp.isEmpty())BrojStavki = getStavka(Voznja) + 1;
        
        //Namesti nove kordinate za sledeci ciklus
        if (newlyPickedUp.size() > 0){
           startX =  newlyPickedUp.get(newlyPickedUp.size() - 1).ToXCord;
           startY =  newlyPickedUp.get(newlyPickedUp.size() - 1).ToYCord;
           for (Paket paket : newlyPickedUp) {
            PocetnaNosivost -=paket.Tezina;
        }
       }
        
      }
       
        //Dodaj poslednju stavku ***PROVERI DA LI OVO ZAISTA STAVLJA NA NULL IdPaketa***
        if(idNearest !=  -1){
            updatePlan(Voznja, idNearest, NeedThisX, NeedThisY, BrojStavki++, 3);
        }
        else{
            return false;
        }
        
       
   //UPDATE Voznja set startX startY
//   String queryLast ="update Voznja set XCord = ?, YCord = ?,Stavka = 1 where IdVoznja = ?";
//    
//    try (PreparedStatement ps = con.prepareStatement(queryLast)){
//            ps.setInt(1, NeedThisX);
//            ps.setInt(2, NeedThisY);
//            ps.setInt(3, Voznja);
//            try(ResultSet rs = ps.getGeneratedKeys()){
//              if(rs.next()){
//                  Voznja = rs.getInt(1);
//              }
//            } catch (SQLException ex) {
//                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//       } catch (SQLException ex) {
//           Logger.getLogger(AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
//       }
//        
        
        return true;
    }
    
    private int getStavka(int IdVoznja){
    
        String query = "select max(Stavka) from PlanVoznje where IdVoznja = ? ";
         Connection con = DB.getInstance().getConnection();
         int ret = 0;
         
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ret = rs.getInt(1);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
        return ret;
        
    }
    
    private List<Paket> pickUp(int IdVoznja, int IdGrad, double PocetnaNosivost, int i){
        List<Paket> PaketCity = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
//       IZ GRADA 
       String query ="select IdPaket,TezinaPaketa,XCord,YCord from Paket join Adresa on IdAdresa = FromAdresa \n" +
                "where StatusPaketa = 1 and DeoPlana is NULL and FromAdresa in (select IdAdresa from Adresa where IdGrad = ?) \n" +
                "order By VremePrihvatanjaPonude asc ";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, IdGrad);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   PaketCity.add(new Paket(rs.getInt(1),rs.getDouble(2),rs.getInt(3),rs.getInt(4)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        for (Paket paket : PaketCity) {
            if((PocetnaNosivost - paket.Tezina) >0){
                updatePlan(IdVoznja, paket.Id, paket.ToXCord, paket.ToYCord,i++,0);
                PocetnaNosivost = PocetnaNosivost - paket.Tezina;
            }
            else{
                break;
            }
        }
//       IZ MAGACINA
        if(PocetnaNosivost == 0){
            return PaketCity;
        }
        else{
            List<Paket> PaketMagacin = new LinkedList<>();
            String queryMagacinPickup ="select Paket.IdPaket,Paket.TezinaPaketa,Adresa.XCord,Adresa.YCord from Paket join UMagacinu on Paket.IdPaket = UMagacinu.IdPaket \n" +
                    "join LokacijaMagacina on LokacijaMagacina.IdMag = UMagacinu.IdMag \n" +
                    "join Adresa on LokacijaMagacina.IdAdresa = Adresa.IdAdresa\n" +
                    "where Paket.StatusPaketa = 2 and DeoPlana is NULL and Adresa.IdGrad = ? \n" +
                    "order By VremePrihvatanjaPonude asc";
       
            try (PreparedStatement ps = con.prepareStatement(queryMagacinPickup)){
                ps.setInt(1, IdGrad);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        PaketMagacin.add(new Paket(rs.getInt(1),rs.getDouble(2),rs.getInt(3),rs.getInt(4)));
                    }
                } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            boolean ping = false;
       
            for (Paket paket : PaketMagacin) {
                if((PocetnaNosivost - paket.Tezina) >0){
                    ping = true;
                    PocetnaNosivost = PocetnaNosivost - paket.Tezina;
                }
                else{
                    break;
                }
            }
            
            if(ping && PaketMagacin.size()>0){
                updatePlan(IdVoznja, PaketMagacin.get(0).Id, PaketMagacin.get(0).ToXCord, PaketMagacin.get(0).ToYCord,i++,1);
                for (Paket paket : PaketMagacin) {
                    postaniDeoPlana(IdVoznja, paket.Id);
                }
            }
            PaketCity.addAll(PaketMagacin);
        }
        
        return PaketCity;
    }
    
    private boolean updatePlan(int IdVoznja,int IdPaket,int XCord,int YCord,int Stavka, int Status){
        Connection con = DB.getInstance().getConnection();
        String query2 ="insert into PlanVoznje(IdVoznja,IdPaket,XCord,YCord,Stavka,Tip) values (?,?,?,?,?,?)";
        int t = 0;
        
        try (PreparedStatement ps = con.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, IdVoznja);
            ps.setInt(2, IdPaket);
            ps.setInt(3, XCord);
            ps.setInt(4, YCord);
            ps.setInt(5, Stavka);
            ps.setInt(6, Status);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
              if(rs.next()){
                  t = rs.getInt(1);
              }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
        postaniDeoPlana(IdVoznja, IdPaket);
        
        return (t==0)?false:true;
    }
    
    private void postaniDeoPlana(int IdVoznja, int IdPaket){
        
        Connection con = DB.getInstance().getConnection();
        
        String query ="update Paket set DeoPlana = ? where IdPaket = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ps.setInt(2, IdPaket);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    }
    
    private int getNearestDeliveryPointForList(int X, int Y, List<Paket> list){
        int t = -1;
        
        Connection con = DB.getInstance().getConnection();
        
        String query ="select top(1) IdPaket,(SQRT(SQUARE(? - XCord) + SQUARE(? - YCord))) as Distance from Adresa right join Paket on IdAdresa = ToAdresa\n" +
                        "where IdPaket in (";
        boolean ping = false;
        for (Paket paket : list) {
            query = query + paket.Id + ",";
           }
           query =  query + "-1 )";
           query = query +" order by Distance";
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, X);
            ps.setInt(2, Y);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   t = rs.getInt(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
        return t;
    }
    
    private List<Paket> changeListToUpdate(List<Paket> list){
        Connection con = DB.getInstance().getConnection();
        
        for (int i = 0; i < list.size(); i++) {
            
            String query ="Select XCord,YCord,IdGrad from Adresa join Paket on IdAdresa = ToAdresa where IdPaket = ?";
       
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, list.get(i).Id);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                    list.get(i).ToXCord = rs.getInt(1);
                    list.get(i).ToYCord = rs.getInt(2);
                    list.get(i).IdGrad = rs.getInt(3);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }     
      }
        
        return list;
    } 
                
    private int getIDCityForDeliveryAdress(int id){
        int t = -1;
        
        Connection con = DB.getInstance().getConnection();
        
        String query ="select Adresa.IdGrad from Grad join Adresa on Adresa.IdGrad = Grad.IdGrad join Paket on Paket.ToAdresa = Adresa.IdAdresa where IdPaket = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, id);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   t = rs.getInt(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
        return t;
    
    }
    
    private List<Paket> createBatchCity(int idGrad,List<Paket> list){
            List<Paket> newList = new LinkedList<>();
            
            for (Paket paket : list) {
             if(paket.IdGrad == idGrad){
                 newList.add(paket);
             }
            }
    
            return newList;
    }
    
      
    

    public int nextStop​(@NotNull java.lang.String courierUsername){
        int t = -2;
        int Stavka = -1;
        int IdVoznje  = -1;
        
        int XCord = -1;
        int YCord = -1;
        int Tip = -1;
        int IdPaket = -1;
        
        
        Connection con = DB.getInstance().getConnection();
        
        
        String query ="select IdVoznja,Stavka from Voznja where IdKurir in (select IdKorisnik from Korisnik where KorisnickoIme = ?)";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, courierUsername);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   IdVoznje = rs.getInt(1);
                   Stavka = rs.getInt(2);
               }
               else{
                   return -2;
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        

        query ="select XCord, YCord, Tip, IdPaket from PlanVoznje where Stavka = ? and IdVoznja = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, Stavka);
            ps.setInt(2, IdVoznje);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   XCord = rs.getInt(1);
                   YCord = rs.getInt(2);
                   Tip = rs.getInt(3);
                   IdPaket = rs.getInt(4);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        switch(Tip){
            case 0:
                pokupiPaket(IdVoznje,IdPaket);
                updateVoznja(IdVoznje,++Stavka);
                return -2;
            case 1:
                pokupiPaketIzMagacina(IdVoznje,IdPaket);
                updateVoznja(IdVoznje,++Stavka);
                return -2;
            case 2:
                isporuciPaket(IdVoznje,IdPaket);
                updateVoznja(IdVoznje,++Stavka);
                return IdPaket;
            case 3:
                zavrsi(IdVoznje);      
                return -1;        
        }
        
        
        return t;
        
    
    }
    
    private void updateVoznja(int IdVoznja,int Stavka){
        
        Connection con = DB.getInstance().getConnection();
        
         String query ="update Voznja set Stavka = ? where IdVoznja = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, Stavka);
            ps.setInt(2, IdVoznja);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    }
    
    
    private void pokupiPaket(int IdVoznje,int IdPaket){
    
           Connection con = DB.getInstance().getConnection();
        
         String query ="update Paket set StatusPaketa = ? where IdPaket = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, 2);
            ps.setInt(2, IdPaket);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
        query ="insert into Pokupljeno(IdVoznja,IdPaket) values (?,?)";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznje);
            ps.setInt(2, IdPaket);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    };
    
    private void pokupiPaketIzMagacina(int IdVoznje,int IdPaket){
        
         
        List<Paket> paketi = dohvatiListuPaketaIzMagacina(IdVoznje, IdPaket);
        
        for (Paket paket : paketi) {
            pokupiPaket(IdVoznje, paket.Id);
        }
        
        
    };
    
    private List<Paket> dohvatiListuPaketaIzMagacina(int IdVoznje, int IdPaket){
        List<Paket> paketi = new LinkedList<>();
        Connection con = DB.getInstance().getConnection();
        
         int IdMag = -1;
         
         String query ="select IdMag from UMagacinu where IdPaket = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdPaket);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                IdMag = rs.getInt(1);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        query = "select IdPaket from Paket where DeoPlana = ? and IdPaket in (select IdPaket from UMagacinu where IdMag = ?)";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznje);
            ps.setInt(2, IdMag);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                paketi.add(new Paket(rs.getInt(1),-1, -1, -1));
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        for (Paket paket : paketi) {
            
            
        query = "delete from UMagacinu where IdPaket = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, paket.Id);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
            
        }
        
        return paketi;
    }
    
    private void isporuciPaket(int IdVoznja,int IdPaket){
        Connection con = DB.getInstance().getConnection();
        
         String query ="delete from Pokupljeno where IdPaket = ? and IdVoznja = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdPaket);
            ps.setInt(2, IdVoznja);
            
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
        query ="update Paket set StatusPaketa = 3 where IdPaket = ?";
        
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdPaket);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
    
    }
    
    private void zavrsi(int IdVoznja){
        List<Stavka> stavke = new LinkedList<>();
        Connection con = DB.getInstance().getConnection();
        
        int StartX = -1;
        int StartY = -1;
        
        
        
        int PocetniMag = -1;
        
        String query = "select IdMag from LokacijaMagacina where IdAdresa in (select IdAdresa from Adresa where IdGrad in (select PocetniGrad from Voznja where IdVoznja = ?))";
        
            try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                PocetniMag = rs.getInt(1);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
                
                
        query = "select XCord,YCord from Adresa where IdAdresa in (select IdAdresa from LokacijaMagacina where IdMag = ?)";
              
              try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, PocetniMag);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                StartX = rs.getInt(1);
                StartY = rs.getInt(2);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        
        query = "select IdPaket,Tip,XCord,YCord from PlanVoznje where IdVoznja = ? order by Stavka asc";

        int IdPaket = -1;
        int Tip = -1;
        int tempX = -1;
        int tempY = -1;
        double rastojanje = 0;
        double cena = 0;
        int count = 0;
        
        try (PreparedStatement ps = con.prepareStatement(query)){
         
            ps.setInt(1, IdVoznja);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                IdPaket = rs.getInt(1);
                Tip = rs.getInt(2);
                tempX = rs.getInt(3);
                tempY = rs.getInt(4);

                rastojanje += Math.sqrt(Math.pow((StartX-tempX), 2)+Math.pow((StartY-tempY), 2));
                
                StartX = tempX;
                StartY = tempY;
                
                if(Tip == 2){
                    count++;
                    cena += getCena(IdPaket);
                }

            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
       query = "select IdVozilo,IdKurir from Voznja where IdVoznja = ?";
       int IdVozilo = -1;
       int IdKurir = -1;
       
           try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                IdVozilo = rs.getInt(1);
                IdKurir = rs.getInt(2);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        query = "select TipGoriva,Potrosnja from Vozilo where IdVozilo = ?";
        int TipGoriva = -1;
        double Potrosnja = 0;
        
         try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVozilo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                TipGoriva = rs.getInt(1);
                Potrosnja = rs.getDouble(2);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
         
       int CenaGoriva = 0;
       switch(TipGoriva){
           case 0:
               CenaGoriva = 15;
               break;
            case 1:
               CenaGoriva = 32;
               break;
            case 2:
               CenaGoriva = 36;
               break;
       }
        
       double profit = cena-(rastojanje*Potrosnja)*CenaGoriva;
       
       double oldProfit = -1;
       int oldCount = 0;
       
       query = "select Profit,Paketi from Kurir where IdKurir = ?";
       
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdKurir);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                oldProfit = rs.getDouble(1);
                oldCount = rs.getInt(2);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       query = "update Kurir set Profit = ?,Paketi = ?, Status = 0, IdVozilo = NULL where IdKurir = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setDouble(1, oldProfit + profit);
            ps.setInt(2, oldCount + count);
            ps.setInt(3, IdKurir);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       query = "insert into Parkiran(IdMag,IdVozilo) values(?,?)";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, PocetniMag);
            ps.setInt(2, IdVozilo);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       query = "select IdPaket from Pokupljeno where IdVoznja = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    query = "insert into UMagacinu(IdMag,IdPaket) values(?,?)";
                    try (PreparedStatement ps1 = con.prepareStatement(query)){
                        ps1.setInt(1, PocetniMag);
                        ps1.setInt(2, rs.getInt(1));
                        ps1.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       
       query = "update Paket set DeoPlana = NULL where DeoPlana = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       query = "delete from PlanVoznje where IdVoznja = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       query = "delete from Pokupljeno where IdVoznja = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       query = "delete from Voznja where IdVoznja = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdVoznja);
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    private double getCena(int IdPaket){
        double cena = -1;
        
        Connection con = DB.getInstance().getConnection();
        String query = "select Cena from Paket where IdPaket = ?";
   
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, IdPaket);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cena = rs.getDouble(1);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
        return cena;
    
    }
    
    public List<Integer> getPackagesInVehicle(String string){
        List<Integer> intList = new LinkedList<>();
         Connection con = DB.getInstance().getConnection();
        String query = "select IdPaket from Pokupljeno where IdVoznja in (select IdVoznja from Voznja where IdKurir in (select IdKorisnik from Korisnik where KorisnickoIme = ?))";
   
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, string);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                intList.add(rs.getInt(1));
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
        return intList;
    };
   
}
