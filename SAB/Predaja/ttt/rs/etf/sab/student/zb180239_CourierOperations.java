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
import rs.etf.sab.operations.CourierOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_CourierOperations implements CourierOperations{
    public boolean insertCourier​(@NotNull java.lang.String courierUserName, @NotNull java.lang.String driverLicenceNumber){
        Connection con = DB.getInstance().getConnection();
       
        String query ="insert into WorkingUser(IdWorkingUser) values ((select IdKorisnik from Korisnik where KorisnickoIme = ?));";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, courierUserName);
            ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        
       query ="insert into Kurir(IdKurir,BrojVozacke) values ((select IdKorisnik from Korisnik where KorisnickoIme = ?),?);";
       
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, courierUserName);
           ps.setString(2, driverLicenceNumber);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    
    }
    
    public boolean deleteCourier​(@NotNull java.lang.String courierUserName){
    Connection con = DB.getInstance().getConnection();
            
            String query ="delete from Kurir where IdKorisnik in (select IdKorisnik from Korisnik where KorisnickoIme = ?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, courierUserName);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    
    }
    
    public java.util.List<java.lang.String> getCouriersWithStatus​(int statusOfCourier){
        List<String> stringList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select KorisnickoIme from Korisnik where IdKorisnik in (select IdKurir from Kurir where Status = ?)";
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, statusOfCourier);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   stringList.add(rs.getString(1));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        return stringList;
    
    }
    
    public java.util.List<java.lang.String> getAllCouriers(){
        List<String> stringList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select KorisnickoIme from Korisnik join Kurir on IdKorisnik = IdKurir order by Profit DESC";
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   stringList.add(rs.getString(1));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        return stringList;
    }
    
    public java.math.BigDecimal getAverageCourierProfit​(int numberOfDeliveries){
        Connection con = DB.getInstance().getConnection();
       
       String query;
       if(numberOfDeliveries != -1){
       query ="select count(*),sum(PROFIT) from Kurir where Paketi = ?";
       }
       else{
       query ="select count(*),sum(PROFIT) from Kurir";
       }
       
       int t =0;
       double d =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           if(numberOfDeliveries!=-1)ps.setInt(1, numberOfDeliveries);
          try(ResultSet rs = ps.executeQuery()){
              if(rs.next()){
                  t = rs.getInt(1);
                  d = rs.getDouble(2);
              }
          
          } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return new java.math.BigDecimal(d/(((double)t)));
    }

}
