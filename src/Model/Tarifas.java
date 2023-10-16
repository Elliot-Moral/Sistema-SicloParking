package Model;

import DataBaseSqlite.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tarifas {
    private String nombre_tarifa;
    private int valor_tarifa;  
    private int id_tarifa;
    
    //constrcutor para llamar el metodo que me trae los datos.
    public Tarifas(){
    
    }

    //constructor para la tabla
    public Tarifas( int id, String nombre_tarifa, int valor_tarifa) {
	this.nombre_tarifa = nombre_tarifa;
	this.valor_tarifa = valor_tarifa;
	this.id_tarifa = id;
    }
    
    
    //contrcutor en general
    public Tarifas(String nombre_tarifa, int valor_tarifa) {
        this.nombre_tarifa = nombre_tarifa;
        this.valor_tarifa = valor_tarifa;
    }
    
    
    
    
    //metodos ===>
    public ObservableList<Tarifas> select_all_tarifa(){
	
	ObservableList<Tarifas> obs_tarifa = FXCollections.observableArrayList();
	
	Conexion obj_conexion = new Conexion();
	
	try {
            ResultSet respuesta = obj_conexion.funtion_consultar_registros("SELECT * FROM tarifa");
            
            while(respuesta.next()){
		
		//tomar los datos.
		id_tarifa = respuesta.getInt("id_trf");
		nombre_tarifa = respuesta.getString("nom_trf");
		valor_tarifa = respuesta.getInt("valor_trf");
			
               //creo el la tarifa.
	       Tarifas trf = new Tarifas(id_tarifa, nombre_tarifa, valor_tarifa);
		
	       //argar el objeto a una observable list
	       obs_tarifa.add(trf);
            }
        } catch (Exception e) {
         
            System.out.println("ERROR EN EL CATCH =>" + e);

        }
	return obs_tarifa;
    }
    
    
    
    
    public int insert_tarifa(String nombre_tarifa, int valor_tarifa){
	Conexion obj_conexion = new Conexion();
	
	//preparar la sentencia sql
	String sentencia = String.format("INSERT INTO tarifa (id_trf, nom_trf, valor_trf) " + "VALUES (null,'%s', '%s')",nombre_tarifa, valor_tarifa);
	
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	System.out.println(respuesta);
	
	return respuesta;

    }
     
     
    
    public int update_tarifa(int id_tarifa, String nombre_tarifa, int valor_tarifa){
	Conexion obj_conexion = new Conexion();
	
	//preparar la sentencia sql
	String sentencia = String.format("UPDATE tarifa SET nom_trf='%s', valor_trf='%s' WHERE id_trf = '%s'" ,nombre_tarifa, valor_tarifa, id_tarifa);
	
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	return respuesta;
    }
    
    public int delete_tarifa(int id_tarifa){
	Conexion obj_conexion = new Conexion();
	
	String sentencia = String.format("DELETE FROM tarifa WHERE id_trf =%d",id_tarifa);
	
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	return respuesta;
	
    }
    
    public int consultar_precio_de_tarifa(String tarifa){
       
        Conexion obj_conexion = new Conexion();
        int valor = 0;
        
        try {
            
            String sentencia = String.format("select valor_trf from tarifa where nom_trf = '%s'" , tarifa);
            ResultSet respuesta = obj_conexion.funtion_consultar_registros(sentencia);
            
            while(respuesta.next()){			
                //tomar los datos.
                valor_tarifa = respuesta.getInt("valor_trf");
                valor = valor_tarifa;          
            }

        } catch (Exception e) {
            System.out.println("ERROR EN EL CATCH =>" + e);
        }

	return valor;
    }
    
    
    public ArrayList<String> consultar_solo_nombres_tarifas(){
        
        Conexion obj_conexion = new Conexion();
        ArrayList<String> nomb_tarifas = new  ArrayList<>();
        
        try {
            
            ResultSet respuesta = obj_conexion.funtion_consultar_registros("select nom_trf from tarifa");            
            while(respuesta.next()){
		
		//tomar los datos.
		nombre_tarifa = respuesta.getString("nom_trf");
		
	       //argar el objeto a una observable list
	       nomb_tarifas.add(nombre_tarifa);
            }

        } catch (Exception e) {
            System.err.println("ERROR EN EL CATCH =>" + e);
        }
        
        return nomb_tarifas;
    }
    
    
    // === setters y getters ===
    public String getNombre_tarifa() {
        return nombre_tarifa;
    }

    public void setNombre_tarifa(String nombre_tarifa) {
        this.nombre_tarifa = nombre_tarifa;
    }

    public int getValor_tarifa() {
        return valor_tarifa;
    }

    public void setValor_tarifa(int valor_tarifa) {
        this.valor_tarifa = valor_tarifa;
    }

    public int getId_tarifa() {
	return id_tarifa;
    }

    public void setId_tarifa(int id) {
	this.id_tarifa = id;
    }
    
    
    
}
