/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;
    private BottomNavigationView bottomNavigationView;
    Toolbar myToolbar;
    TextView fragmentTitle;
    private static final String TAG = "Home Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // access to FirebaseAuth to logout
        mFirebaseAuth = FirebaseAuth.getInstance();

        // allow the fragment to change to appropriate screen
        bottomNavigationView = findViewById(R.id.bottom_navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


        // get intents from other activities when moving back to Home Activity
        Intent intent = getIntent();
        String mainLabel = intent.getStringExtra("Previous");

        // Default fragment to be run initially --> HomeFragment
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                    new HomeFragment()).commit();
        }
        // meant to show Quiz Fragment if user exits quiz --> not sur how to fix this
        else if (mainLabel == "Exit Quiz"){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                    new QuizFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.quiz_button);
        }


        // set custom toolbar
        myToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        fragmentTitle = findViewById(R.id.fragment_label);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(null);


        // Calling forex API =================================
        // link to API --> https://api.exchangerate.host/latest




    }




    // allow the screen to transition between different fragments
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home_button:
                            selectedFragment = new HomeFragment();
                            fragmentTitle.setText("Home");
                            break;
                        case R.id.content_button:
                            selectedFragment = new ContentFragment();
                            fragmentTitle.setText("Content");
                            break;
                        case R.id.profile_button:
                            selectedFragment = new ProfileFragment();
                            fragmentTitle.setText("My Profile");
                            break;
                        case R.id.quiz_button:
                            selectedFragment = new QuizFragment();
                            fragmentTitle.setText("Quizzes");
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                            selectedFragment).commit();

                    return true;

                }
            };

    public void logout(View view) {
        mFirebaseAuth.signOut();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }





    // clicking cards on home fragment
    public void clickQuizCard(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new QuizFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.quiz_button);
        fragmentTitle.setText("Quizzes");
    }

    public void clickNewsCard(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new NewsFragment()).commit();
        fragmentTitle.setText("News");
    }

    public void clickInsightsCard(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new InsightsFragment()).commit();
        fragmentTitle.setText("Insights");
    }

    public void clickCalculatorCard(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new CalculatorFragment()).commit();
        fragmentTitle.setText("Calculators");
    }

    public void clickContentCard(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new ContentFragment()).commit();
        // show the content button on nav bar to be selected
        bottomNavigationView.setSelectedItemId(R.id.content_button);
        fragmentTitle.setText("Content");
    }

    public void clickLeaderboardCard(View view){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home,
                new LeaderboardFragment()).commit();
        // show the content button on nav bar to be selected
        fragmentTitle.setText("Leaderboard");
    }






    // clicking cards on quizzes screen
    public void clickBudgetingQuiz(View v){
        Intent intent = new Intent(HomeActivity.this, QuizQuestionActivity.class);
        String name = "Budgeting";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickInvestingQuiz(View v){
        Intent intent = new Intent(HomeActivity.this, QuizQuestionActivity.class);
        String name = "Investing";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickExtraQuiz(View v){
        Intent intent = new Intent(HomeActivity.this, QuizQuestionActivity.class);
        String name = "Investing";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickTaxationQuiz(View v){
        Intent intent = new Intent(HomeActivity.this, QuizQuestionActivity.class);
        String name = "Taxation";
        intent.putExtra("course", name);
        startActivity(intent);
    }






    // click into course content
    public void clickBudgetingContent(View v){
        Intent intent = new Intent(HomeActivity.this, ContentActivity.class);
        String name = "Budgeting";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickInvestingContent(View v){
        Intent intent = new Intent(HomeActivity.this, ContentActivity.class);
        String name = "Investing";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickExtraContent(View v){
        Intent intent = new Intent(HomeActivity.this, ContentActivity.class);
        String name = "Investing";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickTaxationContent(View v){
        Intent intent = new Intent(HomeActivity.this, ContentActivity.class);
        String name = "Taxation";
        intent.putExtra("course", name);
        startActivity(intent);
    }

    public void clickBudgetingCardCalculator(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://moneysmart.gov.au/budgeting/budget-planner"));
        startActivity(intent);
    }

    public void clickTaxationCardCalculator(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ato.gov.au/Calculators-and-tools/Host/?anchor=STC&anchor=STC#STC/questions"));
        startActivity(intent);
    }

    public void clickInvestingCardCalculator(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.calculator.net/investment-calculator.html"));
        startActivity(intent);
    }


    // click on cards to access news
    public void clickABCnews(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.abc.net.au/news/business/"));
        startActivity(intent);
    }

    public void clickAFRnews(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.afr.com/"));
        startActivity(intent);
    }

    public void clickYFnews(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://au.finance.yahoo.com/"));
        startActivity(intent);
    }

}