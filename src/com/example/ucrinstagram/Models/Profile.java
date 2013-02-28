package com.example.ucrinstagram.Models;


import com.example.ucrinstagram.WebAPI;

import java.util.Date;

public class Profile {

    // ----------------------------
    // ----- MEMBER VARIABLES -----
    // ----------------------------

    public int age;
    public String gender;
    public String bio;
    public Date birthday;
    public int profile_photo;
    public String nickname;

    private int user_id;
    private Date created_at;


    //  this.getClass().getSimpleName().toLowerCase();
    public static String urlSuffix = "profiles";

    // ------------------------
    // ----- CONSTRUCTORS -----
    // ------------------------

    public Profile() {
    }

    public Profile(int user_id, int age, String gender, String bio,
                   Date birthday, int profile_photo, String nickname) {
        this.user_id = user_id;
        this.age = age;
        this.gender = gender;
        this.bio = bio;
        this.birthday = birthday;
        this.profile_photo = profile_photo;
        this.nickname = nickname;

        this.created_at = new Date();
    }

    public Profile(int user_id) {
        WebAPI api = new WebAPI();
        com.example.ucrinstagram.Models.Profile tempProfile = api.getProfile(user_id);

        this.user_id = tempProfile.user_id;
        this.age = tempProfile.age;
        this.gender = tempProfile.gender;
        this.bio = tempProfile.bio;
        this.birthday = tempProfile.birthday;
        this.profile_photo = tempProfile.profile_photo;
        this.nickname = tempProfile.nickname;

        this.created_at = tempProfile.created_at;
    }

    // --------------------------
    // ----- PUBLIC METHODS -----
    // --------------------------

    // NOTE: applies changes to the database
    // NOTE: call after editing the User object!!!!
    public void save() {
        new WebAPI().saveProfile(this);
    }

    // GET
    public Photo getProfilePhoto() {
        return null;
    }

    // SAVE
    public void saveProfile(Profile profile) {
    }

    public void saveProfilePhoto(Photo photo) {
    }

}