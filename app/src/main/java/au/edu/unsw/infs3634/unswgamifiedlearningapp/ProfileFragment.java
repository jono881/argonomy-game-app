/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ProfileFragment extends Fragment {

    private View view;
    private FirebaseUser user;
    private FirebaseFirestore database;
    private TextView userLabel;
    ImageView profileImage;
    private static String TAG = "Profile Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // access to FirebaseUser so user's name can be displayed on home activity
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Initialise database
        database = FirebaseFirestore.getInstance();


        userLabel = (TextView) view.findViewById(R.id.main_label_content);

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
                            String Name = name.substring(0,1).toUpperCase() + name.substring(1);
                            userLabel = (TextView) view.findViewById(R.id.main_label_content);
                            userLabel.setText(Name);

                            // set the profile pic to be shown
                            profileImage = (ImageView) view.findViewById(R.id.profile_picture);
                            String profilePictureString = "profile_" + name;
                            // find the id of the drawable file
                            int resID = getResources().getIdentifier(profilePictureString, "drawable", getActivity().getPackageName());
                            profileImage.setImageResource(resID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "User's first name not found", e);
                        }
                    });

//            // show the name of the user on home activity
//            String email = user.getEmail();
//            // remove "@gmail.com" from email String to make name
//            String name = email.replace("@gmail.com", "");
//            // capitalise first character of name
//            String capitalName = name.substring(0,1).toUpperCase() + name.substring(1);
//            userLabel = (TextView) view.findViewById(R.id.main_label_content);
//            userLabel.setText(capitalName);
//


        }

        return view;
    }



}
