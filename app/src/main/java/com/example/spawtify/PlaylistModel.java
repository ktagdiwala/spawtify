package com.example.spawtify;

/** PlaylistModel
 * Model class for the playlist list items
 * in the playlist recycler view
 * @author Krishna Tagdiwala
 * @since 04-16-2024
 */

public class PlaylistModel {
    String playlistTitle;
    String playlistDescription;
    String songlistString;

    public PlaylistModel(String playlistTitle, String playlistDescription) {
        this.playlistTitle = playlistTitle;
        this.playlistDescription = playlistDescription;
    }

    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public String getSonglistString() {
        return songlistString;
    }
}
