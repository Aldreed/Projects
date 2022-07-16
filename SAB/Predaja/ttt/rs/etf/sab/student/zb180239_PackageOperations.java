/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import com.sun.istack.internal.NotNull;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_PackageOperations implements PackageOperations{
    public int insertPackage​(int addressFrom, int addressTo, @NotNull java.lang.String userName, int packageType, java.math.BigDecimal weight){
        Connection con = DB.getInstance().getConnection();
       
       String query ="insert into Paket(ToAdresa,FromAdresa,TipPaketa,TezinaPaketa,VremeKreiranjaZahteva,Cena,IdKorisnik) values (?,?,?,?,getDate(),?,(select IdKorisnik from Korisnik where KorisnickoIme = ?))";
       
       int t =0;
       double Cena=0;
       java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
       
       
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setInt(1, addressTo);
           ps.setInt(2, addressFrom);
           ps.setInt(3, packageType);
           ps.setDouble(4, weight.doubleValue());
           ps.setDouble(5, Cena);
           ps.setString(6, userName);
           t = ps.executeUpdate();
           if (t>0){
           try(ResultSet rs = ps.getGeneratedKeys()){
              if(rs.next()){
                  t = rs.getInt(1);
              }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
           }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?-1:t;
    
    }

    public boolean acceptAnOffer​(int packageId){
        Connection con = DB.getInstance().getConnection();
       
       String query ="update Paket set StatusPaketa = 1, VremePrihvatanjaPonude = GETDATE() where IdPaket = ? and StatusPaketa = 0";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           t = ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       return (t==0)?false:true;
    }
    
    public boolean rejectAnOffer​(int packageId){
            Connection con = DB.getInstance().getConnection();
       
       String query ="update Paket set StatusPaketa = 4 where IdPaket = ? and StatusPaketa = 0";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           t = ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       return (t==0)?false:true;
    
    }
    
    public java.util.List<java.lang.Integer> getAllPackages(){
        List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdPaket from Paket";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   return intList;
    
    }
    
    public java.util.List<java.lang.Integer> getAllPackagesWithSpecificType​(int type){
        List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdPaket from Paket where TipPaketa = ?";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, type);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   return intList;
    
    }
    
    public java.util.List<java.lang.Integer> getAllUndeliveredPackagesFromCity​(int cityId){
         List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdPaket from Paket where StatusPaketa in (1,2) and FromAdresa in (select IdAdresa from Adresa where IdGrad = ?)";
       
       int t =0;
       // NEISPORUCENI
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, cityId);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }

//       // U Magacinu
//       query ="select IdPaket from Paket where IdPaket in (select IdPaket from UMagacinu where IDMag in(select IDMag from LokacijaMagacina where IdAdresa in (select IdAdresa from Adresa where IdGrad = ?)))";
//       
//       
//        try (PreparedStatement ps = con.prepareStatement(query)){
//           ps.setInt(1, cityId);
//           try(ResultSet rs = ps.executeQuery()){
//               while(rs.next()){
//                   intList.add(new Integer(rs.getInt(1)));
//               }
//            } catch (SQLException ex) {
//                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
//            }
//       } catch (SQLException ex) {
//           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
//       }
        return intList;
    
    }
    
    public java.util.List<java.lang.Integer> getAllUndeliveredPackages(){
        List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdPaket from Paket where StatusPaketa in (1,2)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        return intList;
    
    }
    
    public java.util.List<java.lang.Integer> getAllPackagesCurrentlyAtCity​(int cityId){
        List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdPaket from Paket where StatusPaketa = 1 and FromAdresa in (select IdAdresa from Adresa where IdGrad = ?)";
       
       int t =0;
       // NEISPORUCENI
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, cityId);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       // ISPORUCENI
       query ="select IdPaket from Paket where StatusPaketa = 3 and ToAdresa in (select IdAdresa from Adresa where IdGrad = ?)";
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, cityId);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       // U Magacinu
       query ="select IdPaket from Paket where IdPaket in (select IdPaket from UMagacinu where IDMag in(select IDMag from LokacijaMagacina where IdAdresa in (select IdAdresa from Adresa where IdGrad = ?)))";
       
       
        try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, cityId);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   intList.add(new Integer(rs.getInt(1)));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
        return intList;
    
    }

    public boolean deletePackage​(int packageId){
        Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Paket where StatusPaketa in (0,4) and IdPaket = ?";
       int t =0;
       double Cena=0;
       Date d = new Date(System.currentTimeMillis());
       
       
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setInt(1, packageId);
           t = ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    
    }
    
    //TODO
    public boolean changeWeight​(int packageId, @NotNull java.math.BigDecimal newWeight){
        Connection con = DB.getInstance().getConnection();
       
       String query ="update Paket set TezinaPaketa = ?  where IdPaket = ? and StatusPaketa = 0";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(2, packageId);
           ps.setDouble(1, newWeight.doubleValue());
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       return (t==0)?false:calcPrice(packageId);
    
    }
    
    public boolean changeType​(int packageId, int newType){
         Connection con = DB.getInstance().getConnection();
       
       String query ="update Paket set TipPaketa = ?  where IdPaket = ? and StatusPaketa = 0";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(2, packageId);
           ps.setInt(1, newType);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       return (t==0)?false:calcPrice(packageId);
    
    }

    private boolean calcPrice(int packageId){
        Connection con = DB.getInstance().getConnection();
       
       String query ="select XCord,YCord from Adresa where IdAdresa in (select FromAdresa from Paket where IdPaket = ?)";
       int t = 0;
       double cena = -1;
       int xStart=0;
       int yStart=0;
       int xEnd=0;
       int yEnd=0;
       double weight=-1;
       int tip=-1;
       
       // Start Cords
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   xStart = rs.getInt(1);
                   yStart = rs.getInt(2);
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
       
       // END CORDS
       
       query ="select XCord,YCord from Adresa where IdAdresa in (select ToAdresa from Paket where IdPaket = ?)";
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   xEnd = rs.getInt(1);
                   yEnd = rs.getInt(2);
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
       
       // WEIGHT AND TYPE
       query ="select TezinaPaketa,TipPaketa from Paket where IdPaket = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   weight = rs.getDouble(1);
                   tip = rs.getInt(2);
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
       
       int startPrice = -1;
       int kgPrice = -1;
       
       switch(tip){
           case 0:
               startPrice = 115;
               kgPrice = 0;
            break;
            case 1:
               startPrice = 175;
               kgPrice = 100;
            break;
            case 2:
               startPrice = 250;
               kgPrice = 100;
            break;
            case 3:
               startPrice = 350;
               kgPrice = 500;
            break;
       }
       
       cena = +Math.sqrt(Math.pow((xStart-xEnd), 2)+Math.pow((yStart-yEnd), 2))*(kgPrice*weight + startPrice);
       
       
       query ="update Paket set Cena = ? where IdPaket = ?";
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(2, packageId);
           ps.setDouble(1, cena);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return (t==0)?false:true;
    
    };
    
    
    public int getDeliveryStatus​(int packageId){
        Connection con = DB.getInstance().getConnection();
       
       String query ="select StatusPaketa from Paket where IdPaket = ?";
       
       int t =-1;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
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
    
    public java.math.BigDecimal getPriceOfDelivery​(int packageId){
        Connection con = DB.getInstance().getConnection();
       
       String query ="select Cena from Paket where IdPaket = ?";
       
       double t =-1;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   t = rs.getDouble(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
    return new java.math.BigDecimal(t);
    }
//    TODO
    public int getCurrentLocationOfPackage​(int packageId){
         Connection con = DB.getInstance().getConnection();
       
       String query ="select IdGrad from Grad where IdGrad in (select IdGrad from Adresa where IdGrad = Grad.IdGrad and IdAdresa in (select FromAdresa from Paket where IdPaket = ? and StatusPaketa in (1,0)))";
       
       int t =-1;
       // NEISPORUCENI
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   return rs.getInt(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    
       // ISPORUCENI
       query ="select IdGrad from Grad where IdGrad in (select IdGrad from Adresa where IdGrad = Grad.IdGrad and IdAdresa in (select ToAdresa from Paket where IdPaket = ? and StatusPaketa =  3))";
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   return rs.getInt(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       // U Magacinu
       query ="select IdGrad from Grad where IdGrad in (select Adresa.IdGrad from Adresa where Adresa.IdGrad = Grad.IdGrad and Adresa.IdAdresa in (select LokacijaMagacina.IdAdresa from LokacijaMagacina where LokacijaMagacina.IdAdresa = Adresa.IdAdresa and LokacijaMagacina.IdMag in (select UMagacinu.IdMag from UMagacinu where IdPaket = ?)))";
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   return rs.getInt(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       return t;
    }
    
    public java.sql.Date getAcceptanceTime​(int packageId){
        Connection con = DB.getInstance().getConnection();
       
       String query ="select VremePrihvatanjaPonude from Paket where IdPaket = ?";
       
       java.sql.Date t = null;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, packageId);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   t = rs.getDate(1);
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        return t;
    }
    
}
