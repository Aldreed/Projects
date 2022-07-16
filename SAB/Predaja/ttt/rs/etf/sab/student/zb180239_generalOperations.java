/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.GeneralOperations;

/**
 *
 * @author Bogdan
 */
public class zb180239_generalOperations implements GeneralOperations {

    
    public void eraseAll() {
        
        Connection con = DB.getInstance().getConnection();
        
        String query = "delete from PlanVoznje;\n" +
"delete from Pokupljeno;\n" +
"delete from Voznja;\n" +
"delete from Parkiran;\n" +
"delete from UMagacinu;\n" +
"update Kurir set IdVozilo = NULL;\n" +
"delete from Vozilo;\n" +
"delete from Paket;\n" +
"delete from Kurir;\n" +
"delete from Admin;\n" +
"delete from Kupac;\n" +
"delete from Zahtev;\n" +
"delete from Korisnik;\n" +
"delete from LokacijaMagacina;\n" +
"delete from Adresa;\n" +
"delete from Grad;";
       try (PreparedStatement ps = con.prepareStatement(query)){
            ps.executeUpdate();
       } catch (SQLException ex) {
           Logger.getLogger(zb180239_AddressOperations.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    
    
}
