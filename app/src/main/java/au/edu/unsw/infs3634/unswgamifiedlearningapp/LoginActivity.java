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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/*
 * LoginActivity enables users to log into the app using the email address and
 * password they chose when registering. Authentication is effectuated through Firebase.
 * This screen also features a "Register" button which
 * redirects new users to the Registration screen.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "FIRST NAME";

    EditText email_et, pwd_et;
    Button login_btn, register_btn;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialise the EditText and Button components
        email_et = findViewById(R.id.email_et);
        pwd_et = findViewById(R.id.pwd_et);
        login_btn = findViewById(R.id.exit_quiz_btn);
        register_btn = findViewById(R.id.register_btn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialise database
        database = FirebaseFirestore.getInstance();

        // Handle login button
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
            }
        });

        // Handle register button
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /* The authentication code was adapted from:
    https://firebase.google.com/docs/auth/android/password-auth */
    private void checkInput(){
        String inputted_email = email_et.getText().toString();
        String inputted_password = pwd_et.getText().toString();

        // If the user has inputted both their email and password
        if (!inputted_email.isEmpty() && !inputted_password.isEmpty()) {
            // Verify their credentials
            mAuth.signInWithEmailAndPassword(inputted_email, inputted_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Access FirebaseUser so user's name can be displayed in the home activity
                                mUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (mUser != null) {
                                    // Show the name of the user on home activity
                                    // Fetch the user's name from FireStore
                                    Task<DocumentSnapshot> ref = database.collection("Users")
                                            .document(mUser.getUid())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String name = documentSnapshot.getString("firstName");
                                            String capitalName = name.substring(0,1).toUpperCase() + name.substring(1);
                                            Log.d(TAG, "USER'S FIRST NAME IS: " + capitalName);
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(LoginActivity.this, "Successful Login, " + capitalName,
                                                    Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            // Switch to Home Screen
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e(TAG, "User's first name not found", e);
                                        }
                                    });
                                }
                                    // String email = mUser.getEmail();
                                    // String name = email.replace("@gmail.com", "");
                                    // String capitalName = name.substring(0,1).toUpperCase() + name.substring(1);

                                }
                            else {
                                // If sign in fails, display an error message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // If the user attempts to log on while leaving some fields empty,
            // display the appropriate error messages in the input fields (editTexts).
        } else if (inputted_email.isEmpty() && !inputted_password.isEmpty()) {
            email_et.setError("Please input your email.");
            email_et.requestFocus();

        } else if (!inputted_email.isEmpty() && inputted_password.isEmpty()) {
            pwd_et.setError("Please input your password.");
            pwd_et.requestFocus();
        }
        else {
            email_et.setError("Please input your email.");
            pwd_et.setError("Please input your password.");
        }
    }
}