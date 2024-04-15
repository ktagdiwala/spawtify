package com.example.spawtify;

public class SongModel {
    String songTitle;
    String songArtist;
    String songAlbum;
    String songGenre;
    boolean isExplicit;

    public SongModel(String songTitle, String songArtist, String songAlbum,
                     String songGenre, boolean isExplicit) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songGenre = songGenre;
        this.isExplicit = isExplicit;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public boolean isExplicit() {
        return isExplicit;
    }
}
