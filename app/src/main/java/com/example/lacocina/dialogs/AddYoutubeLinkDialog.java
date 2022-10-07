package com.example.lacocina.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.lacocina.R;

public class AddYoutubeLinkDialog extends AppCompatDialogFragment {

    private EditText inputYoutubeLink;
    private AddYoutubeLinkDialogListener linkDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_video_dialog, null);

        builder.setView(view)
                .setTitle("Add Youtube Link")
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("Add Link", (dialogInterface, i) -> {
                    String youtubeLink = inputYoutubeLink.getText().toString();
                    Log.d("Youtube link", youtubeLink);
                    linkDialogListener.setLink(youtubeLink);
                });

        inputYoutubeLink = view.findViewById(R.id.youtubelink_input);

        return builder.create();
    }

    // Forces to implement methods when implementing interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            linkDialogListener = (AddYoutubeLinkDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +
                    "must implement AddYoutubeLinkDialogListener");
        }
    }
    // Sending youtube link to activity
    public interface AddYoutubeLinkDialogListener {
        void setLink(String youtubeLink);
    }
}
