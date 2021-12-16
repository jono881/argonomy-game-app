/*
 * INFS3634 Group Assignment 2021
 * By Jonathan Kwok, Salma Lakehal, and Gordon Xie
 *
 */
package au.edu.unsw.infs3634.unswgamifiedlearningapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import au.edu.unsw.infs3634.unswgamifiedlearningapp.Model.User;

/*
 * This LeaderboardFragment is the subclass of the Fragment super class.
 * It displays the top 5 users with the highest 'dollar' score in descending order using a
 * RecyclerView and FirestoreRecyclerAdapter. The leaderboard features the users' first name,
 * score, and rank, and updates in realtime due to the users' data being stored in Google Cloud
 * FireStore.
 */

public class LeaderboardFragment extends Fragment {

    private View view;
    private FirebaseFirestore database;
    private RecyclerView mUsersList;
    private FirestoreRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        database = FirebaseFirestore.getInstance();
        // Instantiate the RecyclerView
        mUsersList = (RecyclerView) view.findViewById(R.id.userList);

        // The following code was adapted from: https://www.youtube.com/watch?v=cBwaJYocb9I
        // Query firebase Firestore collection
        Query query = database.collection("Users")
                // Display top 5 users only
                .limit(5)
                // Display them in descending order of their dollar score
                .orderBy("dollars", Query.Direction.DESCENDING);

        // Initialise the FireStoreRecyclerOptions using the User model class
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        mAdapter = new FirestoreRecyclerAdapter<User, myViewHolder>(options) {
            @NonNull
            @Override
            public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Specify the layout which the RecyclerView will use to display the song items
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new myViewHolder(view); // pass the view to the MyViewHolder method
            }

            // Bind the user data to each item
            @Override
            protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull User model) {
                // Display the user's data by setting the information to the view holders
                holder.first_name.setText(model.getFirstName());
                holder.dollars.setText(String.valueOf(model.getDollars()));
                holder.rank.setText(String.valueOf(position+1));
            }
        };

        // Set setHasFixedSize true to ensure that the adapter's contents' size do not change
        mUsersList.setHasFixedSize(true);
        // Specify linear layout manager for RecyclerView
        mUsersList.setLayoutManager(new LinearLayoutManager(getContext()));
        // Set the adapter for the RecyclerView
        mUsersList.setAdapter(mAdapter);

        return view;
    }

    // ViewHolder class
    class myViewHolder extends RecyclerView.ViewHolder {

        public TextView first_name, dollars, rank;

        // Constructor method for the MyViewHolder class
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            // Create the view holders
            first_name = itemView.findViewById(R.id.tv_first_name);
            dollars = itemView.findViewById(R.id.tv_dollars);
            rank = itemView.findViewById(R.id.tv_rank);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}

