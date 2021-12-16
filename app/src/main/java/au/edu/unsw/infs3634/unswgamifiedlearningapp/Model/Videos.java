/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp.Model;

import com.google.firebase.firestore.DocumentId;

/*
 * This model class is used in ContentActivity to retrieve the video links, title and summary.
 *
 */

public class Videos {

    @DocumentId
    String videoID;
    String vidLink;
    String vidTitle;
    String vidSummary;

    public Videos(){

    }

    public Videos(String videoID, String vidLink, String vidTitle, String vidSummary) {
        this.videoID = videoID;
        this.vidLink = vidLink;
        this.vidTitle = vidTitle;
        this.vidSummary = vidSummary;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getVidLink() {
        return vidLink;
    }

    public void setVidLink(String vidLink) {
        this.vidLink = vidLink;
    }

    public String getVidTitle() {
        return vidTitle;
    }

    public void setVidTitle(String vidTitle) {
        this.vidTitle = vidTitle;
    }

    public String getVidSummary() {
        return vidSummary;
    }

    public void setVidSummary(String vidSummary) {
        this.vidSummary = vidSummary;
    }
}
