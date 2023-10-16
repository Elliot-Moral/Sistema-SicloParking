/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cliente;
import helpers.Funciones;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Elliot Moral
 */
public class GestionClientesController implements Initializable {

    @FXML
    private TextField txt_buscar;
    @FXML
    private TableView<Cliente> tbl_clientes;
    @FXML
    private TableColumn<Cliente, String> col_nombre;
    @FXML
    private TableColumn<Cliente, String> col_apellido;
    @FXML
    private TableColumn<Cliente, String> col_cedula;
    @FXML
    private TableColumn<Cliente, String> col_celular;
    @FXML
    private TableColumn<Cliente, String> col_placa;
    @FXML
    private TableColumn<Cliente, String> col_color;
    @FXML
    private Button btn_nuevo;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_cedula;
    @FXML
    private TextField txt_celular;
    @FXML
    private Button bnt_buscar;

    
    private ObservableList<Cliente> obs_clientes;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_guardar;
    
    
    Funciones fn = new Funciones();
    @FXML
    private TextField txt_placa;
    @FXML
    private TextField txt_color;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //cargar la lista al momento de crear la ventana.
	obs_clientes = FXCollections.observableArrayList();
        
        //todo eso es de javafx. //asociar que colomna va con que atributo.
        this.col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.col_apellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.col_cedula.setCellValueFactory(new PropertyValueFactory("cedula"));
	this.col_celular.setCellValueFactory(new PropertyValueFactory("celular"));
        this.col_placa.setCellValueFactory(new PropertyValueFactory("placa"));
        this.col_color.setCellValueFactory(new PropertyValueFactory("color"));
	
	
	//traer los datos
	Cliente obj_cliente = new Cliente();
	obs_clientes = obj_cliente.select_all_cliente();
	
	//cargar los datos.
	this.tbl_clientes.setItems(obs_clientes);
    }    
    
    public void mostrar_datos(){
	
	//TARER LOS DATOS.
	Cliente ob_cli = new Cliente();
	obs_clientes = ob_cli.select_all_cliente();
	this.tbl_clientes.setItems(obs_clientes);
	 
    }
    
    @FXML
    private void nuevo_cliente(ActionEvent event) {
         //llenar los campos de texto con la informacion
	    this.txt_nombre.setText("");
	    this.txt_apellido.setText("");
            this.txt_cedula.setText("");
	    this.txt_celular.setText("");
            this.txt_placa.setText("");
	    this.txt_color.setText("");
            
            //cambiar el nombre del boton guardar a actualizar
            btn_guardar.setText("Guardar");
    }

    @FXML
    private void eliminar_cliente(ActionEvent event) {
        
        Cliente obj_cli = this.tbl_clientes.getSelectionModel().getSelectedItem();
	
        //capturar id para modificar
	int id_cliete = obj_cli.getId_cliente();
        int id_veh = obj_cli.getId_vehiculo();
        
        
        if(obj_cli == null){
            
	    fn.Disparar_alerta_error("Debes selecionar un cliente.");

        }else{
	    
	    //eliminar desde la base de datos.
	    int respuesta = obj_cli.eliminar_cliente(id_cliete, id_veh);
	    
	    //validar si se actualizo.
	    if(respuesta == 1){
		this.mostrar_datos();
		fn.Disparar_alerta_info("Cliente Eliminado!!!");
	    }else{
		fn.Disparar_alerta_info("Error al eliminar!!!");
	    }
        }
    }
    

    @FXML
    private void guardar_cliente(ActionEvent event) {
       
        try {
            
            //capturar los datos de los textfields 
            String st_nombre = txt_nombre.getText();
            String st_apellido = txt_apellido.getText();
            String st_cedula;
            String st_celular;
            String st_placa = txt_placa.getText().toUpperCase();
            String st_color = txt_color.getText();
             
            
            //capturar numeros para el try y validar rapido si ingresan letras
            long long_cedula = Long.parseLong(txt_cedula.getText());
            long long_celular = Long.parseLong(txt_celular.getText());
            
            st_cedula = long_cedula+"";
            st_celular = long_celular+"";
            

            Cliente obj_cl = new Cliente();
            
            //validar el boton.
            String estado_btn = btn_guardar.getText();
            int respuesta;
            if(estado_btn.equals("Guardar")){
                
                respuesta = obj_cl.guardar_cliene(st_nombre, st_apellido, st_cedula, st_celular, st_placa, st_color);
                //validar si los datos se guardaron correctamente.
                if(respuesta == 1){
                    mostrar_datos();
                    fn.Disparar_alerta_info("Cliente Guardado!!");
                }else{
                    fn.Disparar_alerta_info("Error Al Guardar Cliente!!");
                }
                
            }else{  
                
              Cliente obj_cli_tabla_clientes = this.tbl_clientes.getSelectionModel().getSelectedItem();
                if(this.tbl_clientes != null){
                  
                    int id_cliente = obj_cli_tabla_clientes.getId_cliente();
                    int id_veh = obj_cli_tabla_clientes.getId_vehiculo();
                
                    System.out.println(id_cliente+" "+id_veh);
                
                    respuesta = obj_cl.update_cliente(id_cliente, st_nombre, st_apellido, st_cedula, st_celular, id_veh, st_placa, st_color);
                  
                    if(respuesta == 1){
                      mostrar_datos();
                      fn.Disparar_alerta_info("Cliente Actualizado!!");
                    }else{
                      fn.Disparar_alerta_info("Error Al Actualizar Cliente!!");
                    }    
                  
                }else{
                    fn.Disparar_alerta_info("Deben selecionar un cliente!!!");
                }
              
            }            
           
            
        } catch (Exception e) {
            fn.Disparar_alerta_info("La cedula y el numero celular deben ser valores numericos!!. "+e);
            System.out.println(e);
        }


        
        
    }

    @FXML
    private void buscar_cliente(ActionEvent event) {
    }

    @FXML
    private void seleccionar_cliente(MouseEvent event) {
        	//capturar el elemento de la tabla.
        Cliente obj_cliente = this.tbl_clientes.getSelectionModel().getSelectedItem();

	//validar si no se ha selecionado nada.
        if(obj_cliente == null){

	    fn.Disparar_alerta_error("Debes selecionar un cliente.");

        }else{
	    
	    //llenar los campos de texto con la informacion
	    this.txt_nombre.setText(obj_cliente.getNombre());
	    this.txt_apellido.setText(obj_cliente.getApellido());
            this.txt_cedula.setText(obj_cliente.getCedula());
	    this.txt_celular.setText(obj_cliente.getCelular());
            this.txt_placa.setText(obj_cliente.getPlaca());
            this.txt_color.setText(obj_cliente.getColor());
            
            //cambiar el nombre del boton guardar a actualizar
            btn_guardar.setText("Actualizar");
                    
    
        }
    }
    
}
