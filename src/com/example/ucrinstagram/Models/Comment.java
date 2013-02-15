package com.example.ucrinstagram.Models;

import com.example.ucrinstagram.WebAPI;

import java.util.Date;
import java.util.Random;

public class Comment {

    // ----------------------------
    // ----- MEMBER VARIABLES -----
    // ----------------------------

    public String body;

    private int id;
    private int user_id;
    private int original_comment_id;
    private Date created_at;
    private Date deleted_at;



    //  this.getClass().getSimpleName().toLowerCase();
    public static String urlSuffix = "comments";

    // ------------------------
    // ----- CONSTRUCTORS -----
    // ------------------------

    public Comment() {
    }

    public Comment(int user_id, String body, int original_comment_id) {
        this.id = new Random().nextInt();
        this.user_id = user_id;
        this.body = body;
        this.created_at = new Date();
        this.deleted_at = new Date(0);
        this.original_comment_id = original_comment_id;
    }

    public Comment(int id) {
        WebAPI api = new WebAPI();
        Comment tempComment = api.getComment(id);

        this.id = tempComment.id;
        this.user_id = tempComment.user_id;
        this.body = tempComment.body;
        this.created_at = tempComment.created_at;
        this.deleted_at = tempComment.deleted_at;
        this.original_comment_id = tempComment.original_comment_id;
    }

    // --------------------------
    // ----- PUBLIC METHODS -----
    // --------------------------

    public boolean isDeleted() {
        if (deleted_at == null || deleted_at == new Date(0)) {
            return true;
        } else {
            return false;
        }

    }
}
