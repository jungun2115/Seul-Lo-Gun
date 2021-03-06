package com.example.sns_project.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.sns_project.R;
import com.example.sns_project.activity.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UesrinfoFragment extends Fragment {
    private static final String TAG = "UesrinfoFragment";

    public UesrinfoFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        final ImageView profileimageView = view.findViewById(R.id.profileimageView);
        final TextView nameTextView = view.findViewById(R.id.nameTextView);
        final TextView phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        final TextView birthDayTextView = view.findViewById(R.id.birthDayTextView);
        final TextView addressTextView = view.findViewById(R.id.addressTextView);
        final Button logout_btn = (Button)view.findViewById(R.id.logout_btn);


        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        if (document.getData().get("photoUrl") != null) {
                            Glide.with(getActivity()).load(document.getData().get("photoUrl")).centerCrop().override(500).into(profileimageView);
                        }
                        if(document.getData().get("photoUrl")== null){
                            profileimageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
                        }
                        nameTextView.setText(document.getData().get("name").toString());
                        phoneNumberTextView.setText(document.getData().get("phoneNumber").toString());
                        birthDayTextView.setText(document.getData().get("birthDay").toString());
                        addressTextView.setText(document.getData().get("address").toString());
                        logout_btn.setOnClickListener(onClickListener);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.logout_btn:
                    FirebaseAuth.getInstance().signOut();
                    startActivity();
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void startActivity() {
        Intent sintent = new Intent (this.getActivity(), SignUpActivity.class);
        startActivity(sintent);
    }
}
