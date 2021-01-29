package com.atelierul_digital.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atelierul_digital.R;
import com.atelierul_digital.activities.MainActivity;
import com.atelierul_digital.entities.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class AddPetFragment extends Fragment implements View.OnClickListener {
    private static final int CHOOSE_IMAGE = 101;
    private String petId = "";
    private CircleImageView pet_image;
    private TextView welcome_text;
    private TextInputLayout petName_layout;
    private EditText petName_editText;
    private TextInputLayout petType_layout;
    private AutoCompleteTextView petType_editText;
    private TextInputLayout petAge_layout;
    private EditText petAge_editText;
    private TextInputLayout petGender_layout;
    private AutoCompleteTextView petGender_editText;
    private TextInputLayout petDescription_layout;
    private EditText petDescription_editText;
    private MaterialButton addYourPet_button;
    private ProgressBar loading_progressBar_addPet;
    private DatabaseReference petsDatabase;
    private DatabaseReference usersDatabase;
    private Uri profileImage;
    private Bitmap profileImageBitmap;
    private boolean imageChosen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addpet, container, false);

        petsDatabase = FirebaseDatabase.getInstance().getReference("pets");
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");

        pet_image = view.findViewById(R.id.pet_image);
        welcome_text = view.findViewById(R.id.welcome_text);
        petName_layout = view.findViewById(R.id.petName_layout);
        petName_editText = view.findViewById(R.id.petName_editText);
        petType_layout = view.findViewById(R.id.petType_layout);
        petType_editText = view.findViewById(R.id.petType_editText);
        petAge_layout = view.findViewById(R.id.petAge_layout);
        petAge_editText = view.findViewById(R.id.petAge_editText);
        petGender_layout = view.findViewById(R.id.petGender_layout);
        petGender_editText = view.findViewById(R.id.petGender_editText);
        petDescription_layout = view.findViewById(R.id.petDescription_layout);
        petDescription_editText = view.findViewById(R.id.petDescription_editText);
        addYourPet_button = view.findViewById(R.id.addYourPet_button);
        loading_progressBar_addPet = view.findViewById(R.id.loading_progressBar_addPet);

        pet_image.setOnClickListener(this);
        petName_layout.setOnClickListener(this);
        petName_editText.setOnClickListener(this);
        petType_layout.setOnClickListener(this);
        petType_editText.setOnClickListener(this);
        petAge_layout.setOnClickListener(this);
        petAge_editText.setOnClickListener(this);
        petGender_layout.setOnClickListener(this);
        petGender_editText.setOnClickListener(this);
        petDescription_layout.setOnClickListener(this);
        petDescription_editText.setOnClickListener(this);
        addYourPet_button.setOnClickListener(this);

        imageChosen = false;

        petName_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (petName_editText.hasFocus()) {
                    petName_editText.setError(null);

                    StringBuilder welcomeText = new StringBuilder();
                    welcomeText.append("Welcome, ");
                    welcomeText.append(petName_editText.getText().toString());
                    welcomeText.append("!");
                    welcome_text.setText(welcomeText.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        petName_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (petName_editText.getText().toString().isEmpty()) {
                        welcome_text.setText("Welcome, Pet!");
                    }
                }
            }
        });

        String[] petTypes = getResources().getStringArray(R.array.petTypes);
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, petTypes);
        typesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        petType_editText.setAdapter(typesAdapter);
        petType_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String enteredText = petType_editText.getText().toString();

                    ListAdapter listAdapter = petType_editText.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); ++i) {
                        String temp = listAdapter.getItem(i).toString();
                        if (enteredText.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    petType_editText.setText("");
                } else {
                    petType_editText.showDropDown();
                    if (petType_editText.getError() != null) {
                        petType_editText.setError(null);
                    }
                }
            }
        });

        petAge_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (petAge_editText.hasFocus()) {
                    petAge_editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] petGenders = getResources().getStringArray(R.array.genders);
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, petGenders);
        typesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        petGender_editText.setAdapter(gendersAdapter);
        petGender_editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String enteredText = petGender_editText.getText().toString();

                    ListAdapter listAdapter = petGender_editText.getAdapter();
                    for (int i = 0; i < listAdapter.getCount(); ++i) {
                        String temp = listAdapter.getItem(i).toString();
                        if (enteredText.compareTo(temp) == 0) {
                            return;
                        }
                    }

                    petGender_editText.setText("");
                } else {
                    petGender_editText.showDropDown();
                    if (petGender_editText.getError() != null) {
                        petGender_editText.setError(null);
                    }
                }
            }
        });

        petGender_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (petGender_editText.hasFocus()) {
                    petGender_editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        petDescription_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (petDescription_editText.hasFocus()) {
                    petDescription_editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pet_image:
                showImageChooser();
                break;
            case R.id.petName_layout:
            case R.id.petName_editText:
                petName_editText.setError(null);
                break;
            case R.id.petType_layout:
            case R.id.petType_editText:
                petType_editText.setError(null);
                break;
            case R.id.petAge_layout:
            case R.id.petAge_editText:
                petAge_editText.setError(null);
                break;
            case R.id.petGender_layout:
            case R.id.petGender_editText:
                petGender_editText.setError(null);
                break;
            case R.id.petDescription_layout:
            case R.id.petDescription_editText:
                petDescription_editText.setError(null);
                break;
            case R.id.addYourPet_button:
                loading_progressBar_addPet.setVisibility(View.VISIBLE);
                addPet();
                break;
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an image with your pet"),
                CHOOSE_IMAGE);
    }

    private void addPet() {
        String name = petName_editText.getText().toString();
        String type = petType_editText.getText().toString();
        int age = (petAge_editText.getText().toString().equals("")) ? 0 :
                Integer.parseInt(petAge_editText.getText().toString());
        String gender = petGender_editText.getText().toString();
        String description = petDescription_editText.getText().toString();

        boolean isValidRegistration = true;

        if (!Pet.isValidName(name)) {
            petName_editText.setError("Invalid name");
            isValidRegistration = false;
        } else {
            petName_editText.setError(null);
        }

        if (type.isEmpty()) {
            petType_editText.setError("Please select a type");
            isValidRegistration = false;
        } else {
            petType_editText.setError(null);
        }

        if (!Pet.isValidAge(age)) {
            petAge_editText.setError("Invalid age");
            isValidRegistration = false;
        } else {
            petAge_editText.setError(null);
        }

        if (gender.isEmpty()) {
            petGender_editText.setError("Please select a gender");
            isValidRegistration = false;
        } else {
            petGender_editText.setError(null);
        }

        if (!Pet.isValidDescription(description)) {
            petDescription_editText.setError("Invalid description");
            isValidRegistration = false;
        } else {
            petDescription_editText.setError(null);
        }

        if (!imageChosen) {
            pet_image.setBorderColor(Color.parseColor("#FF0023"));
            isValidRegistration = false;
        } else {
            pet_image.setBorderColor(Color.parseColor("#FFFFFF"));
        }

        if (!isValidRegistration) {
            loading_progressBar_addPet.setVisibility(View.GONE);
            return;
        }

        String ownerId = MainActivity.currentUser.getUserId();
        Pet pet = new Pet(petId, name, type, age, ownerId, gender, description);

        petsDatabase
                .child(petId)
                .setValue(pet).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(),
                                    Objects.requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

        MainActivity.currentUser.addPet();
        MyPetsFragment.getPetsData();

        DatabaseReference thisUserReference =
                usersDatabase.child(MainActivity.currentUser.getUserId());
        thisUserReference.setValue(MainActivity.currentUser);

        loading_progressBar_addPet.setVisibility(View.GONE);

        Toast.makeText(getContext(), "Your pet was added successfully", Toast.LENGTH_LONG).show();

        Fragment thisFragment = MainActivity.fragmentManager.findFragmentByTag("AddPetFragment");
        if (thisFragment != null) {
            MainActivity.fragmentManager.beginTransaction().remove(thisFragment).commitAllowingStateLoss();
            MainActivity.fragmentsStack.get("MyPetsFragment").pop();
            MainActivity.showMyPets();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            profileImage = data.getData();
            try {
                profileImageBitmap =
                        MediaStore.Images.Media.getBitmap(
                                Objects.requireNonNull(getActivity()).getApplicationContext()
                                        .getContentResolver(),
                                profileImage);
                pet_image.setImageBitmap(profileImageBitmap);

                uploadPetProfileImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadPetProfileImage() {
        petId = UUID.randomUUID().toString();

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference(
                        "pets_profile_pictures/" + petId + ".jpg");

        if (profileImage != null) {
            storageRef.putFile(profileImage).addOnCompleteListener(
                    new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getContext(), Objects
                                                .requireNonNull(task.getException()).getMessage(),
                                        Toast.LENGTH_LONG).show();
                                pet_image.setImageResource(
                                        R.drawable.defautpetprofilepicture_inverted_icon);
                                imageChosen = false;
                            } else {
                                pet_image.setBorderColor(Color.parseColor("#FFFFFF"));
                                imageChosen = true;
                            }
                        }
                    });
        }
    }
}