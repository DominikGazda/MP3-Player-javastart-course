package pl.javastart.mp3player.mp3;

public class Mp3Song {
    private String title;
    private String author;
    private String album;
    private String filepath;


    public Mp3Song(String title, String author, String album, String filepath) {
        this.title = title;
        this.author = author;
        this.album = album;
        this.filepath = filepath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAlbum() {
        return album;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "Mp3Song [title=" + title + ",author=" + author + ",album=" + album + "]";
    }
}
