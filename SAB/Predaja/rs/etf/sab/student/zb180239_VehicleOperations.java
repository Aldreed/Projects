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
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author Bogdan
 */
class zb180239_VehicleOperations implements VehicleOperations{
    public boolean insertVehicle​(@NotNull java.lang.String licencePlateNumber, int fuelType, java.math.BigDecimal fuelConsumtion, java.math.BigDecimal capacity){
        Connection con = DB.getInstance().getConnection();
       
       String query ="insert into Vozilo(RegBroj,TipGoriva,Potrosnja,Nosivost) values (?,?,?,?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setString(1, licencePlateNumber);
           ps.setInt(2, fuelType);
           ps.setDouble(3, fuelConsumtion.doubleValue());
           ps.setDouble(4, capacity.doubleValue());
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

    public int deleteVehicles​(@NotNull java.lang.String... licencePlateNumbers){
        int t =0;
        
        for (String name : licencePlateNumbers) {
            Connection con = DB.getInstance().getConnection();
       
       String query ="delete from Vozilo where RegBroj = ?";   
       
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
    
    public java.util.List<java.lang.String> getAllVehichles(){
        List<String> strList = new LinkedList<>();
       
       Connection con = DB.getInstance().getConnection();
       
       String query ="select RegBroj from Vozilo";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   strList.add(rs.getString(1));
               }
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_vezbe.class.getName()).log(Level.SEVERE, null, ex);
            }
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   return strList;
    
    }

    public boolean changeFuelType​(@NotNull java.lang.String licensePlateNumber, int fuelType){
        Connection con = DB.getInstance().getConnection();
       
       String query ="update Vozilo set TipGoriva = ? where IdVozilo in (select Vozilo.IdVozilo from Vozilo join Parkiran on Vozilo.IdVozilo = Parkiran.IdVozilo where RegBroj = ?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setInt(1, fuelType);
           ps.setString(2, licensePlateNumber);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    
    }
    
    public boolean changeConsumption​(@NotNull java.lang.String licensePlateNumber, java.math.BigDecimal fuelConsumption){
         Connection con = DB.getInstance().getConnection();
       
       String query ="update Vozilo set Potrosnja = ? where IdVozilo in (select Vozilo.IdVozilo from Vozilo join Parkiran on Vozilo.IdVozilo = Parkiran.IdVozilo where RegBroj = ?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setDouble(1, fuelConsumption.doubleValue());
           ps.setString(2, licensePlateNumber);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    }
    
    
    public boolean changeCapacity​(@NotNull java.lang.String licensePlateNumber, java.math.BigDecimal capacity){
         Connection con = DB.getInstance().getConnection();
       
       String query ="update Vozilo set Nosivost = ? where IdVozilo in (select Vozilo.IdVozilo from Vozilo join Parkiran on Vozilo.IdVozilo = Parkiran.IdVozilo where RegBroj = ?)";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query)){
           ps.setDouble(1, capacity.doubleValue());
           ps.setString(2, licensePlateNumber);
           t = ps.executeUpdate();
           
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    
    }

    public boolean parkVehicle​(@NotNull java.lang.String licencePlateNumbers, int idStockroom){
         Connection con = DB.getInstance().getConnection();
       
       String query ="insert into Parkiran(IdMag,IdVozilo) values (?,(select IdVozilo from Vozilo where RegBroj = ? and IdVozilo not in (select IdVozilo from Voznja)))";
       
       int t =0;
       
       try (PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
           ps.setString(2, licencePlateNumbers);
           ps.setInt(1, idStockroom);
           t = ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return (t==0)?false:true;
    
    }

}
