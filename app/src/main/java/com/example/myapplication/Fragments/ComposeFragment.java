package com.example.myapplication.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.FeedAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.Post;
import com.example.myapplication.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ComposeFragment extends BassFragment {

    private static final String TAG = "ComposeFragment";
    private Button btnLogout;
    private EditText etDescription;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private RecyclerView rvPosts;
    protected FeedAdapter adapter;
    protected List<Post> allPosts;


    public ComposeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment. Create view object hierarchy.
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    //Triggered after on create set up can occur here. Lookups attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPost);
        btnLogout = view.findViewById(R.id.btnLogout);
        etDescription = view.findViewById(R.id.etDescription);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        ivPostImage = view.findViewById(R.id.ivPostImage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        // initialize the array that will hold posts and create a PostsAdapter
        allPosts = new ArrayList<>();
        adapter = new FeedAdapter(getContext(), allPosts);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick logout button");
                MainActivity activity = (MainActivity) getContext();
                activity.logOutUser();
            }
        });
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    //Toast still not working
                    Toast.makeText(getContext(), "Description can not be empty", Toast.LENGTH_SHORT).show();
                }
                if (photoFile == null || ivPostImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no Image", Toast.LENGTH_SHORT).show();
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while Saving", e);
                    Toast.makeText(getContext(), "Error saving post", Toast.LENGTH_SHORT);
                }
            }
        });
        Log.i(TAG, "The save suceeded");
        etDescription.setText("");
        ivPostImage.setImageResource(0);
    }
}