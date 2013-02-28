package com.example.ucrinstagram;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;

import com.example.ucrinstagram.Models.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


// TODO: convert to a service when given the chance, and use string resources
public class WebAPI {

    private Gson gson = new Gson();
    private String apiURL = "http://mgx-dev.com/";

    public WebAPI() {
    }

    // ------------------------
    // ----- User Methods -----
    // ------------------------
    public User[] getAllUsers() {
        String url = apiURL + User.urlSuffix + ".json";
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, User[].class);
    }

    public User getUser(int id) {
        String url = apiURL + User.urlSuffix + "/" + Integer.toString(id) + ".json";
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, User.class);
    }

    public User getUser(String username) {
        String url = apiURL + User.urlSuffix + "/username/" + username + ".json";
        Log.i("OC", "Attempting to get User info by username: " + url);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, User.class);
    }

    // TODO: create some kind of ack, whether the save was successful or not
    public void createUser(User user) {
        String url = apiURL + User.urlSuffix;
        HTTPRequestMethod requestMethod = HTTPRequestMethod.POST;
        getJSONFromServer(new HTTPParams(requestMethod, url, user.getNameValuePairs()));
    }

    public void editUser(User user) {
        saveUser(user);
    }

    public void saveUser(User user) {
        String url = apiURL + User.urlSuffix + "/" + Integer.toString(user.getId()) + ".json";
        HTTPRequestMethod requestMethod = HTTPRequestMethod.PUT;
        getJSONFromServer(new HTTPParams(requestMethod, url, user.getNameValuePairs()));
    }

    // ---------------------------
    // ----- Profile Methods -----
    // ---------------------------
    public com.example.ucrinstagram.Models.Profile[] getProfiles() {
        return null;
    }

    public com.example.ucrinstagram.Models.Profile getProfile(int user_id) {
        String url = apiURL + User.urlSuffix + "/get_profile/" + user_id;
        Log.i("OC", "Attempting to get Profile info by user id: " + url);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, com.example.ucrinstagram.Models.Profile.class);
    }

    public com.example.ucrinstagram.Models.Profile getProfile(User user) {
        return getProfile(user.getId());
    }

    public void saveProfile(com.example.ucrinstagram.Models.Profile profile) {
    }

    public void saveProfileFromUser(com.example.ucrinstagram.Models.Profile profile, User user) {
    }

    // -------------------------
    // ----- Photo Methods -----
    // -------------------------
    public Photo[] getAllPhotos() {
        String url = apiURL + Photo.urlSuffix + ".json";
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Photo[].class);
    }

    public Photo getPhoto(int id) {
        String url = apiURL + Photo.urlSuffix + "/" + Integer.toString(id);
        Log.i("OC", "Attempting to get Profile info by user id: " + url);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Photo.class);
    }

    public Photo[] getPhotosFromUser(int id) {
        String url = apiURL + User.urlSuffix + "/get_photos/" + Integer.toString(id);
        Log.i("OC", "Attempting to get Photos by user id: " + url);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Photo[].class);
    }

    public Photo[] getPhotosFromUser(User user) {
        return getPhotosFromUser(user.getId());
    }

    public void addPhotoToUser(Photo photo, User user) {
        String url = apiURL + User.urlSuffix + "/add_photo/" + Integer.toString(user.getId());
        Log.i("OC", "Attempting to add Photo by user id: " + url);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.POST;
        getJSONFromServer(new HTTPParams(requestMethod, url, photo.getNameValuePairs()));
    }

    // -------------------------
    // ----- Topic Methods -----
    // -------------------------
    public Topic[] getTopics() {
        String url = apiURL + Topic.urlSuffix + ".json";
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Topic[].class);
    }

    public Topic getTopic(int id) {
        String url = apiURL + Topic.urlSuffix + "/" + Integer.toString(id);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Topic.class);
    }

    public void saveTopic(Topic topic) {
    }

    // ---------------------------
    // ----- Comment Methods -----
    // ---------------------------
    public Comment[] getComments() {
        String url = apiURL + Comment.urlSuffix + ".json";
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Comment[].class);
    }

    public Comment getComment(int id) {
        String url = apiURL + Comment.urlSuffix + "/" + Integer.toString(id);
        HTTPRequestMethod requestMethod = HTTPRequestMethod.GET;
        String json = getJSONFromServer(new HTTPParams(requestMethod, url));
        return gson.fromJson(json, Comment.class);
    }

    public void saveComment(Comment comment) {
    }


    // HELPER METHODS
    private String getJSONFromServer(HTTPParams params) {
        String json = null;
        try {
            return json = new getJSONFromServer().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "{}";
    }

//    TODO: Refactor and use reflection when you have the time
//    private User[] getObjectsFromJSON(Class c) {
//        Gson gson = new Gson();
//        String json = getJSONFromServer(apiURL.concat(c.urlSuffixJson));
//        return gson.fromJson(json, c[].class);
//    }

}

// TODO: just pass in HTTPClient or HttpURLConnection objects to getJSONFromServer params?
class HTTPParams {
    public HTTPRequestMethod requestMethod;
    public String url;
    public List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    public HTTPParams(HTTPRequestMethod requestMethod, String url) {
        this.requestMethod = requestMethod;
        this.url = url;
    }

    public HTTPParams(HTTPRequestMethod requestMethod, String url,
                      List<NameValuePair> nameValuePairs) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.nameValuePairs = nameValuePairs;
    }
}

enum HTTPRequestMethod {
    GET, POST, PUT, DELETE
}


// TODO: should the return value be a JSON value instead of String?
// TODO: split this into get requests back and sending data
class getJSONFromServer extends AsyncTask<HTTPParams, Void, String> {

    @Override
    protected String doInBackground(HTTPParams... params) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(params[0].url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);

            HTTPRequestMethod requestMethod = params[0].requestMethod;

            if (requestMethod == HTTPRequestMethod.GET) {
                connection.setRequestMethod("GET");
            } else if (requestMethod == HTTPRequestMethod.POST) {
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params[0].nameValuePairs));
                writer.close();
                os.close();
            } else if (requestMethod == HTTPRequestMethod.PUT) {
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params[0].nameValuePairs));
                writer.close();
                os.close();
            } else if (requestMethod == HTTPRequestMethod.DELETE) {
                connection.setRequestMethod("DELETE");
            } else {
                return "{}";
            }

            Log.i("OC: JSON", "opened http connection");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i("OC: Open Con", "preparing to get input stream");
                InputStream in = new BufferedInputStream(connection.getInputStream());
                return readStream(in);
            } else {
                // Server returned HTTP error code.
                return "{}"; //TODO: create error codes
            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return "{}"; //TODO: create a connection error code
    }

    protected void onPostExecute(Void Result) {

    }


//    protected String getJSONFromServer(String modelURL) {
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(modelURL);
//            connection = (HttpURLConnection) url.openConnection();
//
//            Log.i("OC: JSON", "opened http connection");
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                Log.i("OC: Open Con", "preparing to get input stream");
//                InputStream in = new BufferedInputStream(connection.getInputStream());
//                return readStream(in);
//            } else {
//                // Server returned HTTP error code.
//                return "{}"; //TODO: create error codes
//            }
//
//        } catch (MalformedURLException e) {
//        } catch (IOException e) {
//        } finally {
//            if (connection != null)
//                connection.disconnect();
//        }
//        return "{}"; //TODO: create a connection error code
//    }

    // --------------------------
    // ----- Helper Methods -----
    // --------------------------

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            bo.close();
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}