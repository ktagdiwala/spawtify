package com.example.spawtify.Database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.spawtify.Database.SpawtifyDatabase;

/** Song:
 * A song object that the user can view or interact with
 * @author Krishna Tagdiwala
 * @since 04-09-2024
 */

@Entity(tableName = SpawtifyDatabase.SONGLIST)
public class Song {

    //sets songID as primary key
    @PrimaryKey(autoGenerate = true)
    private int songId;

    private String songTitle;
    private String songArtist;
    private String songAlbum;
    private String songGenre;

    private boolean isExplicit;
    //TODO: Create type converter for boolean to int

    public Song(String songTitle, String songArtist, String songAlbum, String songGenre, boolean isExplicit) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songAlbum = songAlbum;
        this.songGenre = songGenre;
        this.isExplicit = isExplicit;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public boolean isExplicit() {
        return isExplicit;
    }

    public void setExplicit(boolean explicit) {
        isExplicit = explicit;
    }

    @NonNull
    @Override
    public String toString() {
        return songTitle + " by " + songArtist + "\n" +
                "Album: " + songAlbum + "\n" +
                "Genre: " + songGenre + "\n" +
                "Explicit = " + isExplicit;
    }
}
