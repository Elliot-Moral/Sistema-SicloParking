/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.Tarifas;
import Model.TipoVehiculo;
import helpers.Funciones;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Elliot Moral
 */
public class ConfiguracionTarifasController implements Initializable {

    //public ConfiguracionTarifasController() {
	//this.mostrar_datos();
    //}
    
    

    @FXML
    private TableView<Tarifas> tbl_tarifas;
    @FXML
    private TableColumn<Tarifas, String> col_tarifa;
    @FXML
    private TableColumn<Tarifas, Integer> col_valor_tarifa;
    @FXML
    private TextField txt_nombre_tarifa;
    @FXML
    private TextField txt_valor_tarifa;
    @FXML
    private Button bnt_agregar_tarifa;
    @FXML
    private Button bnt_modificar_tarifa;
    @FXML
    private Button bnt_eliminar_tarifa;
    @FXML
    private Button bnt_nueva_tarifa;
    
    //para minipular los elementos en la tabla
    private ObservableList<Tarifas> ObserListaTarifa;
    

    //####para minipular los elementos en la tabla vehiculos####
    private ObservableList<TipoVehiculo> ObserListaTipoVehiculo;   
    @FXML
    private Button bnt_vehiculos;
    @FXML
    private Button bnt_mostrar_tarifas;
    @FXML
    private Pane pnl_vehiculos;
    @FXML
    private Pane pnl_tarifas;
    @FXML
    private Button btn_agregar_vehiculo;
    @FXML
    private Button bnt_eliminar_vehiculo;
    @FXML
    private TableView<TipoVehiculo> tbl_vehiculos;
    @FXML
    private TableColumn<TipoVehiculo, String> col_vehiculo;
    @FXML
    private TextField txt_vehiculo;

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
	//IMPORTANTE-
        //YO DESABILITO EL MODIFICAR EL NOMBRE DE TARIFA POR QUE ME GENERAR PROGLEMAS
        //AL CACULAR LOS COBROS
        this.txt_nombre_tarifa.setDisable(true);
        
        //cargar la lista al momento de crear la ventana.
	ObserListaTarifa = FXCollections.observableArrayList();
        
        //todo eso es de javafx. //asociar que colomna va con que atributo.
        this.col_tarifa.setCellValueFactory(new PropertyValueFactory("nombre_tarifa"));
        this.col_valor_tarifa.setCellValueFactory(new PropertyValueFactory("valor_tarifa"));
	
	//traer los datos
	Tarifas ob_tarifa = new Tarifas();
	ObserListaTarifa = ob_tarifa.select_all_tarifa();
	
	//cargar los datos.
	this.tbl_tarifas.setItems(ObserListaTarifa);
       
        
        //#### Cargar los Datos de la tabla de vehiculos.####
                   
        //cargar la lista al momento de crear la ventana.
	ObserListaTipoVehiculo = FXCollections.observableArrayList();
        
        //todo eso es de javafx. //asociar que colomna va con que atributo.
        //this.col_vehiculo.setCellValueFactory(new PropertyValueFactory("id_tpv"));
        this.col_vehiculo.setCellValueFactory(new PropertyValueFactory("str_tipo_vehiculo"));
	
	//traer los datos
	TipoVehiculo ob_tipo_vehiculo = new TipoVehiculo();
	ObserListaTipoVehiculo = ob_tipo_vehiculo.select_all_TipoVehiculo();
	
	//cargar los datos.
	this.tbl_vehiculos.setItems(ObserListaTipoVehiculo);

    }    
    
    public void mostrar_datos(){
		
	//usando una oservable lista para todo.
	
	//TARER LOS DATOS.
	Tarifas ob_tarifa = new Tarifas();
	ObserListaTarifa = ob_tarifa.select_all_tarifa();
	this.tbl_tarifas.setItems(ObserListaTarifa);
	 
    }

    
    
    @FXML
    private void agregar_tarifa(ActionEvent event) {
        try {
            //mostrar_datos();
            String str_nombre_tarifa = this.txt_nombre_tarifa.getText();
            int int_valor_tarifa = Integer.parseInt(this.txt_valor_tarifa.getText());
        
            Tarifas ob_tarifa = new Tarifas(str_nombre_tarifa, int_valor_tarifa);
	    
	    		    

	    //validar si hay datos repetidos.
	    if(!this.ObserListaTarifa.contains(ob_tarifa)){
		    
		    //this.ObserListaTarifa.add(ob_tarifa);-> util no olvidar.

		//insertar en la base de datos.
		int respuesta = ob_tarifa.insert_tarifa(str_nombre_tarifa, int_valor_tarifa);
		
			//validar la respuesta de la base de datos.
			if(respuesta >= 1){
			    this.mostrar_datos();
			    //this.tbl_tarifas.setItems(ObserListaTarifa);
			}else{
			    System.out.println("algo salio mal");
			}
                
                //restablecer los valores.
                txt_nombre_tarifa.setText("");
                txt_valor_tarifa.setText("");
		
            }else{
                //Alerta javafx
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);//se ve mejor asi.
                alerta.setTitle("Error");
                alerta.setContentText("La persona existe!!");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            //Alerta javafx //PARA ESTO LA EDAD DEBE SER UN ENTERO
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);//se ve mejor asi.
            alerta.setTitle("Error");
            alerta.setContentText("Valores Incorrectos, El Precio Debe Ser Numerico!!");
            alerta.showAndWait();
        }
        
    }
    
    @FXML
    private void seleccionar(MouseEvent event) {
        //Devolver el registro selecionado de la tabla
        Tarifas obj_tarifa = this.tbl_tarifas.getSelectionModel().getSelectedItem();
        
        if(obj_tarifa != null){
            
            this.txt_nombre_tarifa.setText(obj_tarifa.getNombre_tarifa());
            this.txt_valor_tarifa.setText(obj_tarifa.getValor_tarifa() + "");
            
            this.tbl_tarifas.setItems(ObserListaTarifa);
	    
	    //acomodar los botones.
	    bnt_modificar_tarifa.setDisable(false);
	    bnt_eliminar_tarifa.setDisable(false);
	    bnt_agregar_tarifa.setDisable(true);
	    bnt_nueva_tarifa.setDisable(false);

        }
    }

    @FXML
    private void modificar_tarifa(ActionEvent event) {
        
        Tarifas obj_tarifa = this.tbl_tarifas.getSelectionModel().getSelectedItem();
	
	//capturar id para modificar
        int id_tarifa = obj_tarifa.getId_tarifa();
	
	//Clase con funciones reptidas
	Funciones fn = new Funciones();
	
        if(obj_tarifa == null){
            
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);//se ve mejor asi.
            alerta.setTitle("Error");
            alerta.setContentText("Debes selecionar un campo en la tabla.");
            alerta.showAndWait();

        }else{
            
            try { //
            
                String str_nombre_tarifa = this.txt_nombre_tarifa.getText();
                int int_valor_tarifa = Integer.parseInt(this.txt_valor_tarifa.getText());
		

                //se agrega en un segundo objeto. //se pasan los atributos de un objeto al otro "p"
                Tarifas aux = new Tarifas(str_nombre_tarifa, int_valor_tarifa);

                if(!this.ObserListaTarifa.contains(aux)){
		    
		    //modificar en la base de datos.
		    int respuesta = obj_tarifa.update_tarifa(id_tarifa, str_nombre_tarifa, int_valor_tarifa);
		    
		    
		    //validar si se actualizo.
		    if(respuesta == 1){
			this.mostrar_datos();
			fn.Disparar_alerta_info("Valor Actualizado.");
		    }else{
		    	fn.Disparar_alerta_info("Error Al Actualizar.");
		    }

                }else{
                    //Alerta javafx.
		    fn.Disparar_alerta_error("La tarifa ya existe!!");
                }
            
            } catch (Exception e) {
		//Alerta javafx.
                fn.Disparar_alerta_error("VALOR INCORRECTOS!!. Digita El Precio De La Tarifa");

            }
            
        }
    }

    @FXML
    private void eliminar_tarifa(ActionEvent event) {
        
        Tarifas obj_tarifa = this.tbl_tarifas.getSelectionModel().getSelectedItem();
	
        //capturar id para modificar
	int id_tarifa = obj_tarifa.getId_tarifa();
	
	//Clase con funciones reptidas
	Funciones fn = new Funciones();
	
        if(obj_tarifa == null){
            
	    fn.Disparar_alerta_error("Debes selecionar una tarifa.");

        }else{
	    
	    //eliminar desde la base de datos.
	    int respuesta = obj_tarifa.delete_tarifa(id_tarifa);
	    
	    //validar si se actualizo.
	    if(respuesta == 1){
		this.mostrar_datos();
		fn.Disparar_alerta_info("Tarifa eliminada!");
	    }else{
		fn.Disparar_alerta_info("Error al eliminar.");
	    }
        }
    }

    @FXML
    private void nueva_tarifa(ActionEvent event) {
         //reestablecer los valores.
         txt_nombre_tarifa.setText("");
         txt_valor_tarifa.setText("");
         
         //reestablercer los valores
	    bnt_agregar_tarifa.setDisable(false);
	    bnt_modificar_tarifa.setDisable(true);
	    bnt_eliminar_tarifa.setDisable(true);
	    bnt_nueva_tarifa.setDisable(true);

    }


    @FXML
    private void monstrar_panel(ActionEvent event) {
        if(event.getSource() == bnt_vehiculos){
            pnl_vehiculos.toFront();
        }else if(event.getSource() == bnt_mostrar_tarifas){
            pnl_tarifas.toFront();
        }
    }

    
    
    
    
    
    
    //ESTO HACE PARTE DE LA VENTANA DE AGREGAR VEHICULOS.
    public void mostrar_datos_tabla_vehiculos(){
		
	//usando una oservable lista para todo.
	//TARER LOS DATOS.
	TipoVehiculo ob_tipo_vehiculo = new TipoVehiculo();
	ObserListaTipoVehiculo = ob_tipo_vehiculo.select_all_TipoVehiculo();
	this.tbl_vehiculos.setItems(ObserListaTipoVehiculo);
	 
    }
    
    
    
    
    
    @FXML
    private void agregar_vehiculo(ActionEvent event) {
        try {
            //mostrar_datos();
            String str_tipo_vehiculo = this.txt_vehiculo.getText();
           
       
	    TipoVehiculo ob_vehiculo = new TipoVehiculo(str_tipo_vehiculo, 0);
	    		    

	    //validar si hay datos repetidos.
	    if(!this.ObserListaTarifa.contains(ob_vehiculo)){
		    
		    //this.ObserListaTarifa.add(ob_tarifa);-> util no olvidar.

		//insertar en la base de datos.
		int respuesta = ob_vehiculo.insert_tipo_vehiculo(str_tipo_vehiculo);
		
			//validar la respuesta de la base de datos.
			if(respuesta >= 1){
			    this.mostrar_datos_tabla_vehiculos();
			    //this.tbl_tarifas.setItems(ObserListaTarifa);
			}else{
			    System.out.println("algo salio mal");
			}
                
                //restablecer los valores.
                txt_vehiculo.setText("");

		
            }else{
                //Alerta javafx
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText(null);//se ve mejor asi.
                alerta.setTitle("Error");
                alerta.setContentText("El tipo de vehiculo ya existe!!");
                alerta.showAndWait();
            }
        } catch (Exception e) {
            //Alerta javafx //PARA ESTO LA EDAD DEBE SER UN ENTERO
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);//se ve mejor asi.
            alerta.setTitle("Error");
            alerta.setContentText("Valores Incorrectos, El Precio Debe Ser Numerico!!");
            alerta.showAndWait();
        }
    }

    @FXML
    private void eliminar_vehiculo(ActionEvent event) {
        
        TipoVehiculo obj_tpv = this.tbl_vehiculos.getSelectionModel().getSelectedItem();
	
        //capturar id para modificar
	int id_tipo_veh = obj_tpv.getId_tpv();
	
	//Clase con funciones reptidas
	Funciones fn = new Funciones();
	
        if(obj_tpv == null){
            
	    fn.Disparar_alerta_error("Debes selecionar un vehiculo.");

        }else{
	    
	    //eliminar desde la base de datos.
	    int respuesta = obj_tpv.delete_tipo_vehiculo(id_tipo_veh);
	    
	    //validar si se actualizo.
	    if(respuesta == 1){
		this.mostrar_datos_tabla_vehiculos();
		fn.Disparar_alerta_info("Vehiculo eliminado!");
	    }else{
		fn.Disparar_alerta_info("Error al eliminar.");
	    }
        }
    }

    @FXML
    private void seleccionar_vehiculo(MouseEvent event) {
    }


    
}
