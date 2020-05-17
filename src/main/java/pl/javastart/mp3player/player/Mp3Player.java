package pl.javastart.mp3player.player;


import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pl.javastart.mp3player.mp3.Mp3Song;

import java.io.File;


public class Mp3Player {

    private ObservableList<Mp3Song> songList;
    private Media media;
    private MediaPlayer mediaPlayer;


    public Media getMedia() {
        return media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Mp3Player(ObservableList<Mp3Song> songList) {
        this.songList = songList;
    }

    public ObservableList<Mp3Song> getSongList() {
        return songList;
    }

    public void play() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED || mediaPlayer.getStatus() == MediaPlayer.Status.READY) {
            mediaPlayer.play();
        }
    }

    public void stop(){
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.stop();
        }
    }
    public double getLoadedSongLength(){
        if(media != null){
            return media.getDuration().toSeconds();
        }
        else{
            return 0;
        }
    }

    public void setVolume(double volume){
        if(mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void loadSong(int index){
        if(mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.stop();
        }
        Mp3Song mp3Song = songList.get(index);
        media = new Media(new File(mp3Song.getFilepath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.statusProperty().addListener((observable, oldStatus, newStatus) -> {
            if(newStatus == MediaPlayer.Status.READY){
                mediaPlayer.setAutoPlay(true);
            }
        });
    }

}
