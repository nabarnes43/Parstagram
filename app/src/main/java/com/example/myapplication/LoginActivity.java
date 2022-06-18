package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btSignUp;
    private ImageView ivLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //If already logged in go straight to main activity.
        if (ParseUser.getCurrentUser() != null) {
            goToCompose();
        }
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btSignUp = findViewById(R.id.btSignUp);
        ivLogo = findViewById(R.id.ivLogo);


        ivLogo.setImageResource(R.drawable.icon);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUpUser(username,password);
            }
        });




    }

    private void signUpUser(String username, String password) {
        ParseUser user = new ParseUser();



    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attepting to log in user " + username);
        //ToDo navigate to the main activity if the user hass signed in properly.
        //logInIn backgroud will login on a bakcgroud thread so it will get it off the main thread.
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                //if e is not null something went wrong.
                if (e!=null){
                    //Do better error managing.
                    Log.e(TAG, "Issue with login");
                    return;
                }

                goToCompose();


            }
        });
    }


    public void goToCompose() {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
        finish();
    }

    public void goToFeed() {
        Intent i = new Intent(this, FeedActivity.class );
        startActivity(i);
        finish();
    }
}