/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.Question;

/*
 * QuizQuestionActivity is responsible for fetching the question bank pertaining to the quiz that was
 * chosen by the user,  randomly selecting 5 questions from the bank, and then displaying the 5 questions.
 * The questions are fetched from the appropriate document in Firestore using the quiz name.
 * The approach taken was inspired by TVAC Studio's tutorial series called:
 * Quiz App - Firestore MVVM Architecture; https://www.youtube.com/watch?v=zp6lbvQ5L04
 * The main episodes used were episodes 8, 9, and 10.
 */

public class QuizQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "QUIZ ACTIVITY LOG";
    private FirebaseFirestore database;
    private String current_userID;
    private String document_name;
    // List that stores all the questions from the questionBank in Firestore
    private List<Question> selectedQuizBankList = new ArrayList<>();
    // List that stores the 5 questions that will be shown to the user
    private List<Question> questionsInQuizList = new ArrayList<>();
    private boolean answer_allowed = false;

    private TextView question, optiona, optionb, optionc, optiond, time_left, quizname, question_number;
    private CardView cv_a, cv_b, cv_c, cv_d;
    private Button next_btn;
    private CountDownTimer timer;
    private int current_qtn_number, correctResponses, incorrectResponses, dollars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        // Initialise database
        database = FirebaseFirestore.getInstance();
        FirebaseAuth authentication = FirebaseAuth.getInstance();
        database.collection("QuizList");

        // Retrieve the user's ID
        if (authentication.getCurrentUser() != null) {
            current_userID = authentication.getCurrentUser().getUid();
        } else {
            Intent intent = new Intent(QuizQuestionActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        // Initialise textViews
        question = findViewById(R.id.tv_question);
        time_left = findViewById(R.id.tv_timeLeft);
        optiona = findViewById(R.id.tv_answer1);
        optionb = findViewById(R.id.tv_answer2);
        optionc = findViewById(R.id.tv_answer3);
        optiond = findViewById(R.id.tv_answer4);
        quizname = findViewById(R.id.main_label_content);
        question_number = findViewById(R.id.tv_question_num);

        // Initialise cardViews
        cv_a = findViewById(R.id.cv_answer1);
        cv_b = findViewById(R.id.cv_answer2);
        cv_c = findViewById(R.id.cv_answer3);
        cv_d = findViewById(R.id.cv_answer4);
        // Initialise button
        next_btn = findViewById(R.id.next_btn);
        next_btn.setBackgroundColor(ContextCompat.getColor(this, R.color.salmon));

        // Ensure that all UI elements are invisible
        question.setVisibility(View.INVISIBLE);
        time_left.setVisibility(View.INVISIBLE);

        cv_a.setVisibility(View.INVISIBLE);
        cv_b.setVisibility(View.INVISIBLE);
        cv_c.setVisibility(View.INVISIBLE);
        cv_d.setVisibility(View.INVISIBLE);

        optiona.setVisibility(View.INVISIBLE);
        optionb.setVisibility(View.INVISIBLE);
        optionc.setVisibility(View.INVISIBLE);
        optiond.setVisibility(View.INVISIBLE);

        next_btn.setVisibility(View.INVISIBLE);

        // Retrieve quiz_name i.e. the name of the quiz the user wants to complete
        Intent intent = getIntent();
        String quiz_name = intent.getStringExtra("course");
        quizname.setText(quiz_name + " Quiz");

        // Determine the document_name in the Firestore database based on the quiz_name
        if (quiz_name.contains("Budgeting")) {
            document_name = "Budgeting";
        } else if (quiz_name.contains("Investing")) {
            document_name = "Investing";
        }
        else if (quiz_name.contains("Taxation")) {
            document_name = "Taxation";
        }

        // Retrieve all questions in the quiz based on the quiz_name selected
        database.collection("QuizList")
                .document(document_name)
                .collection("Questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        // Store the questions in the selectedQuizBankList
                        selectedQuizBankList = task.getResult().toObjects(Question.class);
                        // Select 5 questions from the selectedQuizBankList
                        for (int i=0; i < 5; i++) {
                            int randomInteger = getRandomInteger(selectedQuizBankList.size(), 0);
                            questionsInQuizList.add(selectedQuizBankList.get(randomInteger));
                            System.out.println("Answer 1: " + questionsInQuizList.get(i).getOptiona());
                            // To prevent duplicate questions, remove the added question from the selectedQuizBankList
                            selectedQuizBankList.remove(randomInteger);
                        }
                        // Once the questions have been selected from the bank, load the UI
                        loadUI();
                    } else {
                        // Display error message
                        Log.d(TAG, "Quiz could not be loaded", task.getException());
                        Toast.makeText(QuizQuestionActivity.this, "Quiz could not be loaded",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

        cv_a.setOnClickListener(this);
        cv_b.setOnClickListener(this);
        cv_c.setOnClickListener(this);
        cv_d.setOnClickListener(this);

        next_btn.setOnClickListener(this);
    }

    private void loadUI() {
        // Display the UI
        question.setVisibility(View.VISIBLE);
        time_left.setVisibility(View.VISIBLE);
        cv_a.setVisibility(View.VISIBLE);
        cv_b.setVisibility(View.VISIBLE);
        cv_c.setVisibility(View.VISIBLE);
        cv_d.setVisibility(View.VISIBLE);
        optiona.setVisibility(View.VISIBLE);
        optionb.setVisibility(View.VISIBLE);
        optionc.setVisibility(View.VISIBLE);
        optiond.setVisibility(View.VISIBLE);

        // Enable the cardviews
        cv_a.setEnabled(true);
        cv_b.setEnabled(true);
        cv_c.setEnabled(true);
        cv_d.setEnabled(true);

        // Hide the next button
        next_btn.setVisibility(View.INVISIBLE);
        next_btn.setEnabled(false);

        Log.d(TAG, "UI loaded");

        // Display the question
        loadQuestion(1);
    }

    private void loadQuestion(int qtnNumber) {
        // Permit the user to select an answer
        answer_allowed = true;
        current_qtn_number = qtnNumber;
        // Load question number
        question_number.setText("Question: " + String.valueOf(current_qtn_number) +"/5");
        // Load question
        question.setText(questionsInQuizList.get(qtnNumber-1).getQuestion());
        Log.d(TAG, questionsInQuizList.get(qtnNumber-1).getQuestion());
        System.out.println(questionsInQuizList.get(qtnNumber-1).getOptiona());
        // Load answer options
        optiona.setText(questionsInQuizList.get(qtnNumber-1).getOptiona());
        optionb.setText(questionsInQuizList.get(qtnNumber-1).getOptionb());
        optionc.setText(questionsInQuizList.get(qtnNumber-1).getOptionc());
        optiond.setText(questionsInQuizList.get(qtnNumber-1).getOptiond());

        // Commence timer
        commenceTimer(qtnNumber);

    }

    private void commenceTimer(int qtnNumber) {

        // Prepare timeLeft text
        Long timeLeft = questionsInQuizList.get(qtnNumber-1).getTimer();
        time_left.setText(timeLeft.toString());

        // Commence countdown by providing value in milliseconds; 1 second for each interval
        timer = new CountDownTimer(timeLeft*1000, 1000) {
            @Override
            public void onTick(long l) {
                // Update time
                time_left.setText(l/1000 + " second/s left");
            }

            @Override
            public void onFinish() {
                // When time is up, prevent the user from selecting an answer
                answer_allowed = false;
                // Notify the user that the time has ended
                Toast.makeText(QuizQuestionActivity.this, "Time's up!",
                        Toast.LENGTH_SHORT).show();
                // Add one to the number of incorrect questions
                incorrectResponses++;
                executeNext();

            }
        };

        timer.start();
    }

    // generate a random integer
    public static int getRandomInteger(int max, int min) {
            return ((int) (Math.random()*(max-min))) + min;
        }

    @Override
    public void onClick(View view) {
        // Determine which element was selected
        int id = view.getId();
        if (id == R.id.cv_answer1) {
            checkSelectedOption(cv_a, optiona.getText());
        } else if (id == R.id.cv_answer2) {
            checkSelectedOption(cv_b, optionb.getText());
        } else if (id == R.id.cv_answer3) {
            checkSelectedOption(cv_c, optionc.getText());
        } else if (id == R.id.cv_answer4) {
            checkSelectedOption(cv_d, optiond.getText());
        } else if (id == R.id.next_btn) {
            // Check whether the last question in the quiz has been reached i.e. the 5th question
            if (current_qtn_number == 5) {
                // Create a Bundle object; adapted from:
                // https://medium.com/@haxzie/using-intents-and-extras-to-pass-data-between-activities-android-beginners-guide-565239407ba0
                Bundle extras = new Bundle();
                // Define extras
                Float  score = ((float) correctResponses/(float) 5)*100;
                System.out.println("SCORE: " + score);
                // Add extras
                extras.putFloat("user_score", score);
                extras.putInt("dollar_score", dollars);
                extras.putString("correct_qtns", String.valueOf(correctResponses));
                extras.putString("incorrect_qtns", String.valueOf(incorrectResponses));
                extras.putString("course", document_name);
                // Initialise intent
                Intent intent = new Intent(this, QuizResultActivity.class);
                // Attach the bundle to the Intent object
                intent.putExtras(extras);
                // Switch to the QuizResultActivity screen
                startActivity(intent);
            } else {
                executeNext();
            }
        }

    }

    private void executeNext() {
        // Check whether the last question in the quiz has been reached i.e. the 5th question
        if (current_qtn_number == 5) {
            next_btn.setText("Display Score");
        }
        // Move onto the next question by incrementing the number of the current question
        // and loading it
        current_qtn_number++;
        loadQuestion(current_qtn_number);
        // Reset the screen
        cv_a.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
        cv_b.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
        cv_c.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
        cv_d.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
        next_btn.setVisibility(View.INVISIBLE);
        next_btn.setEnabled(false);
    }

    private void checkSelectedOption(CardView card, CharSequence selected_answer) {
        // If the user selected an answer successfully
        // Check their answer
        if(answer_allowed) {
            // If they selected the correct answer
            if (selected_answer.equals(questionsInQuizList.get(current_qtn_number-1).getAnswer())) {
                Log.d(TAG, "correct answer");
                // Change the cardView's colour to green
                card.setCardBackgroundColor(ContextCompat.getColor(this,R.color.teal_200));
                // Add one to the number of correct questions
                correctResponses++;
                // Add 200 dollars to the user's dollar score
                dollars = dollars + 200;
            } else {
                // Incorrect answer
                Log.d(TAG, "incorrect answer");
                // Change the cardview's colour to red
                card.setCardBackgroundColor(ContextCompat.getColor(this,R.color.red));

                // Highlight the correct option in green
                if (questionsInQuizList.get(current_qtn_number-1).getAnswer().equals(optiona.getText())) {
                    cv_a.setCardBackgroundColor(ContextCompat.getColor(this,R.color.green));
                } else if (questionsInQuizList.get(current_qtn_number-1).getAnswer().equals(optionb.getText())) {
                    cv_b.setCardBackgroundColor(ContextCompat.getColor(this,R.color.green));
                } else if (questionsInQuizList.get(current_qtn_number-1).getAnswer().equals(optionc.getText())) {
                    cv_c.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
                } else if (questionsInQuizList.get(current_qtn_number-1).getAnswer().equals(optiond.getText())) {
                    cv_d.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green));
                }

                // Add one to the number of incorrect questions
                incorrectResponses++;
            }
            // Prevent the user from selecting a second option
            answer_allowed = false;
            // End the timer
            timer.cancel();
            // Display the next button
            next_btn.setVisibility(View.VISIBLE);
            next_btn.setEnabled(true);
        }
    }

    // method to use back button in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(QuizQuestionActivity.this, HomeActivity.class);
                intent.putExtra("Previous", "Exit Quiz");
                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // clicking cards on quizzes screen
    public void clickBackButton(View v){
        Intent intent = new Intent(QuizQuestionActivity.this, HomeActivity.class);
        Toast.makeText(QuizQuestionActivity.this, "Exiting quiz now",
                Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
