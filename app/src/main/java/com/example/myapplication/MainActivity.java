package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Fragments.ComposeFragment;
import com.example.myapplication.Fragments.PostsFragment;
import com.example.myapplication.Fragments.ProfileFragment;
import com.example.myapplication.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ComposeFragment composefragment = new ComposeFragment();
    PostsFragment postfragment = new PostsFragment();
    ProfileFragment profilefragment = new ProfileFragment();

    private BottomNavigationView bottomNavigationView;

    public void goToProfileTab(ParseUser user) {
        // switch the fragment being displayed
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        profilefragment.user = (User) user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        final FragmentManager fragmentManager = getSupportFragmentManager();


        // handle navigation selection
        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_compose:
                        fragment = composefragment;
                        break;
                    case R.id.action_home:
                        fragment = postfragment;
                        break;
                    case R.id.action_profile:
                    default:
                        profilefragment.user = (User) ParseUser.getCurrentUser();
                        fragment = profilefragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }




    public void logOutUser() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                //Goes back to the login page.
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        Intent i = new Intent(this, LoginActivity.class );
        startActivity(i);
        finish();
    }

}