package com.atelierul_digital.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atelierul_digital.PetsRecyclerViewAdapter;
import com.atelierul_digital.R;
import com.atelierul_digital.activities.MainActivity;
import com.atelierul_digital.entities.Pet;
import com.atelierul_digital.entities.User;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyPetsFragment extends Fragment implements View.OnClickListener {
    private static PetsRecyclerViewAdapter adapter;
    private static User currentUser;
    private static DatabaseReference petsDatabase;
    private RecyclerView recyclerView;
    private Context context;
    private MaterialButton addpet_button;
    private MaterialButton addMorePets_button;
    private Fragment addPetFragment;

    public static void getPetsData() {
        petsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Pet> user_pets = new ArrayList<>();

                for (DataSnapshot petSnapshot : snapshot.getChildren()) {
                    Pet pet = petSnapshot.getValue(Pet.class);

                    if (pet != null) {
                        if (pet.getOwnerId().equals(currentUser.getUserId())) {
                            user_pets.add(pet);
                        }
                    }
                }

                adapter.setMdata(user_pets);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        petsDatabase = FirebaseDatabase.getInstance().getReference("pets");

        View view;
        currentUser = MainActivity.currentUser;
        if (currentUser.getPetsCount() == 0) {
            view = inflater.inflate(R.layout.fragment_nopets, container, false);

            addpet_button = view.findViewById(R.id.addpet_button);
            addpet_button.setOnClickListener(this);
        } else {
            view = inflater.inflate(R.layout.fragment_mypets, container, false);

            addMorePets_button = view.findViewById(R.id.addMorePets_button);
            addMorePets_button.setOnClickListener(this);

            recyclerView = view.findViewById(R.id.myPets_recyclerView);
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new PetsRecyclerViewAdapter(new ArrayList<>());
            recyclerView.setAdapter(adapter);

            getPetsData();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addpet_button:
            case R.id.addMorePets_button:
                addPetFragment = new AddPetFragment();
                MainActivity.fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, addPetFragment,
                                "AddPetFragment").hide(addPetFragment)
                        .commitAllowingStateLoss();
                MainActivity.fragmentManager.beginTransaction().hide(MainActivity.selectedFragment)
                        .show(addPetFragment)
                        .commitAllowingStateLoss();
                MainActivity.selectedFragment = addPetFragment;

                MainActivity.fragmentsStack.get("MyPetsFragment").add(addPetFragment);
                break;
        }
    }
}
