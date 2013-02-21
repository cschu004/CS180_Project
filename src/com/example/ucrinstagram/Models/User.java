package com.example.ucrinstagram.Models;

import com.amazonaws.services.elasticloadbalancing.model.SetLoadBalancerPoliciesForBackendServerRequest;
import com.example.ucrinstagram.WebAPI;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {

    // ----------------------------
    // ----- MEMBER VARIABLES -----
    // ----------------------------

    public String firstname;
    public String lastname;
    public String email;
    public String display_name;
    public String username;

    private int id;
    private String password_hash;

    //  this.getClass().getSimpleName().toLowerCase();
    public static String urlSuffix = "users";

    // ------------------------
    // ----- CONSTRUCTORS -----
    // ------------------------

    public User() {
    }

    // TODO: write validations before saving to enforce that a new user account has a password
    public User(String firstname, String lastname, String email,
                String display_name, String username) {
        this.id = new Random().nextInt();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.display_name = display_name;
        this.username = username;
    }

    public User(String firstname, String lastname, String email,
                String display_name, String username, String password_hash) {
        this.id = new Random().nextInt();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.display_name = display_name;
        this.username = username;
        this.password_hash = password_hash;
    }

    // TODO: double check if when saving back as  PUT request that we need the value?
    public User(int id) {
        WebAPI api = new WebAPI();
        User tempUser = api.getUser(id);

        this.id = tempUser.id;
        this.firstname = tempUser.firstname;
        this.lastname = tempUser.lastname;
        this.email = tempUser.email;
        this.display_name = tempUser.display_name;
        this.username = tempUser.username;
    }

    // --------------------------
    // ----- PUBLIC METHODS -----
    // --------------------------

    // NOTE: applies changes to the database
    // NOTE: call after editing the User object!!!!
    public void save() {
        new WebAPI().saveUser(this);
    }

    public void create(){
        new WebAPI().createUser(this);
    }

    // GET
    public Profile getProfile() {
        return null;
    }

    public Photo[] getPhotos() {
        return null;
    }

    public User[] getFollowers() {
        return null;
    }

    // SAVE
    public void saveProfile(Profile profile) {
    }

    public void savePhoto(Photo photo) {
    }

    public void savePhotos(Photo[] photos) {
    }

    public void saveFollower(User user) {
    }

    public void saveFollower(User[] users) {
    }


    // ----------------------------
    // ----- Accessor METHODS -----
    // ----------------------------

    public List<NameValuePair> getNameValuePairs(){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("user[firstname]", this.firstname));
        nameValuePairs.add(new BasicNameValuePair("user[lastname]", this.lastname));
        nameValuePairs.add(new BasicNameValuePair("user[email]", this.email));
        nameValuePairs.add(new BasicNameValuePair("user[display_name]", this.display_name));
        nameValuePairs.add(new BasicNameValuePair("user[username]", this.username));
        nameValuePairs.add(new BasicNameValuePair("user[password_hash]", this.password_hash));
        return nameValuePairs;
    }

    public int getId(){
        return this.id;
    }

}
