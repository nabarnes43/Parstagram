package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.Post;

import org.parceler.Parcels;

import java.util.Date;

public class PostDetail extends AppCompatActivity {
    private Post post;
    private TextView tvDetailUsername;
    private ImageView ivDetailPost;
    private TextView tvDetailDescription;
    private TextView tvDetailTimeStamp;
    private Button btBack;



    public PostDetail() {

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_post);

        tvDetailUsername = (TextView) findViewById(R.id.tvDetailUsername);
        ivDetailPost = (ImageView) findViewById(R.id.ivDetailPost);
        tvDetailDescription =(TextView) findViewById(R.id.tvDetailDescription);
        tvDetailTimeStamp = (TextView) findViewById(R.id.tvDetailTimeStamp);
        btBack = (Button) findViewById(R.id.btBack);




        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        Date createdAt = post.getCreatedAt();
        String timeAgo = calculateTimeAgo(createdAt);
        tvDetailUsername.setText(post.getUser().getUsername());
        String L = post.getDescription();
        tvDetailTimeStamp.setText(timeAgo);
        tvDetailDescription.setText(L);
        Glide.with(PostDetail.this).load(post.getImage().getUrl()).into(ivDetailPost);


        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMain();
            }
        });

    }

    private void goToMain() {
        finish();
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }

}
