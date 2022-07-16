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
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_CityOperations implements CityOperations{
    
    //TODO Da li negde treba unique? Za ime ili kod recimo 
    public int insertCity​(@NotNull java.lang.String name, java.lang.String postalCode){
        Connection con = DB.getInstance().getConnection();
       
       String query ="insert into Grad(Naziv,PostanskiBroj) values (?,?);";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setString(1, name);
           ps.setString(2, postalCode);
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
    
    
    
    public int deleteCity​(@NotNull java.lang.String... names){
        int t =0;
        
        for (String name : names) {
            Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Grad where Naziv = ?";   
       
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
    
    
    public boolean deleteCity​(int idCity){
        
       Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Grad where IdGrad = ?";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, idCity);
           try{
               t = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    }
    
    
    public java.util.List<java.lang.Integer> getAllCities(){
    
         List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdGrad from Grad";
       
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
    
 }
    
