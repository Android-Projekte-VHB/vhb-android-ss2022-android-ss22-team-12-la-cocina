package com.example.lacocina.bottom_sheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lacocina.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressWarnings("FieldCanBeLocal")
public class BottomSheet extends BottomSheetDialogFragment {
    private BottomSheetListener sheetListener;

    private Button saveEditButton;
    private Button viewNotesButton;
    private ImageButton closeButton;
    private Button saveAsListButton;
    private EditText editText;
    private TextView title;

    // Bundle for NoteBottomSheet
    //private Bundle noteBundle;

    // Fields for received data from Bundle
    private String editTextFromBundle;
    private String titleFromBundle;
    private String notes;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        //Receiving Bundle
        assert this.getArguments() != null;
        titleFromBundle = this.getArguments().getString("title");
        if(titleFromBundle.equals("Description")) {
            editTextFromBundle = this.getArguments().getString("editDescriptionText");

        } else if (titleFromBundle.equals("Ingredients")) {
            editTextFromBundle = this.getArguments().getString("editIngredientsText");
            notes = this.getArguments().getString("ingredientNotes");

        } else if (titleFromBundle.equals("Instruction")) {
            editTextFromBundle = this.getArguments().getString("editInstructionText");
            notes = this.getArguments().getString("instructionNotes");
        }

        // Get notes from Bundle
        String ingredientNotes = this.getArguments().getString("ingredientNotes");
        String instructionNotes = this.getArguments().getString("instructionNotes");

        saveEditButton = v.findViewById(R.id.save_text_button);
        viewNotesButton = v.findViewById(R.id.view_notes_button);
        editText = v.findViewById(R.id.text_input);
        title = v.findViewById(R.id.title);
        closeButton = v.findViewById(R.id.close_edit_button);
        saveAsListButton = v.findViewById(R.id.save_as_list_button);


        //Setting text to editText and title from Bundle
        editText.setText(editTextFromBundle);
        title.setText(titleFromBundle);

        //Set View Notes Button invisible since Description has no Notes
        if(titleFromBundle.equals("Description")) {
            viewNotesButton.setVisibility(Button.INVISIBLE);
            saveAsListButton.setVisibility(Button.INVISIBLE);
        } else if(titleFromBundle.equals("Instruction")) {
            saveAsListButton.setVisibility(Button.INVISIBLE);
        }




        saveEditButton.setOnClickListener(view -> {
            sheetListener.onButtonClicked(titleFromBundle, editText.getText().toString());
            dismiss();
        });

        closeButton.setOnClickListener(view -> dismiss());

        viewNotesButton.setOnClickListener(view -> {

            Bundle noteBundle = new Bundle();

            if(titleFromBundle.equals("Ingredients")) {
                noteBundle.putString("title", titleFromBundle);
                noteBundle.putString("notes", ingredientNotes);
            }
            else if(titleFromBundle.equals("Instruction")) {
                noteBundle.putString("title", titleFromBundle);
                noteBundle.putString("notes", instructionNotes);
            }

            NotesBottomSheet notesBottomSheet = new NotesBottomSheet();
            notesBottomSheet.setArguments(noteBundle);
            notesBottomSheet.show(getParentFragmentManager(), "notesBottomSheet");

        });

        saveAsListButton.setOnClickListener(view -> sheetListener.saveToGroceryList(titleFromBundle, editText.getText().toString()));


        return v;
    }

    // Interfacing sending data to activity
    public interface BottomSheetListener {
        void onButtonClicked(String title, String text);
        void saveToGroceryList(String title, String text);
    }
    // Enforces implementation of methods when implementing interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            sheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement BottomSheetListener");
        }
    }


}
