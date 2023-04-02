package application;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainController mnController = new MainController();
        mnController.setStage(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean checkIfOtherAttribut(String str){
        Pattern pattern = Pattern.compile("[-+#]", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
