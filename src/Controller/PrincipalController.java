
package Controller;

import Model.Patio;
import Model.Tarifas;
import helpers.Funciones;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

    @FXML
    private Button bnt_parqueadero;
    @FXML
    private Button bnt_contabilidad;
    @FXML
    private Button bnt_configuracion;
    @FXML
    private Pane pnl_parqueadero;
    @FXML
    private Pane pnl_contabilidad;
    @FXML
    private Pane pnl_configuracion;
    @FXML
    private Button bnt_salir;
    @FXML
    private Button bnt_agragar;
    @FXML
    private Button bnt_modificar;
    @FXML
    private Button bnt_eliminar;
    @FXML
    private Button bnt_clientes;
    @FXML
    private Button bnt_despachar;
    @FXML
    private Label label_tiempo;
    @FXML
    private Label label_precio;
    @FXML
    private Label label_cantidad_carros;
    @FXML
    private Label label_cantidad_motos;
    @FXML
    private Label label_cantidad_ciclas;
    @FXML
    private Label label_placa;
    
    
    //========= TABLA Y COLOMNAS ========
    
    @FXML
    private TableView<Patio> tbl_principal;
    @FXML
    private TableColumn<Patio, Integer> col_numeral;
    @FXML
    private TableColumn<Patio, String> col_placa;
    @FXML
    private TableColumn<Patio, String> col_entrada;
    @FXML
    private TableColumn<Patio, String> col_hora;
    @FXML
    private TableColumn<Patio, String> col_tafifa;
    @FXML
    private TableColumn<Patio, String> col_tipo_vehiculo;
    @FXML
    private TableColumn<Patio, String> col_color;
    @FXML
    private TableColumn<Patio, String> col_nombre;
    
    //es como un array pero propia de javafx
    private ObservableList<Patio> vehiculos;


    

    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
		
	//esto es un observable lIST
        //vehiculos = FXCollections.observableArrayList();
        //this.tbl_principal.setItems(vehiculos);
	
        //asociar que colomna va con que atributo.
        this.col_numeral.setCellValueFactory(new PropertyValueFactory("indice"));
        this.col_placa.setCellValueFactory(new PropertyValueFactory("placa"));
        this.col_entrada.setCellValueFactory(new PropertyValueFactory("fecha_entrada"));
        this.col_hora.setCellValueFactory(new PropertyValueFactory("hora_entrada"));
        this.col_tafifa.setCellValueFactory(new PropertyValueFactory("tarifa"));
        this.col_color.setCellValueFactory(new PropertyValueFactory("color"));
        this.col_tipo_vehiculo.setCellValueFactory(new PropertyValueFactory("tipo_vehiculo"));
        this.col_nombre.setCellValueFactory(new PropertyValueFactory("nombre_propietario"));
	
	monstrar_datos_patio();
    }    

    @FXML
    private void poner_panel(ActionEvent event) {
        if(event.getSource() == bnt_parqueadero){
            pnl_parqueadero.toFront();
        }else if(event.getSource() == bnt_contabilidad){
            pnl_contabilidad.toFront();
        }
        else if(event.getSource() == bnt_configuracion){
            pnl_configuracion.toFront();
        }
    }
    
    public void	monstrar_datos_patio(){
    	Patio ob_vehiculos = new Patio();
	vehiculos = ob_vehiculos.select_all_patio();
	this.tbl_principal.setItems(vehiculos);
    }

    @FXML
    private void salir(ActionEvent event) {
    }
    
    
    @FXML
    private void agregar(ActionEvent event) throws IOException {
	
        //cargar la vista_modal.
        FXMLLoader cargar_vista = new FXMLLoader(getClass().getResource("/Vista/AddVehiculo.fxml"));
        Parent root = cargar_vista.load();
        
        //cargar el controlador
        AgregarVehiculoController controlador = cargar_vista.getController();
        controlador.inicializar_Atributos(vehiculos);
        
        //crear la escena
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setScene(scene);
         stage.showAndWait();
         
         //obtener los datos de la ventana modal
        Patio obj_vehiculo = controlador.getAtri_vehiculo();
        //ojo porque en el momento esta nulo ver unas lineas arriba
        this.monstrar_datos_patio();

    }

    @FXML
    private void modificar(ActionEvent event) throws IOException {
        
        //capturar el elemento de la tabla.
        Patio obj_vehiculo = this.tbl_principal.getSelectionModel().getSelectedItem();
        
        if(obj_vehiculo == null){
            
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText(null);//se ve mejor asi.
            alerta.setTitle("Error");
            alerta.setContentText("Debes selecionar un vehiculo.");
            alerta.showAndWait();

        }else{
            //cargar la vista_modal.
            FXMLLoader cargar_vista = new FXMLLoader(getClass().getResource("/Vista/AddVehiculo.fxml"));
            Parent root = cargar_vista.load();

            //cargar el controlador
            AgregarVehiculoController controlador = cargar_vista.getController();
            controlador.inicializar_Atributos(vehiculos, obj_vehiculo);

            //crear la escena
             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.initModality(Modality.APPLICATION_MODAL);
             stage.setScene(scene);
             stage.showAndWait();
	     
             
             Patio aux = controlador.getAtri_vehiculo();
                if (aux != null) {
                    this.tbl_principal.refresh();
                }
        }
        
    }

    @FXML
    private void eliminar(ActionEvent event) {
        
        Patio obj_vehiculo = this.tbl_principal.getSelectionModel().getSelectedItem();
        Funciones fn = new Funciones();
	
        if(obj_vehiculo == null){
           
	    fn.Disparar_alerta_error("Debes selecionar un vehiculo.");

        }else{
	    Patio obj_v = new Patio();
	    
	    int tabal_patio = obj_vehiculo.getId_tabla_patio();
	    int id_veh_patio = obj_vehiculo.getId_veh_patio();
	    	    fn.Disparar_alerta_error("tabla = "+tabal_patio + " Registro "+id_veh_patio);

                        			
	    //El modelo se encarga de validar el id del patio y asi mismo validar de que tabla eliminar el registro.
	    int respuesta = obj_v.delete_vehiculo_patio(tabal_patio, id_veh_patio);
	    this.vehiculos.remove(obj_vehiculo);
	    this.tbl_principal.refresh();
	    
	    if(respuesta == 1){
		fn.Disparar_alerta_info("Vehiculo Eliminado.");
	    }else{
	    	fn.Disparar_alerta_error("Error al eliminar el vehiculo.");
	    }

            label_tiempo.setText("0");
            label_placa.setText("PLACA");

        }
    }
    
    
    
    @FXML
    private void selecionar(MouseEvent event) {
        //Devolver el registro selecionado de la tabla.
        Patio obj_vehiculo = this.tbl_principal.getSelectionModel().getSelectedItem();
        
        if(obj_vehiculo != null){
            this.label_placa.setText(obj_vehiculo.getPlaca().toUpperCase());
        }
	
        //traer las fecha y hora de ENTRADA!!! del modelo.
        //el calculo del cobro se realiza desde este mismo metodo por eso se pido la tarifa.
        calcular_tiempo(obj_vehiculo.getHora_entrada(), obj_vehiculo.getFecha_entrada(), obj_vehiculo.getTarifa());
        
    
    }
    
    private void calcular_tiempo(String hora_entrada, String fecha_entrada, String tipo_tarifa){
        
        
        //convertir de 12 horas a 24 horas Necesario.
        int int_hora_en_12h = Integer.parseInt(hora_entrada.substring(0,2));
        String formato_hora_entrada = hora_entrada.substring(9,11);
        
        //Hora de ENTRADA sepmentada.
        int int_hora_entrada = convertir_12horas_a_24horas(int_hora_en_12h, formato_hora_entrada);
        int int_minuto_entrada = Integer.parseInt(hora_entrada.substring(3,5));
        int int_segundo_entrada = Integer.parseInt(hora_entrada.substring(6,8));
        
        //Fecha de ENTRADA sepmentada.
        int int_yeard_entrada = Integer.parseInt(fecha_entrada.substring(0,4));
        int int_mes_entrada = Integer.parseInt(fecha_entrada.substring(5,7));
        int int_dia_entrada = Integer.parseInt(fecha_entrada.substring(8,10));

        
        //Captuara hora y fecha de salida.
        AgregarVehiculoController obj_add_vehiculo_controller = new AgregarVehiculoController();

        
        //convertir de 12 horas a 24 horas Necesario.
        int int_hora_sa_12h = Integer.parseInt(obj_add_vehiculo_controller.capturar_hora_ingreso().substring(0, 2));
        String formato_hora_salida = obj_add_vehiculo_controller.capturar_hora_ingreso().substring(9, 11);
        
        
        //Hora de SALIDA.
        int int_hora_salida = convertir_12horas_a_24horas(int_hora_sa_12h, formato_hora_entrada);
        int int_minuto_salida = Integer.parseInt(obj_add_vehiculo_controller.capturar_hora_ingreso().substring(3,5));
        int int_segundo_salida = Integer.parseInt(obj_add_vehiculo_controller.capturar_hora_ingreso().substring(6, 8));

        
        //Fecha de SALIDA.
        int int_yeard_salida = Integer.parseInt(obj_add_vehiculo_controller.capturar_fecha_ingreso().substring(0,4));
        int int_mes_salida = Integer.parseInt(obj_add_vehiculo_controller.capturar_fecha_ingreso().substring(5,7));
        int int_dia_salida = Integer.parseInt(obj_add_vehiculo_controller.capturar_fecha_ingreso().substring(8, 10));
        
        //definir los dias de cada mes.
        int arr_dias_meses[] = new int[12];//[31,28,31,30,31,30,31,31,30,31,30,31];
        arr_dias_meses[0] = 31;
        arr_dias_meses[1] = 28;
        arr_dias_meses[2] = 31;
        arr_dias_meses[3] = 30;
        arr_dias_meses[4] = 31;
        arr_dias_meses[5] = 30;
        arr_dias_meses[6] = 31;
        arr_dias_meses[7] = 31;
        arr_dias_meses[8] = 30;
        arr_dias_meses[9] = 31;
        arr_dias_meses[10] = 30;
        arr_dias_meses[11] = 31;
        
        //Validar si el año es biciesto.
        if (int_yeard_entrada % 4 == 0 && (int_yeard_entrada % 100 != 0 || int_yeard_entrada % 400 == 0)) {
          arr_dias_meses[1] = 29;
        }
        
        //datos donde se guarda el tiempo calculado
        int base_segundo = 1;
        int base_minuto = 0;
        int base_hora = 0;
        
        //datos donde se guarda el tiempo calculado en circulo horario
        int local_hora = int_hora_entrada;
        int local_minuto = int_minuto_entrada;
        int local_segundo = int_segundo_entrada;
        
        int local_hora_2 = 0;
        int local_minuto_2 = 0;
        int local_segundo_2 = 0;


        //calcular los dias por meses diferentes
        int dias_antes_fin_mes;
        int int_total_dias;
        String calculo_total[];
        calculo_total = new String[3];

        
        //valor para calcular el cobro
        int horas_totales;
        int minutos_totales;
       
        //QUE COMIENCE EL JUEGO.
        //preguntar si la el dia de entrada es el mimo dia de salida_dia por tanto solo calcular las horas de forma lineal.
        if(int_dia_entrada == int_dia_salida && int_hora_salida >= int_hora_entrada && int_mes_entrada == int_mes_salida){

            calculo_total = calcular_horas_lineal(int_hora_entrada, int_hora_salida, int_minuto_entrada, int_minuto_salida, int_segundo_entrada, int_segundo_salida, base_hora, base_minuto, base_segundo);
            
            //monstrar las horas y minutos calculados.
            label_tiempo.setText(calculo_total[2]);
            System.out.println("mismo dia " + calculo_total[2]);
            
                    //### calcular el cobro por el mimo dia' osea solo horas, PASAR DE STRING A ENTERO.
                    horas_totales = Integer.parseInt(calculo_total[0]);
                    minutos_totales = Integer.parseInt(calculo_total[1]);
                    calcular_cobro(0, 0, horas_totales, minutos_totales, tipo_tarifa);
            
        //preguntar la hora a ver si ha pasdo un dia o menos de un dia, los dias son diferentes ejemp 2 - 3 o 2 - 5.
        }else if(int_dia_entrada != int_dia_salida && int_mes_entrada == int_mes_salida){
             
            
            if(int_hora_salida >= int_hora_entrada && int_minuto_salida >= int_minuto_entrada){//es por que ya paso un dia.
              
                //#ACA ES POR que si ha pasado un dia horas y minutos..
                
                //#calcular los dias en patio.
                      if (int_dia_entrada == 1) {
                        int_total_dias = (int_dia_salida - int_dia_entrada) + 1;
                      }else{
                        int_total_dias = int_dia_salida - int_dia_entrada;
                      }

                calculo_total = calcular_horas_lineal(int_hora_entrada, int_hora_salida, int_minuto_entrada, int_minuto_salida, int_segundo_entrada, int_segundo_salida, base_hora, base_minuto, base_segundo);
                
                //validar si ha pasdo 1 o mas dias para mostrar el mensaje de buena forma.
                if(int_total_dias == 0){
                    label_tiempo.setText(calculo_total[2]); 
                }else if(int_total_dias == 1){
                    label_tiempo.setText(int_total_dias+ " Dia " + "- "+ calculo_total[2]);               
                }else{
                    label_tiempo.setText(int_total_dias+ " Dias " + "- "+ calculo_total[2]);               
                }
               
               
                System.out.println("ya pasdo un dia por horas y minutos " + "Dias: " + int_total_dias +" - "+ int_total_dias);
                
                //### calcular el cobro por el mimo dia' osea solo horas, PASAR DE STRING A ENTERO.
                    horas_totales = Integer.parseInt(calculo_total[0]);
                    minutos_totales = Integer.parseInt(calculo_total[1]);
                    calcular_cobro(0, int_total_dias, horas_totales, minutos_totales, tipo_tarifa);
                
            }else{
                               
                //No ha pasdo un dia en horas pues la hora de salida es menor que la hora de salida.                
                
                if (int_dia_entrada == 1) {
                  int_total_dias = int_dia_salida - int_dia_entrada;
                }else{
                  int_total_dias = (int_dia_salida - int_dia_entrada) - 1;
                }
                
                calculo_total = calular_cirulo_horario(local_hora, local_minuto, local_segundo, local_hora_2, local_minuto_2, local_segundo_2, base_hora, base_minuto, base_segundo, int_hora_salida, int_minuto_salida, int_segundo_salida);

                
                //validar si ha pasdo 1 o mas dias para mostrar el mensaje de buena forma.
                if(int_total_dias == 0){
                    label_tiempo.setText(calculo_total[2]); 
                }else if(int_total_dias == 1){
                    label_tiempo.setText(int_total_dias+ " Dia " + "- "+ calculo_total[2]);               
                }else{
                    label_tiempo.setText(int_total_dias+ " Dias " + "- "+ calculo_total[2]);               
                }


                System.out.println("la hora de entrada es menor que la de salida: " + calculo_total[2]);
                
                
                //### calcular el cobro por el mimo dia' osea solo horas, PASAR DE STRING A ENTERO.
                    horas_totales = Integer.parseInt(calculo_total[0]);
                    minutos_totales = Integer.parseInt(calculo_total[1]);
                    calcular_cobro(0, int_total_dias, horas_totales, minutos_totales, tipo_tarifa);
           }

        //############### cuando es mes de entrada es diferente al mes de salida. #################################
        }else if (int_mes_entrada != int_mes_salida) {
            
            System.out.println("meses diferentes");
            //validar las horas
            if(int_hora_salida >= int_hora_entrada && int_minuto_salida >= int_minuto_entrada){//es por que ya paso un dia.

                 
                //#es por que si ha pasado un dia.
                dias_antes_fin_mes = arr_dias_meses[int_mes_entrada-1] - int_dia_entrada;
                int_total_dias = int_dia_salida + dias_antes_fin_mes;
                
                calculo_total = calcular_horas_lineal(int_hora_entrada, int_hora_salida, int_minuto_entrada, int_minuto_salida, int_segundo_entrada, int_segundo_salida, base_hora, base_minuto, base_segundo);

                //validar si ha pasdo 1 o mas dias para mostrar el mensaje de buena forma.
                if(int_total_dias == 1){
                    //monstrar las horas y minutos calculados.
                    label_tiempo.setText(int_total_dias+ " Dia " + "- "+ calculo_total[2]);               
                }else{
                    label_tiempo.setText(int_total_dias+ " Dias " + "- "+ calculo_total[2]);               
                }   
                

            }else{//no ha pasdo un dia en horas y se debe calcular todas hacienco el calcular horario.

                System.out.println("no ha pasdo un dia en horas, pues hora de salida es menor que la hora entrada");
                
                //calcular los dias.
                dias_antes_fin_mes = arr_dias_meses[int_mes_entrada-1] - int_dia_entrada;
                int_total_dias = dias_antes_fin_mes + (int_dia_salida-1);
                
                calculo_total = calular_cirulo_horario(local_hora, local_minuto, local_segundo, local_hora_2, local_minuto_2, local_segundo_2, base_hora, base_minuto, base_segundo, int_hora_salida, int_minuto_salida, int_segundo_salida);
                
                //validar si ha pasdo 1 o mas dias para mostrar el mensaje de buena forma.               
                if(int_total_dias == 0){
                    label_tiempo.setText(calculo_total[2]); 
                }else if(int_total_dias == 1){
                    label_tiempo.setText(int_total_dias+ " Dia " + "- "+ calculo_total[2]);               
                }else{
                    label_tiempo.setText(int_total_dias+ " Dias " + "- "+ calculo_total[2]);               
                }
                

            }
        }
        
          
        
        
    }//fin del metodo.
    
    private String[] calcular_horas_lineal(int hora_en, int hora_sa, int minuto_en, int minuto_sa, int segundo_en, int segundo_sa, int base_hora, int base_minuto, int base_segundo){
        int i = 0; //iterador
       
        String salida[];
        salida = new String[3];
 
        String cadena;
        
        String st_base_hora;
        String st_base_minuto;
        
        while(i <  100000){ //con este bucle debo calcular el tiempo que trascurre en un determinada hora o otra hora?
            segundo_en++;
            base_segundo++;

            if(hora_en == hora_sa && minuto_en == minuto_sa && segundo_en >= segundo_sa){
                break;
            }else{
                    if(segundo_en > 59){
                       segundo_en = 0;
                       minuto_en++;

                            if(minuto_en > 59){
                                  minuto_en = 0;
                                  hora_en++;

                            }else if (base_minuto > 59) {
                                base_minuto = 0;
                                base_hora++;
                            }

                    }else if (base_segundo > 59) {
                      base_segundo = 0;
                      base_minuto++;
                    }
            }
            i++; //total de segundos calulados.
        }
        
        //representar 60 minutosa 1 hora.
        if(base_minuto == 60){
          base_minuto = 0;
          base_hora++;
        }
        
        //Estructurar los Textos Para horas y minutos.
        if(base_hora <= 0){
            cadena = base_minuto + " Minutos";
        }else{
            cadena = base_hora+" Horas " + base_minuto + " Minutos";
        }
        
        //COBROS agregar los elementos al arreglo para hacer los cobros.
        salida[0] = st_base_hora = base_hora+"";
        salida[1] = st_base_minuto = base_minuto+"";
        salida[2] = cadena;
        return salida;
    }
    
    private String[] calular_cirulo_horario(int local_hora, int local_minuto, int local_segundo, int local_hora_2, int local_minuto_2, int local_segundo_2,int base_hora, int base_minuto, int base_segundo, int hora_sa, int minuto_sa, int segundo_sa ){
        int h_24 = 0; //iterador de hora de entrada hasta la medioa noche 24
        int h_hs = 0; //iterador de las 0 horas a la hora de salida
        
  
        //El resultado se guarda como string.
        String[] salida = new String[3];
        String tiempo_en_texto;
        //calcular las horas de entrada hasta media noche 24:00
        while(h_24 <  100000){
            local_segundo++;
            base_segundo++;

            if( local_hora == 24 && local_minuto == 0 && local_segundo >= 0){
                break;
            }else{
                    if(local_segundo > 59){
                        local_segundo = 0;
                        local_minuto++;

                            if(local_minuto > 59){
                              local_minuto = 0;
                              local_hora++;

                            }else if (base_minuto > 59) {
                              base_minuto = 0;
                              base_hora++;
                            }

                    }else if (base_segundo > 59) {
                      base_segundo = 0;
                      base_minuto++;
                    }

            }
            h_24++; //total de segundos calulados.
        }
        
        //calcular las horas de las 0:00 hasa la hora de salida.
        while(h_hs <  100000){
          local_segundo_2++;
          base_segundo++;

          if( local_hora_2 >= hora_sa && local_minuto_2 >= minuto_sa && local_segundo_2 >= segundo_sa){
                break;
            }else{
                    if(local_segundo_2 > 59){
                        local_segundo_2 = 0;
                        local_minuto_2++;

                            if(local_minuto_2 > 59){
                              local_minuto_2 = 0;
                              local_hora_2++;

                            }else if (base_minuto > 59) {
                              base_minuto = 0;
                              base_hora++;
                            }
                    }else if (base_segundo > 59) {
                      base_segundo = 0;
                      base_minuto++;
                    }
            }
            h_hs++;
        }
       
      //Estructurar los Textos Para horas y minutos.
      if(base_hora <= 0){
          tiempo_en_texto = base_minuto + " Minutos";
      }else{
          tiempo_en_texto = base_hora+" Horas " + base_minuto + " Minutos";
      }
      
      //asignar los valores para ser caculados
      salida[0] = base_hora+"";
      salida[1] = base_minuto+"";
      salida[2] = tiempo_en_texto;

      return salida;
    }
    
    private int convertir_12horas_a_24horas(int hora, String formato_hora){
            
        if(formato_hora.equals("PM") || (hora >= 12 &&  formato_hora.equals("PM") )){
            switch (hora){
                case 12:  hora = 12;
                         break;
                case 1:  hora = 13;
                         break;
                case 2:  hora = 14;
                         break;
                case 3:  hora = 15;
                         break;
                case 4:  hora = 16;
                         break;
                case 5:  hora = 17;
                         break;
                case 6:  hora = 18;
                         break;
                case 7:  hora = 19;
                         break;
                case 8:  hora = 20;
                         break;
                case 9:  hora = 21;
                         break;
                case 10:  hora = 22;
                         break;
                case 11:  hora = 23;
                         break;
            }
        }else if(formato_hora.equals("AM") && hora == 12){
            hora = 0;
        }
        return hora;
    }

    public void calcular_cobro (int meses, int dias, int horas, int minutos, String tipo_tarifa){
        
        ArrayList<String> lista_nombres = new  ArrayList<String>();
        int cobrar = 0;
        
        //calcular solo horas y minutos.
        Tarifas trf = new Tarifas();
        
        //consultar en la base de datos valor de la tarifa.
        int valor_tarifa = trf.consultar_precio_de_tarifa(tipo_tarifa);

        //hacer operacion
        switch (tipo_tarifa){
                case "Por Hora":  
                    
                    //validar dias
                    int horas_totales_de_dias = dias * 24;
                    //asignar las horas calculadas a la variabel horas
                    horas = horas + horas_totales_de_dias;
                    
                    cobrar = horas * valor_tarifa;
                    int valor_por_minu = valor_tarifa / 60;
                    cobrar = cobrar + valor_por_minu;
                    
                break;
                    
                case "Por Minuto":  
                     
                    //validar dias a minutos
                    int minutos_totales_de_dias = dias * 1440;
                    int horas_a_minutos = horas * 60;
                    int total_minutos = horas_a_minutos + minutos; 
                    //asignar los minutos de dias como suma de todos los minutos
                    total_minutos = total_minutos + minutos_totales_de_dias;
                    cobrar =  total_minutos * valor_tarifa;
                    
                break;
                
               
                case "Por Mes":  
                    
                    cobrar = meses * valor_tarifa;
                
                break;
                
                case "Por Dia":  
                    
                    cobrar = dias * valor_tarifa;
                
                break;
        }
        //cobro solo por horas
        //int cobro_hora = horas*valor_tarifa;
        
        Funciones fn = new Funciones();
        String numeroFormateado = fn.formatearNumero(cobrar);
        
        System.out.println("EL VALOR A COBRAR ES " + cobrar);
        label_precio.setText(numeroFormateado+"");
    }
    
    
    

    
        @FXML
    private void gestionar_clientes(ActionEvent event) throws IOException {
        //cargar la vista_modal.
        FXMLLoader cargar_vista = new FXMLLoader(getClass().getResource("/Vista/GestionClientes.fxml"));
        Parent root = cargar_vista.load();
        
        //cargar el controlador
        GestionClientesController controlador = cargar_vista.getController();
        
        //crear la escena
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setScene(scene);
         stage.showAndWait();
    }
    
    @FXML
    private void despachar(ActionEvent event) {
        //primero hay que ennviar los datos de la tabla principal en la base de datos tabla despacho.
        Patio obj_vehiculo = this.tbl_principal.getSelectionModel().getSelectedItem();
        Funciones fn = new Funciones();
	
        if(obj_vehiculo == null){
           
	    fn.Disparar_alerta_error("Debes selecionar un vehiculo.");

        }else{
	    Patio obj_v = new Patio();
	    
	    int tabal_patio = obj_vehiculo.getId_tabla_patio();
	    int id_veh_patio = obj_vehiculo.getId_veh_patio();
	    	    fn.Disparar_alerta_error("tabla = "+tabal_patio + " Registro "+id_veh_patio);
                   			
	    //El modelo se encarga de validar el id del patio y asi mismo validar de que tabla eliminar el registro.
	    int respuesta = obj_v.delete_vehiculo_patio(tabal_patio, id_veh_patio);
	    this.vehiculos.remove(obj_vehiculo);
	    this.tbl_principal.refresh();
	    
	    if(respuesta == 1){
		fn.Disparar_alerta_info("Vehiculo Eliminado.");
	    }else{
	    	fn.Disparar_alerta_error("Error al eliminar el vehiculo.");
	    }

            label_tiempo.setText("0");
            label_placa.setText("PLACA");

        }
    }
    

    
    
    
    // ============== ventana de contabilidad =================.
    @FXML
    private TextField txt_buscar;
    @FXML
    private DatePicker filtrar_fecha;
    @FXML
    private TableColumn<?, ?> col_numerador;
    @FXML
    private TableColumn<?, ?> col_salida;
    @FXML
    private TableColumn<?, ?> col_total_horas;
    @FXML
    private TableColumn<?, ?> col_tarifa;
    @FXML
    private TableColumn<?, ?> col_propietario;
    @FXML
    private TableColumn<?, ?> col_precio;
    @FXML
    private Button bnt_ver_reportes;
    @FXML
    
    
    
	// ============== Metodos =================.
	private void ver_reportes(ActionEvent event) {
	}
    
    
    
    
    // ============== ventana de contabilidad =================.
    @FXML
    private void abrir_tarifas(MouseEvent event) throws IOException {
        //cargar la vista_modal.
        FXMLLoader cargar_vista = new FXMLLoader(getClass().getResource("/Vista/ConfiguracionTarifas.fxml"));
        Parent root = cargar_vista.load();
        
        //cargar el controlador
        ConfiguracionTarifasController controlador = cargar_vista.getController();
        
        //crear la escena
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         stage.setScene(scene);
         stage.showAndWait();
    }




    
}
