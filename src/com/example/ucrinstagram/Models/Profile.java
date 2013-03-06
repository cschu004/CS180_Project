package com.example.ucrinstagram.Models;


import com.example.ucrinstagram.WebAPI;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private int id;
    private int user_id;
    public Date created_at;


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

    // SAVE

    // NOTE: applies changes to the database
    // NOTE: call after editing the User object!!!!
    public void save() {
        new WebAPI().saveProfile(this);
    }

    public void saveProfilePhoto(Photo photo) {
        this.profile_photo = photo.getId();
        save();
    }

    // GET
    public Photo getProfilePhoto() {
        return new WebAPI().getPhoto(this.profile_photo);
    }

    // ----------------------------
    // ----- Accessor METHODS -----
    // ----------------------------
    public List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // TODO: currently rails is creating the ID numbers, need to return ID number or let Java set it
        nameValuePairs.add(new BasicNameValuePair("profile[age]", Integer.toString(this.age)));
        nameValuePairs.add(new BasicNameValuePair("profile[gender]", this.gender));
        nameValuePairs.add(new BasicNameValuePair("profile[bio]", this.bio));
        nameValuePairs.add(new BasicNameValuePair("profile[profile_photo]",
                Integer.toString(this.profile_photo)));
        nameValuePairs.add(new BasicNameValuePair("profile[nickname]", this.nickname));
        return nameValuePairs;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.user_id;
    }


}