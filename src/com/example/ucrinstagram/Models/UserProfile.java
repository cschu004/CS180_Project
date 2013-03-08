package com.example.ucrinstagram.Models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.ucrinstagram.WebAPI;

public class UserProfile {

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
    private Date created_at;


    //  this.getClass().getSimpleName().toLowerCase();
    public static String urlSuffix = "profiles";

    // ------------------------
    // ----- CONSTRUCTORS -----
    // ------------------------

    public UserProfile() {
    }

    public UserProfile(int user_id, int age, String gender, String bio,
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

    public UserProfile(int user_id) {
        WebAPI api = new WebAPI();
        UserProfile tempUserProfile = api.getProfile(user_id);

        this.id = tempUserProfile.id;
        this.user_id = tempUserProfile.user_id;
        this.age = tempUserProfile.age;
        this.gender = tempUserProfile.gender;
        this.bio = tempUserProfile.bio;
        this.birthday = tempUserProfile.birthday;
        this.profile_photo = tempUserProfile.profile_photo;
        this.nickname = tempUserProfile.nickname;

        this.created_at = tempUserProfile.created_at;
    }

    // --------------------------
    // ----- PUBLIC METHODS -----
    // --------------------------

    // SAVE

    // NOTE: applies changes to the database
    // NOTE: call after editing the User object!!!!
    public void save() {
        this.id = new WebAPI().saveProfile(this).id;
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

    public Date getCreatedAt(){
        return this.created_at;
    }


}