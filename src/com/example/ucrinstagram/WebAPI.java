package com.example.ucrinstagram;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import com.example.ucrinstagram.Models.*;

public class WebAPI {

    // -------------------------------
    // ----- PUBLIC METHODS HERE -----
    // -------------------------------
    public User[] getUsers() {
        Gson gson = new Gson();
        String json = getJSONFromServer(apiURL.concat(User.urlSuffixJson));
        return gson.fromJson(json, User[].class);
    }

    public Photo[] getPhotos() {
        Gson gson = new Gson();
        String json = getJSONFromServer(apiURL.concat(Photo.urlSuffixJson));
        return gson.fromJson(json, Photo[].class);
    }

    public Topic[] getTopics() {
        Gson gson = new Gson();
        String json = getJSONFromServer(apiURL.concat(Topic.urlSuffixJson));
        return gson.fromJson(json, Topic[].class);
    }

    public Comment[] getComments() {
        Gson gson = new Gson();
        String json = getJSONFromServer(apiURL.concat(Comment.urlSuffixJson));
        return gson.fromJson(json, Comment[].class);
    }

    // --------------------------------
    // ----- PRIVATE METHODS HERE -----
    // --------------------------------
    private static final String apiURL = "http://www.mgx-dev.com/";

    private String getJSONFromServer(String modelURL) {
        HttpURLConnection connection = null;
        try {
            String message = URLEncoder.encode("my message", "UTF-8");

            URL url = new URL(modelURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                return readStream(in);
            } else {
                // Server returned HTTP error code.
                return "[]"; //TODO: create error codes
            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            connection.disconnect();
        }
        return "[]"; //TODO: create a connection error code
    }

//    TODO: Refactor and use reflection when you have the time
//    private User[] getObjectsFromJSON(Class c) {
//        Gson gson = new Gson();
//        String json = getJSONFromServer(apiURL.concat(c.urlSuffixJson));
//        return gson.fromJson(json, c[].class);
//    }


    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }


}
