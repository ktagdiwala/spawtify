package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityChangePasswordBinding;

/** ChangePassword:
 * Change password page allows user to change their password
 *
 * @author James Mondragon
 * @since 04-13-2024
 */

public class ChangePassword extends AppCompatActivity {

    //  Key for storing passed in user ID
    private static final String USER_ID_KEY = "ChangePassword_UserID_Key";

    //  Binding allows us to manipulate and use items on screen
    ActivityChangePasswordBinding binding;

    //  Initialized in onCreate, will be used in setUser to obtain current user
    private int userId;

    //  Initialized in setUser method, used to get username and oldPassword
    User user;

    //  Used in displaying username to user
    private String username;

    //  Stores fields entered by user
    private String oldPassword;
    private String newPassword;
    private String confirmedPassword;

    //  Creates Spaw
    private SpawtifyRepository spawtifyRepository;

    //  Initialized in getDatabase
    //  used to retrieve user information from database in wireUpDisplay and setUser
    UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  Sets userId as passed in value when intentFactory was called by other activity
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //  Creates instance of SpawtifyDatabase and SpawtifyRepository
        //  Don't think either are used or needed, put in here just in case
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Creates instance of UserDAO so we can query and insert from database
        getDatabase();

        //  Sets user in current instance
        setUser();

        //  Sets up the updatePasswordButton with an onClickListener that retrieves the values from
        //  the display, if entered fields meet requirements, then password is successfully changed
        wireUpDisplay();
    }

    //  Sets current user as user that pressed change password button from main activity
    private void setUser() {
        user = userDao.getUserByUserId(userId);
        username = user.getUsername();
    }

    private void getDatabase() {
        userDao = Room.databaseBuilder(this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void getValuesFromDisplay() {
        oldPassword = binding.OldPassword.getText().toString();
        newPassword = binding.NewPassword.getText().toString();
        confirmedPassword = binding.ConfirmPassword.getText().toString();
    }

    /** wireUpDisplay:
     * Sets up updatePasswordButton with onClickListener
     * Once pressed, calls getValuesFromDisplay to retrieve values
     * Then calls checkPasswordRequirements, if passwords meet requirements then password is
     * successfully updated.
     */
    private void wireUpDisplay(){
        //  Set text for username on display
        binding.username.setText(username);

        //  Sets up updatePasswordButton as object so setOnClickListener can be used on it
        Button updatePasswordButton = binding.UpdatePasswordButton;

        //  Wires up updatePasswordButton with onClickListener
        updatePasswordButton.setOnClickListener(v -> {
            //  Retrieves values from display, sets oldPassword, newPassword, and confirmPassword
            getValuesFromDisplay();

            //  If passwords meet requirements, change local user password, then send in modified
            //  user to database with .insert(user)
            if (checkPasswordRequirements()){
                //  Change password in database
                user.setPassword(newPassword);
                userDao.insert(user);

                //  Return to login page
                Intent intent = LoginActivity.intentFactory
                        (getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /** checkPasswordRequirements:
     *
     * @return true if all three password fields meet requirements
     *         false if any of the three password fields do not meet requirements
     */
    private boolean checkPasswordRequirements(){
        //  If user entered old password does not match user's actual old password, then return
        //  false
        if (!oldPassword.equals(user.getPassword())){
            Toast.makeText
                    (this, "Old password is incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }
        //  If new password entered by user is less than 3 characters long, then return false
        if (newPassword.length() < 3){
            Toast.makeText
                    (this, "Password length too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        //  If confirmed password entered by user does not equal new password entered by user, then
        //  return false
        if (!confirmedPassword.equals(newPassword)){
            Toast.makeText
                    (this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        //  All 3 checks have been conducted, passwords meet requirements, return true
        return true;
    }

    /** intentFactory:
     *
     * @param context   current application status
     * @param userId    passed in userId to be stored as extra in intent
     * @return  intent with int extra
     */
    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, ChangePassword.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}