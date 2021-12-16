/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.Videos;

/*
ContentActivity uses an embedded YoutubeVideoPlayer to play video content for users.
It retrieves links from Firebase API and plays it in an embedded format.
When clicking the next button, the next video in the short course will be played.
Upon finishing the videos, the user will have the knowledge the complete the quizzes and earn $$.
 */

public class ContentActivity extends YouTubeBaseActivity{

    private static final String TAG = "VIDEO ACTIVITY LOG";
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private String document_name;
    private FirebaseFirestore database;
    private TextView videoName;
    private Button nextButton;
    private Button prevButton;
    private TextView vidSummary;
    private List<Videos> selectedYoutubeList = new ArrayList<>();
    private List<Videos> linksInYoutubeList = new ArrayList<>();
    private String link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //Next and Previous Buttons
        nextButton = findViewById(R.id.next_btn_content);
        prevButton = findViewById(R.id.previous_btn_content);
        vidSummary = findViewById(R.id.tv_video_summary);

        Intent intent = getIntent();
        String content_name = intent.getStringExtra("course");
        TextView mainLabel = findViewById(R.id.main_label_content);
        videoName = findViewById(R.id.videoTitle);
        mainLabel.setText(content_name);

        database = FirebaseFirestore.getInstance();
        database.collection("Videos");




        //Determine the document name in the database based on the topic
        if (content_name.contains("Budgeting")) {
            document_name = "Budgeting";
        } else if (content_name.contains("Investing")) {
            document_name = "Investing";
        }
        else if (content_name.contains("Taxation")) {
            document_name = "Taxation";
        }

        // Retrieve all videos based on course selected
        database.collection("Videos")
                .document(document_name)
                .collection("Videos")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    // Store the links in the linkslist
                    selectedYoutubeList = task.getResult().toObjects(Videos.class);
                    youTubePlayerView.initialize(YoutubeConfig.getApiKey(), mOnInitializedListener);

                    // Should be put elsewhere
                    // Feeding the video title and summary in order

                }else {
                    Log.d(TAG, "Video could not be loaded", task.getException());
                }

            }
        });

        youTubePlayerView = findViewById(R.id.youtubePlayer);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            int i = 1;
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // Feeding the video title and summary in order
                // I want to play next video when the next button is pressed

                youTubePlayer.loadVideo(selectedYoutubeList.get(i-1).getVidLink());
                videoName.setText(selectedYoutubeList.get(i-1).getVidTitle());
                vidSummary.setText(selectedYoutubeList.get(i-1).getVidSummary());


                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(i == 3){
                            Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else{
                            Log.d(TAG, String.valueOf(i));
                            youTubePlayer.loadVideo(selectedYoutubeList.get(i).getVidLink());
                            videoName.setText(selectedYoutubeList.get(i).getVidTitle());
                            vidSummary.setText(selectedYoutubeList.get(i).getVidSummary());
                            i++;

                        }
                    }
                });

                prevButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (i == -1) {
                            Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d(TAG, String.valueOf(i));
                            youTubePlayer.loadVideo(selectedYoutubeList.get(i - 1).getVidLink());
                            videoName.setText(selectedYoutubeList.get(i - 1).getVidTitle());
                            vidSummary.setText(selectedYoutubeList.get(i - 1).getVidSummary());
                            i--;

                        }
                    }
                });

            }

            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };


        // allow the back button to be shown
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    // method to use back button in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ContentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




}