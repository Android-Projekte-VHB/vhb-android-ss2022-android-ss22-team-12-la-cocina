package com.example.lacocina.bottom_sheets;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lacocina.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
@SuppressWarnings("FieldCanBeLocal")
public class VideoBottomSheet extends BottomSheetDialogFragment {


    private VideoListener videoListener;

    private YouTubePlayerView youTubePlayerView;
    private BottomSheetBehavior bottomSheetBehavior;
    private String youtubeLink;
    private ImageButton closeButton;
    private Button deleteLinkButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_video_layout, container, false);

        assert this.getArguments() != null;
        youtubeLink = this.getArguments().getString("link");
        Log.d("YT Link", youtubeLink);
        String linkID = youtubeLink.substring(17);
        Log.d("YT Link ID", linkID);

        closeButton = v.findViewById(R.id.close_video_button);
        youTubePlayerView = v.findViewById(R.id.youTubePlayerView);
        deleteLinkButton = v.findViewById(R.id.delete_link_button);


        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                //String videoId = linkID;
                youTubePlayer.cueVideo(linkID, 0);
            }
        });



        closeButton.setOnClickListener(view -> dismiss());
        deleteLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoListener.delete();
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
    }
    public interface VideoListener {
        void delete();
    }

}
