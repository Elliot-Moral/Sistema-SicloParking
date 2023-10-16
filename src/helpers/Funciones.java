/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import javafx.scene.control.Alert;

/**
 *
 * @author Elliot Moral
 */
public class Funciones {

    public Funciones() {
    }
    
    public void Disparar_alerta_info(String mensaje){
	
	Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);//se ve mejor asi.
        alerta.setTitle("Info");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public void Disparar_alerta_error(String mensaje){
	
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setHeaderText(null);//se ve mejor asi.
	alerta.setTitle("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public static String formatearNumero(int numero) {
        
        String numeroStr = Integer.toString(numero);
        int longitud = numeroStr.length();
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            resultado.append(numeroStr.charAt(i));
            int posicionDesdeElFinal = longitud - i - 1;
            if (posicionDesdeElFinal % 3 == 0 && posicionDesdeElFinal != 0) {
                resultado.append(".");
            }
        }

        return resultado.toString();
    }
}


