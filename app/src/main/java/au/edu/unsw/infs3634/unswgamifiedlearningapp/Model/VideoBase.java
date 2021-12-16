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

public class VideoBase {

    @DocumentId
    String videoid;
    String videoname;
    String visibility;

    public VideoBase(String videoid, String videoname, String visibility) {
        this.videoid = videoid;
        this.videoname = videoname;
        this.visibility = visibility;
    }

    public VideoBase(){

    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
