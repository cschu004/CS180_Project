package com.example.ucrinstagram.Models;

import java.util.Date;

public class Comment {
    public static String urlSuffixJson = "comment.json";

    public int id;
    public int user_id;
    public String body;
    public Date created_at;
    public Date deleted_at;
    public int original_comment_id;
}
