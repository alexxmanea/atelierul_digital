package com.atelierul_digital;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabase;
    private Button logout_button;
    private Button chooseimage_button;
    private ImageView display_image;
    private TextView uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");

        logout_button = findViewById(R.id.logout_button);
        uid = findViewById(R.id.uid);
        chooseimage_button = findViewById(R.id.chooseimage_button);
        display_image = findViewById(R.id.display_image);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startSignInActivity();
            }
        });

        chooseimage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            startSignInActivity();
        }

        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(mAuth.getCurrentUser().getUid())) {
                    User user =
                            snapshot.child(mAuth.getCurrentUser().getUid()).getValue(User.class);

                    if (user != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(mAuth.getCurrentUser().getUid() + "\n");
                        stringBuilder.append(user.getEmail()).append("\n");
                        stringBuilder.append(user.getFirstName()).append("\n");
                        stringBuilder.append(user.getLastName()).append("\n");
                        stringBuilder.append(user.getCity()).append("\n");
                        stringBuilder.append(user.getPhoneNumber());
                        uid.setText(stringBuilder);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Bitmap bitmap;
    private static final int CHOOSE_IMAGE = 101;
    private Uri profileImage;

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select profile image"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            profileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profileImage);
                display_image.setImageBitmap(bitmap);
                
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference("profile_pictures/" + mAuth.getCurrentUser().getUid().toString() + ".jpg");

        if (profileImage != null) {
            storageRef.putFile(profileImage).addOnCompleteListener(
                    new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
        }
    }

    private void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}