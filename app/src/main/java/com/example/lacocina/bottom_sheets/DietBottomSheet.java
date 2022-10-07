package com.example.lacocina.bottom_sheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lacocina.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressWarnings("FieldCanBeLocal")
public class DietBottomSheet extends BottomSheetDialogFragment {

    // Interface to send data to recipe activity
    public interface dietListener {
        void setDiet(String diet);
    }

    // Forces to implement methods when implementing interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            dietListener = (dietListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement BottomSheetListener");
        }
    }



    private Button veganButton;
    private Button veggieButton;
    private Button meatButton;
    private Button pescButton;
    private ImageButton closeButton;

    private dietListener dietListener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.diet_bottom_sheet, container, false);


        veganButton = v.findViewById(R.id.diet_vegan_button);
        veggieButton = v.findViewById(R.id.diet_veggie_button);
        meatButton = v.findViewById(R.id.diet_meat_button);
        pescButton = v.findViewById(R.id.diet_pesc_button);
        closeButton = v.findViewById(R.id.close_diet_button);

        veganButton.setOnClickListener(view -> {
            dietListener.setDiet("VEGAN");
            dismiss();
        });
        veggieButton.setOnClickListener(view -> {
            dietListener.setDiet("VEGGIE");
            dismiss();
        });
        meatButton.setOnClickListener(view -> {
            dietListener.setDiet("MEAT");
            dismiss();
        });
        pescButton.setOnClickListener(view -> {
            dietListener.setDiet("PESC");
            dismiss();
        });
        closeButton.setOnClickListener(view -> dismiss());

        return v;
    }



}
