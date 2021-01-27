package com.atelierul_digital;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsRecyclerViewAdapter
        extends RecyclerView.Adapter<PetsRecyclerViewAdapter.RecycleViewHolder> {
    private List<Pet> mdata;
    private Bitmap profileImageBitmap;

    public PetsRecyclerViewAdapter(List<Pet> mdata) {
        this.mdata = mdata;
    }

    public List<Pet> getMdata() {
        return mdata;
    }

    public void setMdata(List<Pet> mdata) {
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View recyclerRow =
                layoutInflater.inflate(R.layout.recyclerview_row_mypets, viewGroup, false);
        return new RecycleViewHolder(recyclerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int position) {
        Pet pet = mdata.get(position);

        recycleViewHolder.pet_row_image
                .setImageResource(R.drawable.defautpetprofilepicture_inverted_icon);
        downloadAndSetPetProfileImage(pet.getId(), recycleViewHolder.pet_row_image);
        recycleViewHolder.petName_row_editText.setText(pet.getName());
        recycleViewHolder.petType_row_editText.setText(pet.getType());
        recycleViewHolder.petAge_row_editText.setText(Integer.toString(pet.getAge()));
        recycleViewHolder.petGender_row_editText.setText(pet.getGender());
        recycleViewHolder.petDescription_row_editText.setText(pet.getDescription());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    private void downloadAndSetPetProfileImage(String petId, CircleImageView profile_image) {
        String imageName = "pets_profile_pictures/" + petId + ".jpg";
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

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {
        CardView petDetails_row_cardView;
        CircleImageView pet_row_image;
        TextInputLayout petName_row_layout;
        EditText petName_row_editText;
        TextInputLayout petType_row_layout;
        AutoCompleteTextView petType_row_editText;
        TextInputLayout petAge_row_layout;
        EditText petAge_row_editText;
        TextInputLayout petGender_row_layout;
        AutoCompleteTextView petGender_row_editText;
        TextInputLayout petDescription_row_layout;
        EditText petDescription_row_editText;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            this.petDetails_row_cardView = itemView.findViewById(R.id.petDetails_row_cardView);
            this.pet_row_image = itemView.findViewById(R.id.pet_row_image);
            this.petName_row_layout = itemView.findViewById(R.id.petName_row_layout);
            this.petName_row_editText = itemView.findViewById(R.id.petName_row_editText);
            this.petType_row_layout = itemView.findViewById(R.id.petType_row_layout);
            this.petType_row_editText = itemView.findViewById(R.id.petType_row_editText);
            this.petAge_row_layout = itemView.findViewById(R.id.petAge_row_layout);
            this.petAge_row_editText = itemView.findViewById(R.id.petAge_row_editText);
            this.petGender_row_layout = itemView.findViewById(R.id.petGender_row_layout);
            this.petGender_row_editText = itemView.findViewById(R.id.petGender_row_editText);
            this.petDescription_row_layout = itemView.findViewById(R.id.petDescription_row_layout);
            this.petDescription_row_editText =
                    itemView.findViewById(R.id.petDescription_row_editText);
        }
    }
}
