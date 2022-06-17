package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
public class PostDetail extends AppCompatActivity {
    private Post post;
    private TextView tvDetailUsername;
    private ImageView ivDetailPost;
    private TextView tvDetailDescription;
    private TextView tvDetailTimeStamp;



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


        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));
        tvDetailUsername.setText(post.getUser().getUsername());
        String L = post.getDescription();
        tvDetailDescription.setText(L);
        Glide.with(PostDetail.this).load(post.getImage().getUrl()).into(ivDetailPost);



    }

}
