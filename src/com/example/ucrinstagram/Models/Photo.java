package com.example.ucrinstagram.Models;

import com.amazonaws.services.ec2.model.VolumeDetail;
import com.example.ucrinstagram.WebAPI;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Photo {

    // ----------------------------
    // ----- MEMBER VARIABLES -----
    // ----------------------------

    public String path;
    public String filename;
    public String caption;
    public String gps;

    private int id;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;

    //  this.getClass().getSimpleName().toLowerCase();
    public static String urlSuffix = "photos";

    // ------------------------
    // ----- CONSTRUCTORS -----
    // ------------------------

    public Photo() {
    }

    public Photo(String path, String filename) {
        this.path = path;
        this.filename = filename;
        this.caption = "";
        this.gps = "";
        this.created_at = new Date();
        this.updated_at = new Date();
        this.deleted_at = new Date(0);
    }

    public Photo(String path, String filename, String caption) {
        this.path = path;
        this.filename = filename;
        this.caption = caption;
        this.gps = "";
        this.created_at = new Date();
        this.updated_at = new Date();
        this.deleted_at = new Date(0);
    }

    public Photo(String path, String filename, String caption, String gps) {
        this.path = path;
        this.filename = filename;
        this.caption = caption;
        this.gps = gps;
        this.created_at = new Date();
        this.updated_at = new Date();
        this.deleted_at = new Date(0);
    }

    public Photo(int id) {
        WebAPI api = new WebAPI();
        Photo tempPhoto = api.getPhoto(id);

        this.id = tempPhoto.id;
        this.path    = tempPhoto.path;
        this.filename = tempPhoto.filename;
        this.caption = tempPhoto.caption;
        this.gps = tempPhoto.gps;
        this.created_at = tempPhoto.created_at;
        this.updated_at = tempPhoto.updated_at;
        this.deleted_at = tempPhoto.deleted_at;
    }

    // --------------------------
    // ----- PUBLIC METHODS -----
    // --------------------------
    public void save() {
        new WebAPI().savePhoto(this);
    }

    public Comment[] getComments(){
        return new WebAPI().getCommentsFromPhoto(this);
    }
    
    public void addComment(Comment comment){
        new WebAPI().addCommentToPhoto(this, comment);
    }

    public void addPhotoToUser(User user){
        new WebAPI().addPhotoToUser(this, user);
    }

    public void deletePhoto(){
        new WebAPI().removePhoto(this);
    }

    // ----------------------------
    // ----- Accessor METHODS -----
    // ----------------------------
    public List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // TODO: currently rails is creating the ID numbers, need to return ID number or let Java set it
        nameValuePairs.add(new BasicNameValuePair("photo[path]", this.path));
        nameValuePairs.add(new BasicNameValuePair("photo[filename]", this.filename));
        nameValuePairs.add(new BasicNameValuePair("photo[caption]", this.caption));
        nameValuePairs.add(new BasicNameValuePair("photo[gps]", this.gps));
        return nameValuePairs;
    }

    public int getId(){
        return this.id;
    }

}
