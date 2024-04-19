package com.example.spawtify.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.spawtify.Database.SpawtifyDatabase;

import java.util.Objects;

/** Playlist
 * A playlist created by the user
 * with a title and description.
 * Contains a list of songs
 * @author Krishna Tagdiwala
 * @since 04-14-2024
 */

@Entity(tableName = SpawtifyDatabase.PLAYLIST_TABLE)
public class Playlist {

    // the playlist id (primary key)
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String playlistTitle;
    private String playlistDescription;
    // The list of songIds in the playlist, stored as a string
    private String songIdString;

    // the ID of the user currently logged in
    private int userId;

    /** Playlist constructor
     * Overrides default constructor to take in parameters
     * @param playlistTitle the String title passed in
     * @param playlistDescription the String description passed in
     * @param userId the ID of the user currently logged in
     */
    public Playlist(String playlistTitle, String playlistDescription, int userId) {
        this.playlistTitle = playlistTitle;
        this.playlistDescription = playlistDescription;
        this.userId = userId;
        this.songIdString = "\n";

//        this.songArrayList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return id == playlist.id && userId == playlist.userId && Objects.equals(playlistTitle, playlist.playlistTitle) && Objects.equals(playlistDescription, playlist.playlistDescription) && Objects.equals(songIdString, playlist.songIdString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playlistTitle, playlistDescription, songIdString, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSongIdString() {
        return songIdString;
    }

    public void setSongIdString(String songIdString) {
        this.songIdString = songIdString;
    }
}
