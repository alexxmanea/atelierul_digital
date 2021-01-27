package com.atelierul_digital;

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

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPetsFragment extends Fragment implements View.OnClickListener {
    private static PetsRecyclerViewAdapter adapter;
    private static User currentUser;
    private static DatabaseReference petsDatabase;
    private static List<Pet> user_pets;
    private static RecyclerView recyclerView;
    private static Context context;
    private MaterialButton addpet_button;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabase;
    private MaterialButton addMorePets_button;

    public static void getPetsData() {
        petsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_pets.clear();
                user_pets = new ArrayList<>();
                for (DataSnapshot petSnapshot : snapshot.getChildren()) {
                    Pet pet = petSnapshot.getValue(Pet.class);

                    if (pet.getOwnerId().equals(currentUser.getUserId())) {
                        user_pets.add(pet);
                        System.out.println("AJUNG AICI " + user_pets.size());
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
        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");
        petsDatabase = FirebaseDatabase.getInstance().getReference("pets");

        currentUser = MainActivity.currentUser;

        context = getActivity().getApplicationContext();

        View view;
        if (currentUser.getPetsCount() == 0) {
            view = inflater.inflate(R.layout.fragment_nopets, container, false);

            addpet_button = view.findViewById(R.id.addpet_button);
            addpet_button.setOnClickListener(this);
        } else {
            view = inflater.inflate(R.layout.fragment_mypets, container, false);

            addMorePets_button = view.findViewById(R.id.addMorePets_button);
            addMorePets_button.setOnClickListener(this);

            getPetsData();

            recyclerView = view.findViewById(R.id.myPets_recyclerView);
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(getActivity().getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            user_pets = new ArrayList<>();
            adapter = new PetsRecyclerViewAdapter(user_pets);

            recyclerView.setAdapter(adapter);
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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddPetFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
