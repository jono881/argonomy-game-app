/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*
 * RegisterActivity enables new users to sign up by providing their first name, last name, email,
 * and password. This is effectuated using the in-built Firebase
 * createUserWithEmailAndPassword(email, password) method.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText first_name_et, last_name_et, email_et, pwd_et;
    Button register;

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        first_name_et = findViewById(R.id.first_name_et);
        last_name_et = findViewById(R.id.last_name_et);
        email_et = findViewById(R.id.new_email_et);
        pwd_et = findViewById(R.id.new_pwd_et);
        register = findViewById(R.id.new_register_btn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialise database
        database = FirebaseFirestore.getInstance();

        // Handle register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Retrieve
                String first_name = first_name_et.getText().toString();
                String last_name = last_name_et.getText().toString();
                String email = email_et.getText().toString();
                String password = pwd_et.getText().toString();

                // Variable that specifies whether all details are valid
                Boolean check = true;

                // Check that there are no empty fields
                // If there is an empty field, display the appropriate error message
                // Adapted from: https://www.youtube.com/watch?v=iSsa9OlQJms
                 if (first_name.isEmpty()) {
                     first_name_et.setError("Please input your first name!");
                     check = false;
                 }

                 if (last_name.isEmpty()) {
                     last_name_et.setError("Please input your last name!");
                     check = false;
                 }

                 if (email.isEmpty()) {
                     email_et.setError("Please input your email!");
                     check = false;
                 }

                 if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                     email_et.setError("Please input a valid email!");
                     check = false;
                 }

                 if (password.isEmpty()) {
                     pwd_et.setError("Please input your password!");
                     check = false;
                 }

                 // If all details are valid
                 if (check){
                     // Adapted from: https://firebase.google.com/docs/auth/android/start
                     mAuth.createUserWithEmailAndPassword(email, password)
                             .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     if (task.isSuccessful()) {
                                         // Store the user's details in firebase Firestore
                                         // Adapted from: https://www.youtube.com/watch?v=RiHGwJ_u27k
                                         // Fetch the document where the user Object should be stored
                                         DocumentReference document = database.collection("Users").document(mAuth.getUid());
                                         // Use a hashmap to add the user's details to Firestore
                                         Map<String, Object> user = new HashMap<>();
                                         // Add the user's details by defining the keys and values
                                         user.put("firstName", first_name);
                                         user.put("lastName", last_name);
                                         user.put("email", email);
                                         user.put("dollars", 0);
                                         document.set(user);
                                         // Display a toast with a "successful" message
                                         Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                                         // Switch to the Login Screen
                                         Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                         startActivity(intent);
                                     } else {
                                         // If sign in fails, display an error message to the user.
                                         Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                     }
                                 }
                             });
                 }
            }
        });
    }
}
