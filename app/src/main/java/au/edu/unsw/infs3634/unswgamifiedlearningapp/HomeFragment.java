/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */

package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment{

    private View view;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private UserProfileChangeRequest profileUpdates;
    private TextView userLabel;
    private static String TAG = "Home Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);


        // access to FirebaseUser so user's name can be displayed on home activity
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Initialise database
        database = FirebaseFirestore.getInstance();

        userLabel = (TextView) view.findViewById(R.id.main_label_content);

        // show user's name --> https://firebase.google.com/docs/auth/android/manage-users
        if (user != null) {

            // Show the name of the user on home activity
            // Fetch the user's name from FireStore
            Task<DocumentSnapshot> ref = database.collection("Users")
                    .document(user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String name = documentSnapshot.getString("firstName");
                            String capitalName = name.substring(0,1).toUpperCase() + name.substring(1);
                            userLabel.setText("Welcome back, " + capitalName + "!");
                            // set profile picture --> https://www.pexels.com/search/profile%20picture/
                            String pathway = "app/src/main/res/drawable/profile_" + capitalName + ".jpg";
                            profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(pathway))
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "User's first name not found", e);
                        }
                    });

//            // show the emailName of the user on home activity
//            String email = user.getEmail();
//            // change the email string to remove "@gmail.com" to get user's name
//            String emailName = email.replace("@gmail.com", "");
//            Log.d(TAG, "User is: " + emailName);
//            // capitalise first character of user's name
//            String capitalName = emailName.substring(0,1).toUpperCase() + emailName.substring(1);
//            userLabel = (TextView) view.findViewById(R.id.main_label_content);

        }

        return view;

    }




}
