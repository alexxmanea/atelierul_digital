package com.atelierul_digital;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_IMAGE = 101;
    public static User currentUser;
    FirebaseUser firebaseUser;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener;
    private BottomNavigationView bottomNavigationView;
    private CircleImageView profile_image;
    private CardView profile_details;
    private TextView hello_text;
    private TextView close_icon;
    private LinearLayout profile_email_layout;
    private TextInputLayout profile_email_inputLayout;
    private EditText profile_email_editText;
    private Button profile_email_save_button;
    private LinearLayout profile_firstName_layout;
    private TextInputLayout profile_firstName_inputLayout;
    private EditText profile_firstName_editText;
    private Button profile_firstName_save_button;
    private LinearLayout profile_lastName_layout;
    private TextInputLayout profile_lastName_inputLayout;
    private EditText profile_lastName_editText;
    private Button profile_lastName_save_button;
    private LinearLayout profile_gender_layout;
    private TextInputLayout profile_gender_inputLayout;
    private AutoCompleteTextView profile_gender_editText;
    private Button profile_gender_save_button;
    private LinearLayout profile_city_layout;
    private TextInputLayout profile_city_inputLayout;
    private AutoCompleteTextView profile_city_editText;
    private Button profile_city_save_button;
    private LinearLayout profile_phoneNumber_layout;
    private TextInputLayout profile_phoneNumber_inputLayout;
    private EditText profile_phoneNumber_editText;
    private Button profile_phoneNumber_save_button;
    private LinearLayout profile_description_layout;
    private TextInputLayout profile_description_inputLayout;
    private EditText profile_description_editText;
    private Button profile_description_save_button;
    private LinearLayout profile_occupation_layout;
    private TextInputLayout profile_occupation_inputLayout;
    private EditText profile_occupation_editText;
    private Button profile_occupation_save_button;
    private MaterialButton change_profile_picture_button;
    private MaterialButton reviews_button;
    private MaterialButton signout_button;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabase;
    private boolean userNeedsUpdate = false;
    private Uri profileImage;
    private Bitmap profileImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        profile_image = findViewById(R.id.profile_image);
        profile_details = findViewById(R.id.profile_details);
        profile_details.setVisibility(View.GONE);
        hello_text = findViewById(R.id.hello_text);
        close_icon = findViewById(R.id.close_icon);
        profile_email_layout = findViewById(R.id.profile_email_layout);
        profile_email_inputLayout = findViewById(R.id.profile_email_inputLayout);
        profile_email_editText = findViewById(R.id.profile_email_editText);
        profile_email_save_button = findViewById(R.id.profile_email_save_button);
        profile_firstName_layout = findViewById(R.id.profile_firstName_layout);
        profile_firstName_inputLayout = findViewById(R.id.profile_firstName_inputLayout);
        profile_firstName_editText = findViewById(R.id.profile_firstName_editText);
        profile_firstName_save_button = findViewById(R.id.profile_firstName_save_button);
        profile_lastName_layout = findViewById(R.id.profile_lastName_layout);
        profile_lastName_inputLayout = findViewById(R.id.profile_lastName_inputLayout);
        profile_lastName_editText = findViewById(R.id.profile_lastName_editText);
        profile_lastName_save_button = findViewById(R.id.profile_lastName_save_button);
        profile_gender_layout = findViewById(R.id.profile_gender_layout);
        profile_gender_inputLayout = findViewById(R.id.profile_gender_inputLayout);
        profile_gender_editText = findViewById(R.id.profile_gender_editText);
        profile_gender_save_button = findViewById(R.id.profile_gender_save_button);
        profile_city_layout = findViewById(R.id.profile_city_layout);
        profile_city_inputLayout = findViewById(R.id.profile_city_inputLayout);
        profile_city_editText = findViewById(R.id.profile_city_editText);
        profile_city_save_button = findViewById(R.id.profile_city_save_button);
        profile_phoneNumber_layout = findViewById(R.id.profile_phoneNumber_layout);
        profile_phoneNumber_inputLayout = findViewById(R.id.profile_phoneNumber_inputLayout);
        profile_phoneNumber_editText = findViewById(R.id.profile_phoneNumber_editText);
        profile_phoneNumber_save_button = findViewById(R.id.profile_phoneNumber_save_button);
        profile_description_layout = findViewById(R.id.profile_description_layout);
        profile_description_inputLayout = findViewById(R.id.profile_description_inputLayout);
        profile_description_editText = findViewById(R.id.profile_description_editText);
        profile_description_save_button = findViewById(R.id.profile_description_save_button);
        profile_occupation_layout = findViewById(R.id.profile_occupation_layout);
        profile_occupation_inputLayout = findViewById(R.id.profile_occupation_inputLayout);
        profile_occupation_editText = findViewById(R.id.profile_occupation_editText);
        profile_occupation_save_button = findViewById(R.id.profile_occupation_save_button);
        change_profile_picture_button = findViewById(R.id.change_profile_picture_button);
        reviews_button = findViewById(R.id.reviews_button);
        signout_button = findViewById(R.id.signout_button);

        profile_image.setOnClickListener(this);
        close_icon.setOnClickListener(this);
        profile_firstName_save_button.setOnClickListener(this);
        profile_lastName_save_button.setOnClickListener(this);
        profile_gender_editText.setOnClickListener(this);
        profile_gender_inputLayout.setOnClickListener(this);
        profile_gender_save_button.setOnClickListener(this);
        profile_city_save_button.setOnClickListener(this);
        profile_phoneNumber_save_button.setOnClickListener(this);
        profile_description_save_button.setOnClickListener(this);
        profile_occupation_save_button.setOnClickListener(this);
        change_profile_picture_button.setOnClickListener(this);
        signout_button.setOnClickListener(this);
        profile_firstName_inputLayout.setOnClickListener(this);
        profile_firstName_editText.setOnClickListener(this);
        profile_lastName_inputLayout.setOnClickListener(this);
        profile_lastName_editText.setOnClickListener(this);
        profile_phoneNumber_inputLayout.setOnClickListener(this);
        profile_phoneNumber_editText.setOnClickListener(this);

        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigate_myPets:
                        selectedFragment = new MyPetsFragment();
                        item.setCheckable(true);
                        break;
                    case R.id.navigate_myJobs:
                        selectedFragment = new MyJobsFragment();
                        break;
                    case R.id.navigate_jobs:
                        selectedFragment = new JobsFragment();
                        break;
                    case R.id.navigate_addJob:
                        selectedFragment = new AddJobFragment();
                        break;
                    case R.id.navigate_messages:
                        selectedFragment = new MessagesFragment();
                        break;
                    default:
                        selectedFragment = new MyPetsFragment();
                        break;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        };
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        String[] genders = getResources().getStringArray(R.array.genders);
        ArrayAdapter<String> adapter_genders = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                genders);
        adapter_genders.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        profile_gender_editText.setAdapter(adapter_genders);
        profile_gender_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String enteredText = profile_gender_editText.getText().toString();

                    ListAdapter listAdapter = profile_gender_editText.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); ++i) {
                        String temp = listAdapter.getItem(i).toString();
                        if (enteredText.compareTo(temp) == 0) {
                            if (!profile_gender_editText.getText().toString()
                                    .equals(currentUser.getGender())) {
                                profile_gender_save_button.setVisibility(View.VISIBLE);
                            } else {
                                profile_gender_save_button.setVisibility(View.GONE);
                            }
                            return;
                        }
                    }

                    profile_gender_save_button.setVisibility(View.GONE);
                    profile_gender_editText.setText(currentUser.getGender());
                } else {
                    if (profile_gender_editText.getText().toString().equals(User.DEFAULT_GENDER)) {
                        profile_gender_editText.setText("");
                    }
                    profile_gender_editText.showDropDown();
                }
            }
        });

        profile_gender_editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!profile_gender_editText.getText().toString()
                        .equals(currentUser.getGender())) {
                    profile_gender_save_button.setVisibility(View.VISIBLE);
                }
            }
        });

        String[] cities = getResources().getStringArray(R.array.cityNames);
        ArrayAdapter<String>
                adapter_cities = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item,
                cities);
        adapter_cities.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        profile_city_editText.setAdapter(adapter_cities);
        profile_city_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String enteredText = profile_city_editText.getText().toString();

                    ListAdapter listAdapter = profile_city_editText.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); ++i) {
                        String temp = listAdapter.getItem(i).toString();
                        if (enteredText.compareTo(temp) == 0) {
                            if (!profile_city_editText.getText().toString()
                                    .equals(currentUser.getCity())) {
                                profile_city_save_button.setVisibility(View.VISIBLE);
                            } else {
                                profile_city_save_button.setVisibility(View.GONE);
                            }
                            return;
                        }
                    }

                    profile_city_save_button.setVisibility(View.GONE);
                    profile_city_editText.setText(currentUser.getCity());
                }
            }
        });
        profile_city_editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!profile_city_editText.getText().toString()
                        .equals(currentUser.getCity())) {
                    profile_city_save_button.setVisibility(View.VISIBLE);
                }
            }
        });

        profile_firstName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (profile_firstName_editText.hasFocus()) {
                    profile_firstName_editText.setError(null);
                }
                if (profile_firstName_editText.getText().toString()
                        .equals(currentUser.getFirstName())) {
                    profile_firstName_save_button.setVisibility(View.GONE);
                } else {
                    profile_firstName_save_button.setVisibility(View.VISIBLE);
                }
                hello_text.setText(
                        new StringBuilder().append("Hello, ")
                                .append(profile_firstName_editText.getText().toString())
                                .append("!").toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        profile_firstName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (profile_firstName_editText.getText().toString().equals("")) {
                        profile_firstName_editText.setText(currentUser.getFirstName());
                    }
                }
            }
        });

        profile_lastName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (profile_lastName_editText.hasFocus()) {
                    profile_lastName_editText.setError(null);
                }
                if (profile_lastName_editText.getText().toString()
                        .equals(currentUser.getLastName())) {
                    profile_lastName_save_button.setVisibility(View.GONE);
                } else {
                    profile_lastName_save_button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        profile_lastName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (profile_lastName_editText.getText().toString().equals("")) {
                        profile_lastName_editText.setText(currentUser.getLastName());
                    }
                }
            }
        });

        profile_phoneNumber_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (profile_phoneNumber_editText.hasFocus()) {
                    profile_phoneNumber_editText.setError(null);
                }
                if (profile_phoneNumber_editText.getText().toString()
                        .equals(currentUser.getPhoneNumber())) {
                    profile_phoneNumber_save_button.setVisibility(View.GONE);
                } else {
                    profile_phoneNumber_save_button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        profile_phoneNumber_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (profile_phoneNumber_editText.getText().toString().equals("")) {
                        profile_phoneNumber_editText.setText(currentUser.getPhoneNumber());
                    }
                }
            }
        });

        profile_description_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (profile_description_editText.getText().toString()
                            .equals(User.DEFAULT_DESCRIPTION)) {
                        profile_description_editText.setText("");
                    }
                } else {
                    if (profile_description_editText.getText().toString().equals("") ||
                            profile_description_editText.getText().toString()
                                    .equals(User.DEFAULT_DESCRIPTION)) {
                        profile_description_editText.setText(currentUser.getDescription());
                    }
                }
            }
        });
        profile_description_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (profile_description_editText.hasFocus()) {
                    if (profile_description_editText.getText().toString()
                            .equals(currentUser.getDescription())) {
                        profile_description_save_button.setVisibility(View.GONE);
                    } else if (profile_description_editText.getText().toString()
                            .equals(User.DEFAULT_DESCRIPTION)) {
                        profile_description_save_button.setVisibility(View.GONE);
                    } else if (profile_description_editText.getText().toString().equals("")) {
                        profile_description_save_button.setVisibility(View.GONE);
                    } else {
                        profile_description_save_button.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        profile_occupation_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (profile_occupation_editText.getText().toString()
                            .equals(User.DEFAULT_OCCUPATION)) {
                        profile_occupation_editText.setText("");
                    }
                } else {
                    if (profile_occupation_editText.getText().toString().equals("") ||
                            profile_occupation_editText.getText().toString()
                                    .equals(User.DEFAULT_OCCUPATION)) {
                        profile_occupation_editText.setText(currentUser.getOccupation());
                    }
                }
            }
        });
        profile_occupation_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (profile_occupation_editText.hasFocus()) {
                    if (profile_occupation_editText.getText().toString()
                            .equals(currentUser.getOccupation())) {
                        profile_occupation_save_button.setVisibility(View.GONE);
                    } else if (profile_occupation_editText.getText().toString()
                            .equals(User.DEFAULT_OCCUPATION)) {
                        profile_occupation_save_button.setVisibility(View.GONE);
                    } else if (profile_occupation_editText.getText().toString().equals("")) {
                        profile_occupation_save_button.setVisibility(View.GONE);
                    } else {
                        profile_occupation_save_button.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (firebaseUser != null) {
            updateProfileInformation();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                profile_details.setVisibility(View.VISIBLE);
                profile_image.setVisibility(View.GONE);

                updateProfileInformation();
                break;
            case R.id.close_icon:
                profile_details.setVisibility(View.GONE);
                profile_image.setVisibility(View.VISIBLE);

                if (userNeedsUpdate) {
                    updateUserData();
                }
                break;
            case R.id.signout_button:
                profile_details.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "You've been Signed Out",
                        Toast.LENGTH_LONG).show();

                synchronized (this) {
                    if (userNeedsUpdate) {
                        updateUserData();
                    }
                }

                signOut();
                startSignInActivity();
                break;
            case R.id.profile_firstName_save_button:
                if (!RegisterActivity
                        .isValidfirstName(profile_firstName_editText.getText().toString())) {
                    profile_firstName_editText.setError("Invalid First Name");
                    break;
                }
                profile_firstName_editText.setError(null);
                currentUser.setFirstName(profile_firstName_editText.getText().toString());
                hello_text.setText(
                        new StringBuilder().append("Hello, ")
                                .append(currentUser.getFirstName())
                                .append("!").toString());

                profile_firstName_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.profile_lastName_save_button:
                if (!RegisterActivity
                        .isValidLastName(profile_lastName_editText.getText().toString())) {
                    profile_lastName_editText.setError("Invalid Last Name");
                    break;
                }
                profile_lastName_editText.setError(null);
                currentUser.setLastName(profile_lastName_editText.getText().toString());
                profile_lastName_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.profile_gender_editText:
            case R.id.profile_gender_inputLayout:
                if (profile_gender_editText.getText().toString().equals(User.DEFAULT_GENDER)) {
                    profile_gender_editText.setText("");
                }
                profile_gender_editText.showDropDown();
                break;
            case R.id.profile_gender_save_button:
                currentUser.setGender(profile_gender_editText.getText().toString());
                profile_gender_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.profile_city_save_button:
                currentUser.setCity(profile_city_editText.getText().toString());
                profile_city_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.profile_phoneNumber_save_button:
                if (!RegisterActivity
                        .isValidPhoneNumber(profile_phoneNumber_editText.getText().toString())) {
                    profile_phoneNumber_editText.setError("Invalid Phone Number");
                    break;
                }
                profile_phoneNumber_editText.setError(null);
                currentUser.setPhoneNumber(profile_phoneNumber_editText.getText().toString());
                profile_phoneNumber_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.profile_description_save_button:
                currentUser.setDescription(profile_description_editText.getText().toString());
                profile_description_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.profile_occupation_save_button:
                currentUser.setOccupation(profile_occupation_editText.getText().toString());
                profile_occupation_save_button.setVisibility(View.GONE);
                userNeedsUpdate = true;
                break;
            case R.id.change_profile_picture_button:
                showImageChooser();
                break;
            case R.id.profile_firstName_inputLayout:
            case R.id.profile_firstName_editText:
                profile_firstName_editText.setError(null);
                break;
            case R.id.profile_lastName_inputLayout:
            case R.id.profile_lastName_editText:
                profile_lastName_editText.setError(null);
                break;
            case R.id.profile_phoneNumber_inputLayout:
            case R.id.profile_phoneNumber_editText:
                profile_phoneNumber_editText.setError(null);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            startSignInActivity();
            return;
        }

        updateProfileInformation();
    }

    public void updateProfileInformation() {
        if (firebaseUser != null) {
            usersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(firebaseUser.getUid())) {
                        currentUser = snapshot.child(firebaseUser.getUid()).getValue(User.class);

                        if (currentUser != null) {
                            hello_text.setText(
                                    new StringBuilder().append("Hello, ")
                                            .append(currentUser.getFirstName())
                                            .append("!").toString());

                            profile_email_editText.setText(currentUser.getEmail());
                            profile_firstName_editText.setText(currentUser.getFirstName());
                            profile_lastName_editText.setText(currentUser.getLastName());
                            profile_gender_editText.setText(currentUser.getGender());
                            profile_city_editText.setText(currentUser.getCity());
                            profile_phoneNumber_editText.setText(currentUser.getPhoneNumber());
                            profile_description_editText.setText(currentUser.getDescription());
                            profile_occupation_editText.setText(currentUser.getOccupation());

                            userNeedsUpdate = false;

                            downloadAndSetProfileImage();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public synchronized void updateUserData() {
        DatabaseReference thisUserReference = usersDatabase.child(currentUser.getUserId());

        thisUserReference.setValue(currentUser);

        updateProfileInformation();
    }

    private void signOut() {
        mAuth.signOut();
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select your profile image"),
                CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            profileImage = data.getData();
            try {
                profileImageBitmap =
                        MediaStore.Images.Media.getBitmap(getContentResolver(), profileImage);
                profile_image.setImageBitmap(profileImageBitmap);
                profile_image.setVisibility(View.VISIBLE);

                uploadProfileImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadAndSetProfileImage() {
        String imageName = "profile_pictures/" + firebaseUser.getUid() + ".jpg";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(imageName);

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                profileImageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profile_image.setImageBitmap(profileImageBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
    }

    private void uploadProfileImage() {
        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference(
                        "profile_pictures/" + firebaseUser.getUid() + ".jpg");

        if (profileImage != null) {
            storageRef.putFile(profileImage).addOnCompleteListener(
                    new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(getApplicationContext(), "You changed your profile " +
                                    "picture", Toast.LENGTH_LONG).show();
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