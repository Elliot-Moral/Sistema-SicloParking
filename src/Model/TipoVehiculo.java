/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DataBaseSqlite.Conexion;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Elliot Moral
 */
public class TipoVehiculo {
    
    private String str_tipo_vehiculo;
    private int id_tpv;
    

    public TipoVehiculo() {
    }

    public TipoVehiculo(String str_tipo_vehiculo, int id_tpv) {
	this.str_tipo_vehiculo = str_tipo_vehiculo;
	this.id_tpv = id_tpv;
    }
    

	
    
    //===== Setter and Getters =====
    public String getStr_tipo_vehiculo() {
	return str_tipo_vehiculo;
    }

    public void setStr_tipo_vehiculo(String str_tipo_vehiculo) {
	this.str_tipo_vehiculo = str_tipo_vehiculo;
    }

    public int getId_tpv() {
	return id_tpv;
    }

    public void setId_tpv(int id_tpv) {
	this.id_tpv = id_tpv;
    }
    
    


    
    //===== metodos ======
    public ObservableList<TipoVehiculo> select_all_TipoVehiculo(){
	
	ObservableList<TipoVehiculo> obs_TipoVehiculo = FXCollections.observableArrayList();
	
	Conexion obj_conexion = new Conexion();
	
	try {
            ResultSet respuesta = obj_conexion.funtion_consultar_registros("SELECT * FROM tipo_vehiculo");
            
            while(respuesta.next()){
		
		//tomar los datos.
		this.id_tpv = respuesta.getInt("id_tpv");
		this.str_tipo_vehiculo = respuesta.getString("nom_tpv");
                
               //creo el la tarifa.
	       TipoVehiculo tp_veh = new TipoVehiculo(str_tipo_vehiculo, id_tpv);
		
	       //argar el objeto a una observable listo
	       obs_TipoVehiculo.add(tp_veh);
            }
        } catch (Exception e) {
         
            System.out.println("ERROR EN EL CATCH =>" + e);

        }
	return obs_TipoVehiculo;
    }
	
    
    
    
    
    public int insert_tipo_vehiculo(String tipo_vehiculo){
	Conexion obj_conexion = new Conexion();
	
	//preparar la sentencia sql
	String sentencia = String.format("INSERT INTO tipo_vehiculo (id_tpv, nom_tpv) " + "VALUES (null,'%s')",tipo_vehiculo);
	
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	System.out.println(respuesta);
	
	return respuesta;

    }
    
        public int delete_tipo_vehiculo(int id_tpv){
	Conexion obj_conexion = new Conexion();
	
	String sentencia = String.format("DELETE FROM tipo_vehiculo WHERE id_tpv =%d",id_tpv);
	
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	return respuesta;
	
    }
      
}
