package application;

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class Main extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}


import java.io.File;

public class Main {

    public static void main(String[] args) {
        File f = new File("C:\\Users\\17010-27-09\\Documents\\ProjetCDA");
        if(f.exists()){
            System.out.println("Exist");
        }else {
            System.out.println("Does not exists");
        }
    }
}