package pl.javastart.mp3player.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPaneController {
    @FXML
    private MenuItem fileMenuItem;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    public MenuItem getFileMenuItem() {
        return fileMenuItem;
    }

    public MenuItem getDirMenuItem() {
        return dirMenuItem;
    }


    public void initialize() {
        closeMenuItem.setOnAction(actionEvent -> Platform.exit());

        aboutMenuItem.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/fxml/aboutPane.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("About");
                    stage.show();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}