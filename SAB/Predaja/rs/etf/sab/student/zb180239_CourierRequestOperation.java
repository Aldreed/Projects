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
import rs.etf.sab.operations.CourierRequestOperation;

/**
 *
 * @author Bogdan
 */
class zb180239_CourierRequestOperation implements CourierRequestOperation{
        public boolean insertCourierRequest​(@NotNull java.lang.String userName, @NotNull java.lang.String driverLicenceNumber){
              Connection con = DB.getInstance().getConnection();
       
       String query ="select IdKurir from Kurir where IdKurir = (select IdKorisnik from Korisnik where KorisnickoIme = ?)";    
              
               try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, userName);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   return false;
               }
           }
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
               
       query ="insert into Zahtev(IdKorisnik,BrojVozacke) values ((select IdKorisnik from Korisnik where KorisnickoIme = ?),?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, userName);
           ps.setString(2, driverLicenceNumber);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
        }
        
        public boolean deleteCourierRequest​(java.lang.String userName){
            Connection con = DB.getInstance().getConnection();
            
            String query ="delete from Zahtev where IdKorisnik in (select IdKorisnik from Korisnik where KorisnickoIme = ?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, userName);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
        }
        
        public java.util.List<java.lang.String> getAllCourierRequests(){
            List<String> stringList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select KorisnickoIme from Korisnik where IdKorisnik in (select IdKorisnik from Zahtev)";
       
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
        
        public boolean grantRequest​(@NotNull java.lang.String username){
            Connection con = DB.getInstance().getConnection();
       
       String queryIdKurir ="insert into Kurir(IdKurir,Status,BrojVozacke) values (?,0,(select BrojVozacke from Zahtev where IdKorisnik = ?));";
       String queryIdWorkingUser ="insert into WorkingUser(IdWorkingUser) values (?);";
       String queryIdKor ="select IdKorisnik from Korisnik where KorisnickoIme = ?";
       
       
       int t =0;
       
       int id =0;
            
        
        //moze bolje->spoji ovo sa poslednjim uputom
        try (PreparedStatement ps = con.prepareStatement(queryIdKor)){
           ps.setString(1, username);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                  id = rs.getInt(1);
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
       
        
        try (PreparedStatement ps = con.prepareStatement(queryIdWorkingUser)){
           ps.setInt(1, id);
           t = ps.executeUpdate();
           if(t==0){
               return false;
           }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       try (PreparedStatement ps = con.prepareStatement(queryIdKurir)){
           ps.setInt(1, id);
           ps.setInt(2, id);
           t = ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
            deleteCourierRequest(username);
       
       return (t==0)?false:true;
        
        }
        
        public boolean changeDriverLicenceNumberInCourierRequest​(@NotNull java.lang.String userName, @NotNull java.lang.String licencePlateNumber){
            Connection con = DB.getInstance().getConnection();
       
       String query ="update Zahtev set BrojVozacke = ?  where IdKorisnik in (select IdKorisnik from Korisnik where KorisnickoIme = ?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(2, userName);
           ps.setString(1, licencePlateNumber);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
        }
    }
