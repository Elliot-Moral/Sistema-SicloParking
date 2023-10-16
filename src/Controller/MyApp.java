
package Controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MyApp extends Application { //debo heredar de la clase application
    public void start(Stage primaryStage) throws IOException{  //hay que crear un metodo astractro llamado start() y poner eso como parametro, obligatorio.
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Login.fxml"));
        Parent root = loader.load();

        Scene escena = new Scene(root);
        primaryStage.setScene(escena);
        LoginController controlador_login = loader.getController();
        controlador_login.setEsenario(primaryStage);
	
	primaryStage.setTitle("Login");

        primaryStage.show();

        //StackPane root = new StackPane();   //este es una clase StackPane que seria el nodo raiz o contenedor raiz.
        // Scene scene = new Scene(root, 320, 250); //Scene Tiene varios 6 constrcutores, pero necesita un nodo rais, un objeto que como defatul se llama root
        // Label label = new Label("hola mundo");  // crear un label del java.scene.control necesariamente tiene que ser este.
        // //ahora toca agregar el label al objeto raiz para que se muestre.
        // root.getChildren().add(label); // se agrego el label al nodo raiz
        // //ya se tiene todo lo necesario ahora solo hace falta mostrar la interfas y se llama a primvaryStage
        // primaryStage.setScene(scene); //agregar la escena a al esenario
        // primaryStage.setTitle("Primera ventana"); //poner el titulo.
        // primaryStage.show(); //ahora hay que mostrar la ventana
    }
    public static void main(String[] args) {
        launch(args); //este metodo me llama a el metodo start()
    }
}
