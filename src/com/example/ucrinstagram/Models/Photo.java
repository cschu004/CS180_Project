package com.example.ucrinstagram.Models;

import com.example.ucrinstagram.WebAPI;

import java.util.Date;
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
}
