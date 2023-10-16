
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    private Stage stage;
    private String str_user;
    private String str_password;
    @FXML
    private Button bnt_login;
    @FXML
    private TextField txt_user;
    @FXML
    private TextField txt_password;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
    public void monstrar_ventana_principal() throws IOException {
   	
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Principal.fxml"));
	Parent root = loader.load();
	Scene escena = new Scene(root);
	Stage stage = new Stage();
	stage.setScene(escena); 
	stage.setTitle("Siclo Part");
	stage.show();
	this.stage.close();
    }    
    
    public void setEsenario(Stage primaryStage) {
        stage = primaryStage;
    }

    @FXML
    private void validar_datos(ActionEvent event) throws IOException {
        
        //manejar mejor las variables.
        String str_user = txt_user.getText();
        String str_pass = txt_password.getText();
        
        //validar las credenciales
        /*        if ( str_user.equals("moral") && str_pass.equals("moral")){
        monstrar_ventana_principal();
        }else{
        System.out.println("Oh Madre mia algo salio mal " + str_user);
        }
        */
        monstrar_ventana_principal();
    }

    
    
}
