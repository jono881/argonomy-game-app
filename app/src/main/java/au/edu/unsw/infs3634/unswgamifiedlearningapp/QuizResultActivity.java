/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*
 * QuizResultActivity displays the user's quiz result as a percentage, the number of correctly and
 * incorrectly answered questions, and the number of dollars earned.
 * The icon and 'result' message on the screen are set according to the user's performance.
 * QuizResultActivity also updates the user's dollar score in FireStore.
 */

public class QuizResultActivity extends AppCompatActivity {

    private static final String TAG = "UPDATE DOLLARS";

    TextView message, user_score, dollars_earned, correct_answers, incorrect_answers;
    ImageView result_image;
    Button exitBtn;

    private FirebaseUser mUser;
    private FirebaseFirestore database;

    private int current_dollars;
    private int new_dollars;
    private String document_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // initialise widgets
        user_score = findViewById(R.id.tv_user_score);
        dollars_earned = findViewById(R.id.tv_user_dollar_score);
        correct_answers = findViewById(R.id.tv_user_correct);
        incorrect_answers = findViewById(R.id.tv_user_incorrect);
        result_image = findViewById(R.id.result_image);
        message = findViewById(R.id.tv_result_msg);
        exitBtn = findViewById(R.id.exit_quiz_btn);

        // Initialize Firebase User
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        // Initialise database
        database = FirebaseFirestore.getInstance();

        // Get intent and bundle
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // Set text of TextViews using the bundle of extras
        user_score.setText(extras.getFloat("user_score") + " %");
        dollars_earned.setText(String.valueOf(extras.getInt("dollar_score")));
        correct_answers.setText(extras.getString("correct_qtns"));
        incorrect_answers.setText(extras.getString("incorrect_qtns"));
        document_name = intent.getStringExtra("course");

        // Set image depending on the user's score
        if (extras.getFloat("user_score") < 50) {
            result_image.setBackgroundResource(R.drawable.sad);
            message.setText("Unfortunately, you did not pass");
        } else {
            result_image.setBackgroundResource(R.drawable.trophy);
            message.setText("Congratulations!");
        }

        /* The following is responsible for fetching the user's current dollar score from FireStore and
        / updating it in in real time. */
        Task<DocumentSnapshot> ref = database.collection("Users")
                .document(mUser.getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        current_dollars = documentSnapshot.getLong("dollars").intValue();
                        Log.d(TAG, "CURRENT DOLLARS ARE: " + current_dollars);

                        // Calculate the user's new dollar score
                        Log.d(TAG, "EARNED DOLLARS ARE: " + extras.getInt("dollar_score"));
                        new_dollars = current_dollars + extras.getInt("dollar_score");
                        Log.d(TAG, "TOTAL DOLLARS: " + new_dollars);

                        // Update the user's dollar score in firebase Firestore
                        DocumentReference document = database
                                .collection("Users")
                                .document(mUser.getUid());
                        // Use a hashmap to update the dollar score
                        // Adapted from: https://www.youtube.com/watch?v=KxY5-dBSdgk
                        Map<String, Object> map = new HashMap<>();
                        map.put("dollars", new_dollars);
                        document.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "Dollars successfully updated");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Dollars did not update", e);
                            }
                        });
                    }
                });
    }

    // clicking cards on quizzes screen
    public void clickBackButton(View v){
        Intent intent = new Intent(QuizResultActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    // clicking cards on quizzes screen
    public void clickRetryQuiz(View v){
        Intent intent = new Intent(QuizResultActivity.this, QuizQuestionActivity.class);
        // send intent of course name so user can retry the same quiz they've completed
        intent.putExtra("course", document_name);
        startActivity(intent);
    }
}