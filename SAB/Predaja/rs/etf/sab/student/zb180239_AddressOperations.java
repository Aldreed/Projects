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
import rs.etf.sab.operations.AddressOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_AddressOperations implements AddressOperations{
    
    
   public int deleteAddresses​(@NotNull java.lang.String name, int number){
       Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Adresa where Ulica = ? and Broj= ?";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setString(1, name);
           ps.setInt(2, number);
           try{
               t = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return t;
       
    }
    
   public boolean deleteAdress​(int idAddress){
   
       Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Adresa where IdAdresa = ?";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, idAddress);
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
   
   
   public int deleteAllAddressesFromCity​(int idCity){
   Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Adresa where IdGrad = ?";
       
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
       
       return t;
   
   }
   
   public java.util.List<java.lang.Integer> getAllAddresses(){
       List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdAdresa from Adresa";
       
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
   
   public java.util.List<java.lang.Integer> getAllAddressesFromCity​(int idCity){
       List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdAdresa from Adresa where IdGrad = ?";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, idCity);
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
       
   return (intList.size() > 0)?intList:null;
   
   
   }
   
   public int insertAddress​(@NotNull java.lang.String street, int number, int cityId, int xCord, int yCord){
   
       Connection con = DB.getInstance().getConnection();
       
       String query ="insert into Adresa(Ulica,Broj,IDGrad,Xcord,YCord) values (?,?,?,?,?);";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setString(1, street);
           ps.setInt(2, number);
           ps.setInt(3, cityId);
           ps.setInt(4, xCord);
           ps.setInt(5, yCord);
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
   
}
