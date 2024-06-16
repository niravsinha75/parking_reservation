package com.example.parkig_reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserInfoAdapter extends ArrayAdapter<UserInfo> {
    private Context mContext;
    private List<UserInfo> mUserInfoList;
    private DatabaseReference usersRef;

    public UserInfoAdapter(Context context, List<UserInfo> userInfoList) {
        super(context, 0, userInfoList);
        mContext = context;
        mUserInfoList = userInfoList;
        usersRef = FirebaseDatabase.getInstance().getReference("UserInformation");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.user_info_item, parent, false);
        }

        UserInfo userInfo = mUserInfoList.get(position);

        TextView textVehicleModel = listItem.findViewById(R.id.text_vehicle_model);
        textVehicleModel.setText("Vehicle Model: " + userInfo.getVehicleModel());

        TextView textVehicleNumber = listItem.findViewById(R.id.text_vehicle_number);
        textVehicleNumber.setText("Vehicle Number: " + userInfo.getVehicleNumber());

        TextView textOwnerName = listItem.findViewById(R.id.text_owner_name);
        textOwnerName.setText("Owner Name: " + userInfo.getOwnerName());

        TextView textDateTime = listItem.findViewById(R.id.text_date_time);
        textDateTime.setText("Date and Time: " + userInfo.getDateTime());

        TextView textParkingDuration = listItem.findViewById(R.id.text_parking_duration);
        textParkingDuration.setText("Parking Duration: " + userInfo.getParkingDuration() + " hours");

        Button deleteUserButton = listItem.findViewById(R.id.button_delete_user);
        deleteUserButton.setOnClickListener(v -> {
            String ownerNameToDelete = userInfo.getOwnerName();

            // Find the child key in Firebase that matches the ownerName
            usersRef.orderByChild("ownerName").equalTo(ownerNameToDelete).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        childSnapshot.getRef().removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                mUserInfoList.remove(position); // Remove user from the list
                                notifyDataSetChanged(); // Update the list
                                Toast.makeText(mContext, "User deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Failed to delete user", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return listItem;
    }
}
