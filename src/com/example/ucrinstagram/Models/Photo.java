package com.example.ucrinstagram.Models;

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
        this.id = new Random().nextInt();
        this.path = path;
        this.filename = filename;
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
        this.created_at = tempPhoto.created_at;
        this.updated_at = tempPhoto.updated_at;
        this.deleted_at = tempPhoto.deleted_at;
    }

    // ----------------------------
    // ----- Accessor METHODS -----
    // ----------------------------
    public List<NameValuePair> getNameValuePairs() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        // TODO: currently rails is creating the ID numbers, need to return ID number or let Java set it
        nameValuePairs.add(new BasicNameValuePair("photo[path]", this.path));
        nameValuePairs.add(new BasicNameValuePair("photo[filename]", this.filename));
        return nameValuePairs;
    }

}


