package com.example.myapplication.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseUser;

@ParseClassName("_User")

public class User extends ParseUser {
    public static final String KEY_PROFILE_PHOTO = "profilePhoto";

    public ParseFile getProfilePhoto() {return getParseFile(KEY_PROFILE_PHOTO);}

    public void setKeyProfilePhoto(ParseFile parseFile) {put(KEY_PROFILE_PHOTO,parseFile);}
}

