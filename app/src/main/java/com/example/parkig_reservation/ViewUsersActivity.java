package com.example.parkig_reservation;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewUsersActivity extends AppCompatActivity {

    ListView usersListView;
    List<UserInfo> userInfoList;
    UserInfoAdapter userInfoAdapter;

    private DatabaseReference usersRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        usersListView = findViewById(R.id.usersListView);
        userInfoList = new ArrayList<>();
        userInfoAdapter = new UserInfoAdapter(this, userInfoList);
        usersListView.setAdapter(userInfoAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            usersRef = FirebaseDatabase.getInstance().getReference("UserInformation");
            getUsersFromDatabase();
        }
    }

    private void getUsersFromDatabase() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userInfoList.clear(); // Clear the existing list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInfo userInfo = snapshot.getValue(UserInfo.class); // Get UserInfo from snapshot
                    if (userInfo != null) {
                        userInfoList.add(userInfo); // Add to the list
                    }
                }
                userInfoAdapter.notifyDataSetChanged(); // Notify adapter to refresh the ListView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(ViewUsersActivity.this, "Error retrieving data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
