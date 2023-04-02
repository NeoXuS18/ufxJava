package controller;

import application.JavaWriter;
import application.UFXReader;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Classe;

import java.io.File;
import java.util.ArrayList;

public class MainController {

    public static Boolean getterAndSetter = false;
    public static Boolean constructor = false;
    private Stage stage;
    @FXML
    private Label labelPath;

    @FXML
    private CheckBox getterAndSetterCheckbox;

    @FXML
    private CheckBox constructorCheckBox;
    @FXML
    private Label labelPathUFXFile;

    private String pathToExport;

    private String pathOfuxfFile;



    @FXML
    public void submitForm() {
        ArrayList<Classe> elements = UFXReader.read("C:\\Users\\Antonin\\Desktop\\Code\\CDA\\ufxJava\\src\\main\\resources\\Diagramme Médiathèque sa mère.uxf");

        if (pathOfuxfFile != null){
            if (getterAndSetterCheckbox.isSelected()){
                getterAndSetter = true;
            }else {
                getterAndSetter = false;
            }
            if (constructorCheckBox.isSelected()){
                constructor = true;
            }else {
                constructor = false;
            }
            if(pathToExport != null){
//                JavaWriter.writeJava(pathToExport, elements);
            }else {
                labelPath.setText("Merci de renseigner un dossier");
            }
        }else {
            labelPathUFXFile.setText("Merci de renseigner un fichier uxf");
        }
    }

    @FXML
    public void directoryChooserHandle() {
        DirectoryChooser drChooser = new DirectoryChooser();
        File selectedDirectory = drChooser.showDialog(stage);
        if (selectedDirectory != null){
            pathToExport = selectedDirectory.getAbsolutePath();
            labelPath.setText(selectedDirectory.getName());
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void fileUFXHandle(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.uxf"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null){
            pathOfuxfFile = selectedFile.getAbsolutePath();
            labelPathUFXFile.setText(selectedFile.getName());
        }

    }

}