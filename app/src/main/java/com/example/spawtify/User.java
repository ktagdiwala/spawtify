package com.example.spawtify;

//  @Entity(tableName = AppDatabaseName.User_Table)
public class User {

//    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;

    public User(int mUserId, String mUsername, String mPassword) {
        this.mUserId = mUserId;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
