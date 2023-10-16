/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBaseSqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Elliot Moral
 */
public class Conexion {
    String strConnexionDB = "jdbc:sqlite:C:/Users/Elliot Moral/Music/siclo_park.db";
    Connection conectar = null;
   
    
    public Conexion(){
        try {
            Class.forName("org.sqlite.JDBC"); //un enrredijo que no entiendo
            conectar = DriverManager.getConnection(strConnexionDB);
            System.out.println("Se establecio la conexion");
        } catch (Exception e) {
            System.out.println("Error de conexion => " + e);
        }
    }
    // esta funcion me sirve para ejecutar codigo sql y hacer insert update delete
    //recuerde que las funciones se resiclan o mejor dicho quererde para que sirven las funciones!!
    public int funtion_ejecutar_sentencia_sql(String sentencia_sql){ //por que se pone int, se usa para que devuelva un numero la funcion.
        try {
            PreparedStatement pstm = conectar.prepareStatement(sentencia_sql); //insertando datos o mejor dicgo sentencia sql
            pstm.execute(); //inportante esto!!! me llevo tiempo encontrar el error, falataba esta parte
            return 1; //no se para que devuelve 1
        } catch (SQLException e) {
            System.out.println("Error del metodo ejecutar_sentencia_sql => " + e);
            return 0;
        }
        
    } 
    //crear una funcion que devuelva las consultas
    public ResultSet funtion_consultar_registros(String sentencia_sql){
        try {
            PreparedStatement pstm = conectar.prepareStatement(sentencia_sql);
            ResultSet respuesta = pstm.executeQuery();
            return respuesta;
            
        } catch (Exception e) {
            System.out.println("error catch funcion consultar_registros"+e);
            return null;
        }
    }
}

