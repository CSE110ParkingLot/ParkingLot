package com.example.linning.loginregister;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Instance Variables
    Button bLogin;
    public EditText etUsername, etPassword;
    public TextView registerLink;
    public UserLocalStore userLocalStore;

    /* onCreate Method sets up Login editTexts and Buttons for user to fill
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set instance variables to correct resource id specified in content_login.xml
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.tvRegisterLink);

        //set buttons to be clickable and to store shared preferences
        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);


    }

    /* onClick method called when user clicks on either the Login button or the Register button.
     * Takes in a view to specify which button is clicked via getId()
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //if Login button is pressed, get the info filled out for Username and Password
            case R.id.bLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                //check to see if that user exists, if so, store user data and log user in
                User user = new User(username, password);
                authenticate(user);
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);
                break;

            //if Register button is pressed, open registration activity
            case R.id.tvRegisterLink:
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
                break;
        }
    }

    /* authenticate method checks to see if indormation entered in the login activity
     * is an existing user and that the correct password is provided. Takes in a user as parameter
     * to check to see if a user with the same username and password exists.
     */
    private void authenticate(User user) {

        //open the database and find the correct data
        ServerRequests serverRequest = new ServerRequests(this);
        serverRequest.fetchUserDataAsyncTask(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    /* showErrorMessage method displays an error message to the user if the provided
     * username and/or password is incorrect
     */
    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Invalid username or password.");
        dialogBuilder.setPositiveButton("Try again.", null);
        dialogBuilder.show();
    }

    /* logUserIn method logs user into their account and starts the next activity, maps activity.
     * Is called when authenticate method successfully authenticates the user.
     */
    private void logUserIn(User returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MapsActivity.class));
    }

}
