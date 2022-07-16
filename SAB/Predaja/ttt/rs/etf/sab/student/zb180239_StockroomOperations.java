/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.StockroomOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_StockroomOperations implements StockroomOperations{
       public int insertStockroom​(int address){
           Connection con = DB.getInstance().getConnection();
       
       String queryCheck ="select IdAdresa from LokacijaMagacina where IdAdresa in (select IdAdresa from Adresa where IdGrad in (select IdGrad from Adresa where IdAdresa = ?))";
       String query ="insert into LokacijaMagacina(IdAdresa) values (?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(queryCheck)){
           ps.setInt(1, address);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   return -1;
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setInt(1, address);
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
       
       public boolean deleteStockroom​(int idStockroom){
            Connection con = DB.getInstance().getConnection();
       
       String query ="delete from LokacijaMagacina where IdMag = ?";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, idStockroom);
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
       
       public int deleteStockroomFromCity​(int idCity){
             Connection con = DB.getInstance().getConnection();
       
       String querySelect ="select IdMag from LokacijaMagacina where IdAdresa in (select IdAdresa from Adresa where IdGrad=?)";
       String queryDelete ="delete from LokacijaMagacina where IdMag = ? ";
       
       int t =-1;
       int temp = -1;
       try (PreparedStatement ps = con.prepareStatement(querySelect)){
           ps.setInt(1, idCity);
           try(ResultSet rs = ps.executeQuery()){
               if(rs.next()){
                   temp = rs.getInt(1);
               }
               else{
                   return -1;
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       try (PreparedStatement ps = con.prepareStatement(queryDelete)){
           ps.setInt(1, temp);
           try{
               t = ps.executeUpdate();
               if(t==0){
                   return -1;
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return temp;
       
       }
       
       public java.util.List<java.lang.Integer> getAllStockrooms(){
           List<Integer> intList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select IdMag from LokacijaMagacina";
       
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
