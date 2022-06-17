package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public FeedAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);    }



    /*Whenever RecyclerView has to show an item to a user it will call onBindViewHolder with this item’s position and ViewHolder.
    Here we should first get the post at this position. Then we can delegate binding to ViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.rootView.setTag(post);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final View rootView;
        private TextView tvUsername;
        private ImageView ivImagePost;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImagePost = itemView.findViewById(R.id.ivImagePost);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            rootView = itemView;
            //On click listener for item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Post post = (Post) v.getTag();
                    if (post!=null) {
                        Intent i = new Intent(context, PostDetail.class );
                        i.putExtra("post", Parcels.wrap(post));
                        context.startActivity(i);
                    }

                }
            });
        }



        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) context).goToProfileTab(post.getUser());
                }
            });
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImagePost);
            }
        }


    }


    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }







}