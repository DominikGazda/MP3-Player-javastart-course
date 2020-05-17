package pl.javastart.mp3player.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.javastart.mp3player.mp3.Mp3Parser;
import pl.javastart.mp3player.mp3.Mp3Song;
import pl.javastart.mp3player.player.Mp3Player;

import java.io.File;

public class MainController {
    @FXML
    private ContentPaneController contentPaneController;
    @FXML
    private ControlPaneController controlPaneController;
    @FXML
    private MenuPaneController menuPaneController;

    private Mp3Player mp3Player;

    public void initialize() {
        createPlayer();
        configureTableClick();
        configureButtons();
        configureMenu();
        showMessage("Witaj w programie Mp3Player");
    }

    private void createPlayer() {
        ObservableList<Mp3Song> mp3List = contentPaneController.getContentTable().getItems();
        mp3Player = new Mp3Player(mp3List);
    }

    private void configureTableClick() {
        TableView<Mp3Song> tableView = contentPaneController.getContentTable();
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                playSelectedSong(selectedIndex);
            }
        });
    }

    private void playSelectedSong(int selectedIndex) {
        mp3Player.loadSong(selectedIndex);
        configureProgressBar();
        configureVolume();
        controlPaneController.getPlayButton().setSelected(true);
    }

    private void configureProgressBar() {
        Slider progressSlider = controlPaneController.getProgressSlider();
        mp3Player.getMediaPlayer().setOnReady(() -> progressSlider.setMax(mp3Player.getLoadedSongLength()));
        mp3Player.getMediaPlayer().currentTimeProperty().addListener((arg, oldVal, newVal) -> {
            progressSlider.valueProperty().setValue(newVal.toSeconds());

        });
        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (progressSlider.isValueChanging()) {
                mp3Player.getMediaPlayer().seek(Duration.seconds(newValue.doubleValue()));
            }
        });

    }

    private void configureVolume() {
        Slider volumeSlider = controlPaneController.getVolumeSlider();
        volumeSlider.valueProperty().unbind();
        volumeSlider.setMax(1.0);
        volumeSlider.valueProperty().bindBidirectional(mp3Player.getMediaPlayer().volumeProperty());
    }

    private void configureButtons() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        ToggleButton playButton = controlPaneController.getPlayButton();
        Button nextButton = controlPaneController.getNextButton();
        Button previousButton = controlPaneController.getPreviousButton();

        playButton.setOnAction(actionEvent -> {
            if (playButton.isSelected()) {
                mp3Player.play();
            } else {
                mp3Player.stop();
            }
        });
        nextButton.setOnAction(actionEvent -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() + 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());

        });
        previousButton.setOnAction(actionEvent -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() - 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });
    }

    private void configureMenu()  {
        MenuItem openFile = menuPaneController.getFileMenuItem();
        MenuItem openDirectory = menuPaneController.getDirMenuItem();

           openFile.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mp3", "*.mp3"));
            File file = fc.showOpenDialog(new Stage());

            try {
                contentPaneController.getContentTable().getItems().add(Mp3Parser.createMp3FromPath(file));
            } catch (Exception e) {
                showMessage("Nie można otworzyć pliku "+file.getName());
            }
        });

           openDirectory.setOnAction(actionEvent -> {
            DirectoryChooser dc = new DirectoryChooser();
            File dir = dc.showDialog(new Stage());
            try {
                contentPaneController.getContentTable().getItems().addAll(Mp3Parser.createMp3List(dir));
            } catch (Exception e) {
                showMessage("Nie można otworzyć folderu "+ dir.getName());
            }
        });
    }

    private void showMessage (String message){
        controlPaneController.getMessageTextField().setText(message);
    }
}



