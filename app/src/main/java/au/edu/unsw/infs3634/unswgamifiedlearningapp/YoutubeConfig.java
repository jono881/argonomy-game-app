/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

public class YoutubeConfig {
    public YoutubeConfig(){

    }

    // store the API key for YouTubePlayerView to be successfully run
    private static final String API_KEY = "AIzaSyBaJpQ4c4F5P4oj0meY5YnfSOrUGvOEmxk";

    public static String getApiKey(){
        return API_KEY;
    }
}
