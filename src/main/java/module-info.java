
module mp3player {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires jid3lib;
    requires javafx.media;

    exports pl.javastart.mp3player.main to javafx.graphics;
    opens pl.javastart.mp3player.controller to javafx.fxml;
    opens pl.javastart.mp3player.mp3 to javafx.base;
} 