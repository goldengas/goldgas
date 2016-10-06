/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldgasagua;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author IFSP
 */
public class Conexao {
    
    public static Connection getConnection()
    {
        try{
            return DriverManager.getConnection("jdbc:mysql://localhost/goldgas", "root", "");
        }
        catch(Exception e){
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
