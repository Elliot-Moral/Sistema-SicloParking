/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Cliente;
import Model.Patio;
import Model.Tarifas;
import Model.TipoVehiculo;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import helpers.Funciones;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 * FXML Controller class
 *
 * @author Elliot Moral
 */
public class AgregarVehiculoController implements Initializable {
    
    private int id_cliente;
    private String nombre_cliente;
    private String apellido_cliente;
    private int id_vehiculo;
    private String placa_cliente;
    private String color_cliente;
    private String telefono_cliente;
    private int id_tarifa;
    private int id_tp_vh;
    private int id_tabla_patio;
    
    private int id_veh_patio;

    @FXML
    private Button bnt_cancelar;
    @FXML
    private Button btn_aceptar;
    @FXML
    private TextField txt_placa;
    @FXML
    private TextField txt_color;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_telefono;
    @FXML
    private ComboBox<String> combox_tipo_vehiculo;
    @FXML
    private ComboBox<String> combox_tarifa;
    
    //TODO
    private ObservableList<Patio> vehiculos;
    private Patio atri_vehiculo;
    
    
    
    
    //agregar contenido a el combobox TIPO VEHICULO
    private ObservableList<TipoVehiculo> lista_tipo_de_veiculo = FXCollections.observableArrayList();
    
    //codigo para el combox TARIFA.
    private ObservableList<Tarifas> lista_tarifas = FXCollections.observableArrayList();
    
    private ObservableList<Cliente> obs_clientes;
    
    //tabla
    @FXML
    private TextField txt_filtrar_cliente;
    @FXML
    private TableView<Cliente> tbl_cliente;
    @FXML
    private TableColumn<Cliente, Integer> col_nombre;
    @FXML
    private TableColumn<Cliente, String> col_apellido;
    @FXML
    private TableColumn<Cliente, String> col_celular;
    @FXML
    private TableColumn<Cliente, String> col_celdula;
    @FXML
    private CheckBox checkbox_cliente_casual;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		
	//cargar la lista al momento de crear la ventana.
	obs_clientes = FXCollections.observableArrayList();
        
        //todo eso es de javafx. //asociar que colomna va con que atributo.
        this.col_nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.col_apellido.setCellValueFactory(new PropertyValueFactory("apellido"));
	this.col_celular.setCellValueFactory(new PropertyValueFactory("celular"));
	this.col_celdula.setCellValueFactory(new PropertyValueFactory("cedula"));
	
	//traer los datos
	Cliente obj_cliente = new Cliente();
	obs_clientes = obj_cliente.select_all_cliente();
	
	//cargar los datos.
	this.tbl_cliente.setItems(obs_clientes);
	
	cargar_datos_combox();
	
	checkbox_cliente_casual.setSelected(true);
	
	//desactivar la tabla pues es un clinte casual
	this.tbl_cliente.setDisable(true);
    } 
    
    
    
    public void cargar_datos_combox(){
	
    //========= combox Trifas =========>
	//cargar las tarifas de la base de datos en el combox
	Tarifas trf = new Tarifas();
	trf.select_all_tarifa();
	
	//guardar los datos en la lista.
	lista_tarifas = trf.select_all_tarifa();
	
	//recorrel la lista 
	for (int i = 0; i < lista_tarifas.size(); i++) {
	    //insertar los elementos en el combox.
	    combox_tarifa.getItems().add(lista_tarifas.get(i).getNombre_tarifa());
	}
	
    //========= combox tipo ve =========>
	
	//cargar las tarifas de la base de datos en el combox
	TipoVehiculo tpv = new TipoVehiculo();
	tpv.select_all_TipoVehiculo();
	
	//guardar los datos en la lista.
	lista_tipo_de_veiculo = tpv.select_all_TipoVehiculo();
	
	//recorrel la lista 
	for (int i = 0; i < lista_tipo_de_veiculo.size(); i++) {
	    //insertar los elementos en el combox.
	    combox_tipo_vehiculo.getItems().add(lista_tipo_de_veiculo.get(i).getStr_tipo_vehiculo());
	}
    }
    
    public void seleccionar_id_combox(int tabla, String nombre){
	
	if(tabla == 1){
	    
	    //cargar las tarifas de la base de datos en el combox
	    Tarifas trf = new Tarifas();

	    //guardar los datos en la lista.
	    lista_tarifas = trf.select_all_tarifa();

	    //recorrel la lista 
	    for (int i = 0; i < lista_tarifas.size(); i++) {

		if(nombre.equals(lista_tarifas.get(i).getNombre_tarifa())){
		    id_tarifa = lista_tarifas.get(i).getId_tarifa();
		    break;
		}
	    }
	    
	}else{
	    
	    //cargar las tarifas de la base de datos en el combox
	    TipoVehiculo tpv = new TipoVehiculo();
	    tpv.select_all_TipoVehiculo();

	    //guardar los datos en la lista.
	    lista_tipo_de_veiculo = tpv.select_all_TipoVehiculo();

	    //recorrel la lista 
	    for (int i = 0; i < lista_tipo_de_veiculo.size(); i++) {

		if(nombre.equals(lista_tipo_de_veiculo.get(i).getStr_tipo_vehiculo())){
		    id_tp_vh = lista_tipo_de_veiculo.get(i).getId_tpv();
		    break;
		}
	    }
	}


    }
    
    
    
    
    
    
    //como la otra venta esta esperando le paso la info.
    public Patio getAtri_vehiculo() {
        return atri_vehiculo;
    }

    
    public void inicializar_Atributos(ObservableList<Patio> vehiculos){
        this.vehiculos = vehiculos;
    }
    
    //inicalizar los elemtos y cargar lo que esta en la tabla para que se pueda modificar.
    public void inicializar_Atributos(ObservableList<Patio> vehiculos, Patio atri_vehiculo){
        this.vehiculos = vehiculos;
        this.atri_vehiculo = atri_vehiculo;
        this.txt_placa.setText(atri_vehiculo.getPlaca());
        this.txt_color.setText(atri_vehiculo.getColor());
        this.txt_nombre.setText(atri_vehiculo.getNombre_propietario());
	this.txt_telefono.setText(atri_vehiculo.getTelefono());
	
	//Esta variable id del vehiculo de la tabla recurrente de modo de poder modificar.
	this.id_veh_patio = atri_vehiculo.getId_veh_patio();
	this.id_tabla_patio = atri_vehiculo.getId_tabla_patio();
        
        //establecer a mi antojo el valor del combox
        this.combox_tipo_vehiculo.setValue(atri_vehiculo.getTipo_vehiculo());
        this.combox_tarifa.setValue(atri_vehiculo.getTarifa());
       
	valiar_si_es_para_modificar();
    }    
    
    private void valiar_si_es_para_modificar(){
	
	//la tabla recurrente corresponde al 2
	if(id_tabla_patio == 2){
	    
	    //deshabilitar campos que no son necesarios.
	    this.txt_nombre.setDisable(true);
	    this.txt_telefono.setDisable(true);
	    this.txt_placa.setDisable(true);
	    this.txt_color.setDisable(true);
	    this.checkbox_cliente_casual.setDisable(true);
	    this.tbl_cliente.setDisable(false);
	}
    }
    
    
    @FXML
    private void seleccionar_cliente(MouseEvent event) {
	
	//capturar el elemento de la tabla.
        Cliente obj_cliente = this.tbl_cliente.getSelectionModel().getSelectedItem();
	
	Funciones fn = new Funciones();
        
	//validar si no se ha selecionado nada.
        if(obj_cliente == null){
            
	    this.id_cliente = 0;
	    this.id_vehiculo = 0;
	    
	    fn.Disparar_alerta_error("Debes selecionar un cliente.");

        }else{
	    
	    //llenar los campos de texto con la informacion
	    this.txt_placa.setText(obj_cliente.getPlaca());
	    this.txt_color.setText(obj_cliente.getColor());
	    //this.txt_telefono.setText(obj_cliente.getCelular());
	    //this.txt_telefono.setText(obj_cliente.getCelular());
	     
	    //llenar las variables para la base de datos.
	    this.id_cliente = obj_cliente.getId_cliente();
	    this.id_vehiculo = obj_cliente.getId_vehiculo();
	    this.nombre_cliente = obj_cliente.getNombre();
	    this.placa_cliente = obj_cliente.getPlaca();
	    this.color_cliente = obj_cliente.getColor();
	    this.telefono_cliente = obj_cliente.getCelular();
	    
	    //desabilitar texfiel.
	    txt_nombre.setDisable(true);
	    txt_telefono.setDisable(true);
	    
	    //asignar los valores a los text_fied.
	    txt_nombre.setText(nombre_cliente);
	    txt_telefono.setText(telefono_cliente);
	    
	    //desactivar el checkbox.
	    checkbox_cliente_casual.setSelected(false);
    
        }
    }
    
    @FXML
    private void validar_selecion_checkbox(MouseEvent event) {
	
	Cliente obj_cliente = this.tbl_cliente.getSelectionModel().getSelectedItem();
	
	if(!checkbox_cliente_casual.isSelected()){// &&  obj_cliente == null
	    this.txt_placa.setDisable(true);
	    this.txt_color.setDisable(true);
	    this.txt_nombre.setDisable(true);
	    this.txt_telefono.setDisable(true);
	    //activar la tabla pues es un clinte recurrente
	    this.tbl_cliente.setDisable(false);
	    System.out.println("no habilitado");
	    

	    
	}else if(checkbox_cliente_casual.isSelected()){
	    
	    //desactivar la tabla pues es un clinte secular
	    this.tbl_cliente.setDisable(true);
	    
	    System.out.println("si habilitado");
	    this.txt_placa.setDisable(false);
	    this.txt_color.setDisable(false);
	    this.txt_nombre.setDisable(false);
	    this.txt_telefono.setDisable(false);
	    
	    //borrar variables globales.
	    this.id_cliente = 0;
	    this.id_vehiculo = 0;
	    this.nombre_cliente = "";
	    this.placa_cliente = "";
	    this.color_cliente = "";
	    this.telefono_cliente = "";
	    
	    //resetear los textfiel
	    this.txt_nombre.setText("");
	    this.txt_telefono.setText("");
	    this.txt_placa.setText("");
	    this.txt_color.setText("");
	}
    }
    
    
    public String capturar_fecha_ingreso() {
        //este se debe guardar en la base de datos.
        //String dateTime = DateTimeFormatter.ofPattern("MMM dd yyyy").format(LocalDateTime.now());
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        //recorgar de dateTime la fecha para que solo sea la fecha y no la hora
        String fecha_real = dateTime.substring(0, 10);

            //System.out.println(fecha_real);
       
        return fecha_real;
    }
    
    public String capturar_hora_ingreso() {
        String dateTime = DateTimeFormatter.ofPattern("hh:mm:ss a").format(LocalDateTime.now());
        return dateTime;
    }

       
    
    
    @FXML
    private void agregar_vehiculo(ActionEvent event) {
	
	
	Funciones fn = new Funciones();
	
    //variales necesarias;

	//capturar datos combobox para la tabla
        String str_tipo_vehiculo = combox_tipo_vehiculo.getValue();
	String str_tarifa = combox_tarifa.getValue();
	
	//capturar id para la base de datos, se debe mejorar esta funcion para calgar los dos ids
	seleccionar_id_combox(1, combox_tarifa.getValue());
	seleccionar_id_combox(2, combox_tipo_vehiculo.getValue());
            
        //capturar la fecha de ingreso.         
        String str_fecha_entrada = capturar_fecha_ingreso();
        String str_hora_entrada = capturar_hora_ingreso();
	System.out.println(str_fecha_entrada + " - " + str_hora_entrada);
	
	String str_placa = txt_placa.getText().toUpperCase();
        String str_color = txt_color.getText();
        String str_propietario= txt_nombre.getText();
	String str_tel = txt_telefono.getText();
	
	//validar si tenemos el cliente y su informacion.
	if(this.id_cliente == 0){
	                
	    
            
        
            Patio obj_vehiculo = new Patio(str_placa, str_color, str_tipo_vehiculo, str_propietario, str_fecha_entrada, str_hora_entrada, str_tarifa, str_tel);
	    
	    
            if(!vehiculos.contains(obj_vehiculo)){
            
		    //======= modificar =======.
                    if(this.atri_vehiculo != null){
                        this.atri_vehiculo.setPlaca(str_placa);
                        this.atri_vehiculo.setColor(str_color);
                        this.atri_vehiculo.setNombre_propietario(str_propietario);
                        this.atri_vehiculo.setTipo_vehiculo(str_tipo_vehiculo);
                        this.atri_vehiculo.setTarifa(str_tarifa);
			this.atri_vehiculo.setTelefono(str_tel);
			
			//la fecha y hora de entrada no se modifica.
			int respuesta = obj_vehiculo.update_vehiculo_patio(id_veh_patio, 1, str_placa, str_color, str_propietario, str_tel, id_tarifa, id_tp_vh, id_cliente, id_vehiculo);
			//int id_veh_patio, int patio, String placa, String color, String nombre, String tel,  int tarifa, int tipo_veh, int id_cli, int id_veh
						
			//validar si los datos se guardaron correctamente.
			if(respuesta == 1){
			    //this.mostrar_datos();
			    fn.Disparar_alerta_info("Vehiculo actualizado recurente");
			}else{
			    fn.Disparar_alerta_info("Error al actualizar Recurrente");
			}
    
		    //======= insertar =======.
                    }else{
			
			//ahora los datos se guardan desde la ventana principal.
                        this.atri_vehiculo = obj_vehiculo;
                        
			fn.Disparar_alerta_info("se añadio el registro.");
  
			
			//El modelo se encarga de validar los valores nulos y asi mismo guarda los datos segun tengamos cliente y vehiculo.
			int respuesta = obj_vehiculo.insertar_vehiculo(id_vehiculo, str_placa, str_color, str_fecha_entrada, str_hora_entrada, id_tarifa, id_tp_vh, id_cliente, str_propietario, str_tel);
                            
                            //##CReAR EL TIKECT##
                            PrinterMatrix obj_pinter = new PrinterMatrix();
                            String nom_fac = "00455";
                            String nom_novio = "Siclo Part";
                            String nom_novia = "El mejor Sistema de parqueadero";
                            
                            Extenso e = new Extenso();
                            e.setNumber(BigDecimal.TEN);
                            
                            obj_pinter.setOutSize(9, 32);
                            obj_pinter.printCharAtCol(1, 1, 32, "=");
                            obj_pinter.printTextWrap(1, 2, 8, 32, "FACTURA DE VENTA");
                            obj_pinter.printTextWrap(2, 3, 1, 32, "Numero de factura"+nom_fac);
                            obj_pinter.printTextWrap(3, 3, 1, 32, "Fecha De Emision: 01/01/23");
                            obj_pinter.printTextWrap(4, 3, 1, 32, "Hora: 12:22 Pm");
                            obj_pinter.printTextWrap(5, 3, 1, 32, "Novio"+nom_novio);
                            obj_pinter.printTextWrap(6, 3, 1, 32, "Novia"+nom_novia);

                            obj_pinter.toFile("Impresion.txt");
                                    
                            FileInputStream inputStream = null;
                            
                            try{
                                inputStream = new FileInputStream("Impresion.txt");
                            } catch (FileNotFoundException ex){
                                ex.printStackTrace();
                            }
                            
                            if (inputStream == null){
                                return;
                            }
                            
                            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
                            Doc document = new SimpleDoc(inputStream, docFormat, null);
                            
                            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
                            PrintService defaulPrintServices = PrintServiceLookup.lookupDefaultPrintService();
                            
                            if (defaulPrintServices!=null){
                                DocPrintJob printJob = defaulPrintServices.createPrintJob();
                                
                                try{
                                    printJob.print(document, attributeSet);
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }else{
                                System.err.println("No hay impresora instalada!!!");
                            }
                        
                        //### Generar codigo de barras.
                        try{
                            Document pagina = new Document();
                            String ruta = System.getProperty("user.home");
                            
                            PdfWriter archivo = PdfWriter.getInstance(pagina, new FileOutputStream(ruta+"/Desktop/cdb.pdf"));
                            pagina.open();
                            
                            Barcode128 codigo128 = new Barcode128();
                            codigo128.setCode("13456789");
                            Image imagen = codigo128.createImageWithBarcode(archivo.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                            imagen.scalePercent(200);
                            
                            pagina.add(imagen);
                            pagina.close();
                            
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                                
                    
                        
                                    
			//validar si los datos se guardaron correctamente.
			if(respuesta == 1){
			    //this.mostrar_datos();
			    fn.Disparar_alerta_info("VALOR GUARDADO.");
			}else{
			    fn.Disparar_alerta_info("Error Al GUARDAR");
			}
                        

                    }
                    
                //cerrar la ventana modal.
                Stage stage = (Stage) this.btn_aceptar.getScene().getWindow();
                stage.close();
                
            }else{
		fn.Disparar_alerta_info("El vehiculo ya existe.");
            }
	
	}else{
	    //es por que si tenemos cliente y vehiculo.
	    
	    Patio obj_vehiculo = new Patio(placa_cliente, color_cliente, str_tipo_vehiculo, nombre_cliente, str_fecha_entrada, str_hora_entrada, str_tarifa, telefono_cliente);
	    	     
	   
            if(!vehiculos.contains(obj_vehiculo)){
            
		    //======= modificar =======.
                    if(this.atri_vehiculo != null){
                        this.atri_vehiculo.setPlaca(str_placa);
			this.atri_vehiculo.setColor(str_color);
			this.atri_vehiculo.setNombre_propietario(str_propietario);
			this.atri_vehiculo.setTipo_vehiculo(str_tipo_vehiculo);
			this.atri_vehiculo.setTarifa(str_tarifa);
			this.atri_vehiculo.setTelefono(str_tel);
			
                        //la fecha y hora de entrada no se modifica.
			int respuesta = obj_vehiculo.update_vehiculo_patio(id_veh_patio, 2, null, null, null, null, id_tarifa, id_tp_vh, id_cliente, id_vehiculo);		
						
			//validar si los datos se guardaron correctamente.
			if(respuesta == 1){
			    //this.mostrar_datos();
			    fn.Disparar_alerta_info("Vehiculo actualizado recurente");
			}else{
			    fn.Disparar_alerta_info("Error al actualizar Recurrente");
			}
			
			
		    //======= insertar =======.
                    }else{

                        this.atri_vehiculo = obj_vehiculo;
                        
			fn.Disparar_alerta_info("se añadio el registro.");
			
			//El modelo se encarga de validar los valores nulos y asi mismo guarda los datos segun tengamos cliente y vehiculo
			int respuesta = obj_vehiculo.insertar_vehiculo(id_vehiculo, null, null, str_fecha_entrada, str_hora_entrada, id_tarifa, id_tp_vh, id_cliente, null, null);
			

			//validar si los datos se guardaron correctamente.
			if(respuesta == 1){
			    //this.mostrar_datos();
			    fn.Disparar_alerta_info("VALO GUARDADO. Recurrente");
			}else{
			    fn.Disparar_alerta_info("Error Al GUARDAR Recurrente");
			}
                        

                    }
                    
                //cerrar la ventana modal.
                Stage stage = (Stage) this.btn_aceptar.getScene().getWindow();
                stage.close();
                
            }else{
		fn.Disparar_alerta_info("El vehiculo ya existe.");
            }
	    
	}
     
      
        
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
    }







    
    
}
