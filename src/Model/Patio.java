package Model;

import DataBaseSqlite.Conexion;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Patio {

    //por el usuario   
    private String placa;
    private String color;
    private String nombre_propietario;
    private String telefono;
    private String fecha_entrada;
    private String hora_entrada;

    private String tipo_vehiculo;
    private String tarifa;

    //el indice o numeral que sale en la tabla de todos los elemtos que en esta se encuentra.
    //este valor se actualiza al ingresar o eliminar elementos de la tabla.
    private int indice_tabla;
    
    //valor para modificar en la base de datos.
    private int id_veh_patio; 
    private int id_vehiculo;
    private int id_cliente;
    private int id_tabla_patio;
    
    

    public Patio() {
    }

    //contrcutor para traer de la base de datos y agregar a la base de datos
    public Patio(int id_veh_patio, String placa, String color, String nombre_propietario, String fecha_entrada, String hora_entrada, String tipo_vehiculo, String tarifa, int id_vehiculo, int id_cliente, int indice_tabla, int id_tabla_patio, String telefono) {
	this.placa = placa;
	this.color = color;
	this.nombre_propietario = nombre_propietario;
	this.fecha_entrada = fecha_entrada;
	this.hora_entrada = hora_entrada;
	this.tipo_vehiculo = tipo_vehiculo;
	this.tarifa = tarifa;
	this.id_veh_patio = id_veh_patio;
	this.id_vehiculo = id_vehiculo;
	this.id_cliente = id_cliente;
	this.indice_tabla = indice_tabla;
	this.id_tabla_patio = id_tabla_patio;
	this.telefono = telefono;
    }
    
    
    

    //Constructor como que se puede borrar
    public Patio(String placa, String color, String tipo_vehiculo, String nombre_propietario, String fecha_entrada, String hora_entrada, String tarifa, String telefono) {
	this.placa = placa;
	this.color = color;
	this.nombre_propietario = nombre_propietario;
	this.tipo_vehiculo = tipo_vehiculo;
	this.fecha_entrada = fecha_entrada;
	this.hora_entrada = hora_entrada;
	this.tarifa = tarifa;
	this.telefono = telefono;

    }

    //Metodos.
        public ObservableList<Patio> select_all_patio(){
	
	ObservableList<Patio> obs_en_patio = FXCollections.observableArrayList();
	
	Conexion obj_conexion = new Conexion();
	indice_tabla = 0;
	
	try {
            ResultSet respuesta = obj_conexion.funtion_consultar_registros(
		    "SELECT p.id_pat, placa_pat, p.color_pat, p.fec_en_pat, p.hora_en_pat, p.nom_cli_pat, p.cel_pat, t.nom_trf, tp.nom_tpv \n" +
		    "FROM en_patio_casual as p \n" +
		    "JOIN tarifa as t ON p.id_trf_pat = t.id_trf \n" +
		    "JOIN tipo_vehiculo as tp ON p.id_tpv_pat = tp.id_tpv"); 
	    
	    ResultSet respuesta2 = obj_conexion.funtion_consultar_registros(
		    "SELECT p.id_ptr, v.pla_vh, p.fec_en_ptr, p.hora_en_ptr, t.nom_trf, tp.nom_tpv, v.color_vh, c.nom_cli, c.tel_cli\n" +
		    "FROM en_patio_recurrente as p\n" +
		    "JOIN vehiculo as v ON p.id_vh_ptr = v.id_vh\n" +
		    "JOIN tarifa as t ON v.id_trf_vh = t.id_trf\n" +
		    "JOIN tipo_vehiculo as tp ON v.id_tpv_vh = tp.id_tpv\n" +
		    "JOIN cliente as c ON v.id_cli_vh = c.id_cli;");
            
	    while(respuesta.next()){

		//tomar los datos.
		id_veh_patio = respuesta.getInt("id_pat");
		placa = respuesta.getString("placa_pat");
		color = respuesta.getString("color_pat");
		fecha_entrada = respuesta.getString("fec_en_pat");
		hora_entrada = respuesta.getString("hora_en_pat");
		tarifa = respuesta.getString("nom_trf");
		tipo_vehiculo = respuesta.getString("nom_tpv");
		nombre_propietario = respuesta.getString("nom_cli_pat");
		telefono = respuesta.getString("cel_pat");
		id_tabla_patio = 1;
		indice_tabla++;

		//creo el la tarifa.
		Patio vh_patio = new Patio(id_veh_patio, placa, color, nombre_propietario, fecha_entrada, hora_entrada, tipo_vehiculo, tarifa,  id_vehiculo, id_cliente, indice_tabla, id_tabla_patio, telefono);

		//argar el objeto a una observable list
		obs_en_patio.add(vh_patio);
		
	    }
	    
	    
	    while(respuesta2.next()){
		
		//tomar los datos.
		id_veh_patio = respuesta2.getInt("id_ptr");
		placa = respuesta2.getString("pla_vh");
		color = respuesta2.getString("color_vh");
		fecha_entrada = respuesta2.getString("fec_en_ptr");
		hora_entrada = respuesta2.getString("hora_en_ptr");
		tarifa = respuesta2.getString("nom_trf");
		tipo_vehiculo = respuesta2.getString("nom_tpv");
		nombre_propietario = respuesta2.getString("nom_cli");
		telefono = respuesta2.getString("tel_cli");
		indice_tabla++;	
		id_tabla_patio = 2;
		
               //creo el la tarifa.
	       Patio vh_patio = new Patio(id_veh_patio, placa, color, nombre_propietario, fecha_entrada, hora_entrada, tipo_vehiculo, tarifa,  id_vehiculo, id_cliente, indice_tabla, id_tabla_patio, telefono);
		
	       //argar el objeto a una observable list.
	       obs_en_patio.add(vh_patio);
            }
			
        } catch (Exception e) {
         
            System.out.println("ERROR EN EL CATCH =>" + e);

        }
	return obs_en_patio;
    }
    
    
    
    public int insertar_vehiculo(int id_vh, String placa, String color, String fec_en, String hora_en, int id_trf, int id_tipo_vh, int cliente, String nombre, String tel) {

	Conexion obj_conexion = new Conexion();
	String sentencia;
	
	//validar si tenemos valores nulos es decir, si tenemos cliente y vehiculo
	if(cliente != 0){
	    //tenemos cliente y vehiculo.
	    sentencia = String.format("INSERT INTO en_patio_recurrente (id_ptr, id_vh_ptr, fec_en_ptr, hora_en_ptr) " +
		    "VALUES (null,'%s', '%s', '%s')",id_vh, fec_en, hora_en);
	}else{
	    //no tenemos cliente ni vehiculo.
	    sentencia = String.format("INSERT INTO en_patio_casual (id_pat,  placa_pat, color_pat, fec_en_pat, hora_en_pat, id_trf_pat, id_tpv_pat,  nom_cli_pat, cel_pat) " + 
		    "VALUES (null,  '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", placa, color, fec_en, hora_en, id_trf, id_tipo_vh, nombre, tel);
	}
	

	//Insertar todos los datos
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	return respuesta;

    }
    
    public int update_vehiculo_patio(int id_veh_patio, int patio, String placa, String color, String nombre, String tel,  int tarifa, int tipo_veh, int id_cli, int id_veh){
	
	Conexion obj_conexion = new Conexion();

	String sentencia = null;
	
	//validar si tenemos valores nulos es decir, si tenemos cliente y vehiculo
	if(id_cli != 0){
	    //tenemos cliente y vehiculo.
	    sentencia = String.format("UPDATE en_patio_recurrente SET id_vh_ptr='%s' WHERE id_ptr = '%s'" ,id_veh, id_veh_patio);
	    String udp_veiculo = String.format("UPDATE vehiculo SET id_trf_vh='%s', id_tpv_vh='%s' WHERE id_vh = '%s'" ,tarifa, tipo_veh, id_veh);
	    
	    int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	    
	    //solo modificar el vehiculo
	    int respuesta_2 = obj_conexion.funtion_ejecutar_sentencia_sql(udp_veiculo);


	}else{
	    //no tenemos cliente ni vehiculo.
	    sentencia = String.format("UPDATE en_patio_casual SET placa_pat='%s', color_pat='%s', id_trf_pat='%s', id_tpv_pat='%s', nom_cli_pat='%s', cel_pat='%s' "
		    + "WHERE id_pat = '%s'" ,placa, color, tarifa, tipo_veh, nombre, tel,  id_veh_patio);
	}	
	
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	return respuesta;
    }
    

    
    public int delete_vehiculo_patio(int patio, int id_veh_patio){
	
	Conexion obj_conexion = new Conexion();
	String sentencia = "";
	
	if(patio == 1){
	    sentencia = String.format("DELETE FROM en_patio_casual WHERE id_pat =%d",id_veh_patio);
	}else if(patio == 2){
	    sentencia = String.format("DELETE FROM en_patio_recurrente WHERE id_ptr =%d",id_veh_patio);
	}
		
	int respuesta = obj_conexion.funtion_ejecutar_sentencia_sql(sentencia);
	return respuesta;
	
    }
    
    
    

    //Setters y Getters.
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

    public String getNombre_propietario() {
	return nombre_propietario;
    }

    public void setNombre_propietario(String nombre_propietario) {
	this.nombre_propietario = nombre_propietario;
    }

    public String getTipo_vehiculo() {
	return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
	this.tipo_vehiculo = tipo_vehiculo;
    }

    public String getFecha_entrada() {
	return fecha_entrada;
    }

    public void setFecha_entrada(String fecha_entrada) {
	this.fecha_entrada = fecha_entrada;
    }

    public String getHora_entrada() {
	return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
	this.hora_entrada = hora_entrada;
    }

    public String getTarifa() {
	return tarifa;
    }

    public void setTarifa(String tarifa) {
	this.tarifa = tarifa;
    }

    public int getIndice() {
	return indice_tabla;
    }

    public void setIndice(int indice_tabla) {
	this.indice_tabla = indice_tabla;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public int getIndice_tabla() {
	return indice_tabla;
    }

    public void setIndice_tabla(int indice_tabla) {
	this.indice_tabla = indice_tabla;
    }

    public int getId_veh_patio() {
	return id_veh_patio;
    }

    public void setId_veh_patio(int id_veh_patio) {
	this.id_veh_patio = id_veh_patio;
    }

    public int getId_vehiculo() {
	return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
	this.id_vehiculo = id_vehiculo;
    }

    public int getId_cliente() {
	return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
	this.id_cliente = id_cliente;
    }

    public int getId_tabla_patio() {
	return id_tabla_patio;
    }

    public void setId_tabla_patio(int id_tabla_patio) {
	this.id_tabla_patio = id_tabla_patio;
    }
    

}
