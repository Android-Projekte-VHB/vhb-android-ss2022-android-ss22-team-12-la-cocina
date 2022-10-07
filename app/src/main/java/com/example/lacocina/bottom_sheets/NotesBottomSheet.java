package com.example.lacocina.bottom_sheets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lacocina.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

@SuppressWarnings("FieldCanBeLocal")
public class NotesBottomSheet extends BottomSheetDialogFragment {

    private NoteSheetListener noteListener;

    private ImageButton closeButton;
    private Button saveButton;
    private TextView notesTitle;
    private EditText notesInput;

    private String title;
    private String notes;

    // Se
    public interface NoteSheetListener {
        void saveNote(String note, String title);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            noteListener = (NoteSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement BottomSheetListener");
        }
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.note_bottom_sheet_layout, container, false);

        assert this.getArguments() != null;
        title = this.getArguments().getString("title");
        notes = this.getArguments().getString("notes");



        closeButton = v.findViewById(R.id.close_note_button);
        notesInput = v.findViewById(R.id.note_input);
        saveButton = v.findViewById(R.id.save_note_button);
        notesTitle = v.findViewById(R.id.note_title);

        notesTitle.setText("Notes");
        notesInput.setText(notes);

        saveButton.setOnClickListener(view -> {
            Log.d("To TestBottomSheet", notesInput.getText().toString());
            String notes = notesInput.getText().toString();
            noteListener.saveNote(notes, title);

        });

        closeButton.setOnClickListener(view -> dismiss());

        return v;
    }








}
