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
public class Cliente {
    
    private int id_cliente;
    private String nombre;
    private String apellido;
    private String celular;
    private String cedula;
    
    //informacion del vehiculo
    private int id_vehiculo;
    private String placa;
    private String color;

    //contructor para llamar los metodos
    public Cliente() {
    }
       
    
    
    //contructor para la base de datos.
    public Cliente(int id_cliente, String nombre, String apellido, String celular, String cedula, int id_vehiculo, String placa, String color) {
	this.id_cliente = id_cliente;
	this.nombre = nombre;
	this.apellido = apellido;
	this.celular = celular;
	this.cedula = cedula;
	this.id_vehiculo = id_vehiculo;
	this.placa = placa;
	this.color = color;
    }
    
    

    
    
    //=======  metodos  =======
       public ObservableList<Cliente> select_all_cliente(){
	
	ObservableList<Cliente> obs_clientes = FXCollections.observableArrayList();
	
	Conexion obj_conexion = new Conexion();
	
	try {
            ResultSet respuesta = obj_conexion.funtion_consultar_registros("select * from cliente inner JOIN vehiculo where id_cli == id_vh");
            
            while(respuesta.next()){
		
		//tomar los datos tabla cliente.
		id_cliente = respuesta.getInt("id_cli");
		nombre = respuesta.getString("nom_cli");
		apellido = respuesta.getString("ape_cli");
		celular = respuesta.getString("tel_cli");
		cedula = respuesta.getString("cc_cli");
		
		//tabla vehiculo
		id_vehiculo = respuesta.getInt("id_vh");		
		placa = respuesta.getString("pla_vh");
		color = respuesta.getString("color_vh");

			
               //creo el cliente.
	       Cliente cl = new Cliente(id_cliente, nombre, apellido, celular, cedula, id_vehiculo, placa, color);

	       //argar el objeto a una observable list
	       obs_clientes.add(cl);
            }
        } catch (Exception e) {
         
            System.out.println("ERROR EN EL CATCH =>" + e);

        }
	return obs_clientes;
    } 
    
     
       
    public int guardar_cliene(String nombre, String apellido, String cedula, String celular, String placa, String color){
        
        Conexion obj_conexion = new Conexion();
        int validar_insersiones = 0;
        int tarifa_defaul = 1;
        int tipvh_defaul = 1;
	
	//preparar la sentencia sql, para la tabla cliente.
	String sentencia = String.format("INSERT INTO cliente (id_cli, nom_cli, ape_cli, cc_cli, tel_cli) " + "VALUES (null,'%s', '%s','%s', '%s')",nombre, apellido, cedula, celular);
        int respuesta1 = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	
        int id_cliente = consultar_ultimo_registro_id();
        
        //para la tabla vehiculo.
        String sentencia2 = String.format("INSERT INTO vehiculo (id_vh, id_cli_vh, pla_vh, color_vh, id_trf_vh, id_tpv_vh) " + "VALUES (null,'%s', '%s','%s', '%s', '%s')",id_cliente, placa, color, tarifa_defaul, tipvh_defaul);
        int respuesta2 = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia2);
        
        //validar
        if(respuesta1 == 1 && respuesta2 == 1){
            validar_insersiones = 1;
        }else{
            System.out.println("Error en alguna de las dos insersiones");
        }
	
	return validar_insersiones;
    }
    
    public int consultar_ultimo_registro_id(){
        
        int ultimo_id = 0;
        	
	Conexion obj_conexion = new Conexion();
	
	try {
            ResultSet respuesta = obj_conexion.funtion_consultar_registros("select id_cli from cliente order by id_cli desc limit 1");
            
            while(respuesta.next()){
		
		//tomar los datos tabla cliente.
		ultimo_id = respuesta.getInt("id_cli");

            }
        } catch (Exception e) {
         
            System.out.println("ERROR EN EL CATCH =>" + e);

        }
	return ultimo_id;
    
    }
    
    
    public int update_cliente(int id_cliente, String nombre, String apellido, String cedula, String celular, int id_vh, String placa, String color){
	Conexion obj_conexion = new Conexion();
	
	//preparar la sentencia sql tabla cliente
        String sentencia1 = String.format("UPDATE cliente SET nom_cli='%s', ape_cli='%s', cc_cli='%s', tel_cli='%s' WHERE id_cli = '%s'" ,nombre, apellido, cedula, celular, id_cliente);
	int respuesta1 = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia1);

        String sentencia2 = String.format("UPDATE vehiculo SET pla_vh='%s', color_vh='%s' WHERE id_vh = '%s'" ,placa, color, id_vh);
	int respuesta2 = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia2);
	
        int confirmacion = 0;
        //validar
        if(respuesta1 == 1 && respuesta2 == 1){
            confirmacion = 1;
        }else{
            System.out.println("No se pudo actuarlizar algo cliente o vehiculo, revisar bien");
        }
        
	return confirmacion;
    }
    
    
    public int eliminar_cliente(int id_cliente, int id_vhc){
        
        Conexion obj_conexion = new Conexion();
	
        //tabla cliente.
	String sentencia1 = String.format("DELETE FROM cliente WHERE id_cli =%d",id_cliente);
        int respuesta1 = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia1);
        
        //tabla cliente.
        String sentencia2 = String.format("DELETE FROM vehiculo WHERE id_vh =%d",id_vhc);
	int respuesta2 = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia2);
        
        int confirmacion = 0;
        
        if(respuesta1 == 1 && respuesta2 == 1){
            confirmacion = 1;
        }else{
            System.out.println("No se pudo elimiar algo cliente o vehiculo, revisar bien");
        }
        
	return confirmacion;
    }
       
    
    //SETTERS AND GETTERS.
    public int getId_cliente() {
	return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
	this.id_cliente = id_cliente;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellido() {
	return apellido;
    }

    public void setApellido(String apellido) {
	this.apellido = apellido;
    }

    public String getCelular() {
	return celular;
    }

    public void setCelular(String celular) {
	this.celular = celular;
    }

    public String getCedula() {
	return cedula;
    }

    public void setCedula(String cedula) {
	this.cedula = cedula;
    }

    public String getPlaca() {
	return placa;
    }

    public void setPlaca(String placa) {
	this.placa = placa;
    }

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }

    public int getId_vehiculo() {
	return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
	this.id_vehiculo = id_vehiculo;
    }
    

    
    
    

}
