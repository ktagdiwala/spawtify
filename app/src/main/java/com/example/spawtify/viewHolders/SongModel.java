package com.example.spawtify.viewHolders;

public class SongModel {
    public int songId;
    public String songTitle;
    public String songArtist;
    public String songAlbum;
    public String songGenre;
    public boolean isExplicit;

    public SongModel(int songId, String songTitle, String songArtist, String songAlbum,
                     String songGenre, boolean isExplicit) {
        this.songId = songId;
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songGenre = songGenre;
        this.isExplicit = isExplicit;
    }

    public int getSongId() {
        return songId;
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
