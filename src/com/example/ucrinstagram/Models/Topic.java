package com.example.ucrinstagram.Models;


import com.example.ucrinstagram.WebAPI;

import java.util.Random;

public class Topic {

    // ----------------------------
    // ----- MEMBER VARIABLES -----
    // ----------------------------

    public String tag_name;

    private int id;

    //  this.getClass().getSimpleName().toLowerCase();
    public static String urlSuffix = "topics";

    // ------------------------
    // ----- CONSTRUCTORS -----
    // ------------------------

    public Topic() {
    }

    public Topic(String tag_name) {
        this.id = new Random().nextInt();
        this.tag_name = tag_name;
    }

    public Topic(int id) {
        WebAPI api = new WebAPI();
        Topic tempTopic = api.getTopic(id);

        this.id = tempTopic.id;
        this.tag_name    = tempTopic.tag_name;
    }
}
