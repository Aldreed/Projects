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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rs.etf.sab.operations.UserOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_UserOperations implements UserOperations {
    

public boolean insertUser​(@NotNull java.lang.String userName, @NotNull java.lang.String firstName, @NotNull java.lang.String lastName, @NotNull java.lang.String password, @NotNull int idAddress){
         Connection con = DB.getInstance().getConnection();
       
       String query ="insert into Korisnik(Ime,Prezime,KorisnickoIme,Sifra,IdAdresa) values (?,?,?,?,?)";
       String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%_`~]).{8,}";
       String regexVelikoSlovo = "^[A-Z].*$";
       boolean validPassword = PasswordCheck(password,regex);
       boolean validIme = PasswordCheck(firstName, regexVelikoSlovo);
       boolean validPrezime = PasswordCheck(lastName, regexVelikoSlovo);
       
       if(!(validPassword & validIme & validPrezime)){
           return false;
       }
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setString(1, firstName);
           ps.setString(2, lastName);
           ps.setString(3, userName);
           ps.setString(4, password);
           ps.setInt(5, idAddress);
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
       
       return (t==0)?false:true;
    
    
    }

public int deleteUsers​(@NotNull java.lang.String... userNames){

     int t =0;
        
        for (String name : userNames) {
            Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Korisnik where KorisnickoIme = ?";   
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, name);
           try{
               t += ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
        return t;
}

public java.util.List<java.lang.String> getAllUsers(){
    List<String> stringList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select KorisnickoIme from Korisnik";
       
       int t =0;
       
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

public int getSentPackages​(@NotNull java.lang.String... userNames){
        int t =-1;
        
        for (String name : userNames) {
            Connection con = DB.getInstance().getConnection();
        int id =0;
            
        String queryIdKor ="select IdKorisnik from Korisnik where KorisnickoIme = ?";
        
        try (PreparedStatement ps = con.prepareStatement(queryIdKor)){
           ps.setString(1, name);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                  id = rs.getInt(1);
               }
               else{
                   continue;
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
            
            
       String query ="select count (*) from Paket where IdKorisnik = ?";   
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, id);
           try(ResultSet rs = ps.executeQuery()){
              if(rs.next()){
              if(t==-1){
                  t=0;
              }
              t = rs.getInt(1);
              }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
        return t;

}

public boolean declareAdmin​(@NotNull java.lang.String userName){
    
       Connection con = DB.getInstance().getConnection();
       
       String queryIdAdmin ="insert into Admin(IdAdmin) values ((select IdWorkingUser from WorkingUser where IdWorkingUser = ?));";
       String queryIdWorkingUser ="insert into WorkingUser(IdWorkingUser) values (?);";
       
       int t =0;
       
       int id =0;
            
        String queryIdKor ="select IdKorisnik from Korisnik where KorisnickoIme = ?";
        
        try (PreparedStatement ps = con.prepareStatement(queryIdKor)){
           ps.setString(1, userName);
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
//               return false;
           }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       try (PreparedStatement ps = con.prepareStatement(queryIdAdmin)){
           ps.setInt(1, id);
           t = ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;

}

    private static boolean PasswordCheck(String password,String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
