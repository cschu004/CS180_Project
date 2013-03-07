package com.example.ucrinstagram.Models;

import com.example.ucrinstagram.Models.*;
import android.util.Log;
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
    public User(String username, String password_hash){
        this.firstname = "";
        this.lastname = "";
        this.display_name = username;
        this.username = username;
        this.password_hash = password_hash;
        this.email = "";
    }

    public User(String firstname, String lastname, String email,
                String display_name, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.display_name = display_name;
        this.username = username;
        this.password_hash = "";
        this.email = email;
    }

    public User(String firstname, String lastname, String email,
                String display_name, String username, String password_hash) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.display_name = display_name;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
    }

    // TODO: double check if when saving back as  PUT request that we need the value?
    public User(int id) {
        WebAPI api = new WebAPI();
        User tempUser = api.getUser(id);

        if (tempUser != null){
            this.id = tempUser.id;
            this.firstname = tempUser.firstname;
            this.lastname = tempUser.lastname;
            this.email = tempUser.email;
            this.display_name = tempUser.display_name;
            this.username = tempUser.username;
            this.password_hash = tempUser.password_hash;
        }
    }

    public User(String username){
        WebAPI api = new WebAPI();
        User tempUser = api.getUser(username);

        if (tempUser != null){
            this.id = tempUser.id;
            this.firstname = tempUser.firstname;
            this.lastname = tempUser.lastname;
            this.email = tempUser.email;
            this.display_name = tempUser.display_name;
            this.username = tempUser.username;
            this.password_hash = tempUser.password_hash;
        }
    }

    // --------------------------
    // ----- PUBLIC METHODS -----
    // --------------------------

    // NOTE: applies changes to the database
    // NOTE: call after editing the User object!!!!
    public void save() {
        new WebAPI().saveUser(this);
    }

    public void create() {
        // automatically creates a default profile w/empty values
        new WebAPI().createUser(this);
    }

    // GET
    public Boolean checkPassword(String hash) {
        if (hash.equals(this.password_hash))
            return true;
        else
            return false;
    }

    public static Boolean exists(String username){
        return new WebAPI().userExists(username);
    }

    public Boolean exists(){
        return new WebAPI().userExists(this.username);
    }

    public Profile getProfile() {
        return new WebAPI().getProfile(this);
    }

    public Photo[] getPhotos() {
        return new WebAPI().getPhotosFromUser(this);
    }

    public User[] getFriends() {
        return new WebAPI().getFriends(this);
    }

    public User[] getFriendedBy(){
        return new WebAPI().getFriendedBy(this);
    }
    
    public User[] getFavorites(){
    	return new WebAPI().getFavorites(this);
    }
     
    
    // SAVE
    public void saveProfile(Profile profile) {
        new WebAPI().saveProfileFromUser(profile, this);
    }

    public void addPhoto(Photo photo) {
        new WebAPI().addPhotoToUser(photo, this);
    }

    public void addPhotos(Photo[] photos) {
    }

    public void addFriend(User friend){
        new WebAPI().addFriend(this, friend);
    }

    public void addFriends(User[] friends) {
    }

    public void addFavorite(Photo favorite){
        new WebAPI().addFavorite(this, favorite);
    }
    
    
    // DELETE
    public void removePhoto(Photo photo){

    }

    public void removePhotos(Photo[] photos){

    }

    public void removeFriend(User friend){
        new WebAPI().removeFriend(this, friend);
    }

    public void removeFriends(User[] friends){

    }
    
    public void removeFavorite(Photo favorite){
    	new WebAPI().removeFavorite(this, favorite);	
    }
    
    // ----------------------------
    // ----- Accessor METHODS -----
    // ----------------------------

    public List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // TODO: currently rails is creating the ID numbers, need to return ID number or let Java set it
        nameValuePairs.add(new BasicNameValuePair("user[firstname]", this.firstname));
        nameValuePairs.add(new BasicNameValuePair("user[lastname]", this.lastname));
        nameValuePairs.add(new BasicNameValuePair("user[email]", this.email));
        nameValuePairs.add(new BasicNameValuePair("user[display_name]", this.display_name));
        nameValuePairs.add(new BasicNameValuePair("user[username]", this.username));
        nameValuePairs.add(new BasicNameValuePair("user[password_hash]", this.password_hash));
        return nameValuePairs;
    }

    public int getId() {
        return this.id;
    }

}
