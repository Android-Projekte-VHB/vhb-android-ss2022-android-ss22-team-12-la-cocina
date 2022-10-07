package com.example.lacocina.bottom_sheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lacocina.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressWarnings("FieldCanBeLocal")
public class TimeBottomSheet extends BottomSheetDialogFragment {

    // Sending data to activity
    public interface TimeListener {
        void setTime(int time);
    }

    private TimeListener timeListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            timeListener = (TimeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement TimeListener");
        }
    }

    private NumberPicker numberPicker;
    private TextView timeView;
    private ImageButton closeButton;
    private Button setTimerButton;

    private int timeValue;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_bottom_sheet, container, false);


        timeView = v.findViewById(R.id.timer_view);
        numberPicker = v.findViewById(R.id.number_picker_time);
        closeButton = v.findViewById(R.id.close_timer_button);
        setTimerButton = v.findViewById(R.id.set_time_button);

        // init min and max time of number picker
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(300);

        //Set current selected value of number picker
        timeView.setText(String.format("Cooking Time: %s Minutes", numberPicker.getValue()));

        numberPicker.setOnValueChangedListener((numberPicker, oldVal, newVal) -> {
            timeView.setText(String.format("Cooking Time: %s Minutes", newVal));
            timeValue = newVal;
        });

        setTimerButton.setOnClickListener(view -> {
            timeListener.setTime(timeValue);
            dismiss();
        });

        closeButton.setOnClickListener(view -> dismiss());

        return v;
    }
}
