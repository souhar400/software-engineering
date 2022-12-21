package de.gruppe.e.klingklang.view.TrackSelectionMenus;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.util.Objects;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.Recorder;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class TrackOptionMenu extends BottomSheetDialogFragment {

    private View view;
    private File track;
    private LinearLayout linearLayout;
    private Button button;
    private FragmentManager associatedManager;

    public TrackOptionMenu(File track, LinearLayout linearLayout, Button button, FragmentManager associatedManager) {
        super();
        this.track = track;
        this.linearLayout = linearLayout;
        this.button = button;
        this.associatedManager = associatedManager;
    }

    /**
     * Creates the dialog and calls methods to display it in fullscreen
     * @param savedInstanceState
     * @return The created dialog
     */
    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupFullHeight(bottomSheetDialog);
        });
        return  dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() != null &&
                getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideNavigationAndSwipeUpBar();
        }
    }


    /**
     * Sets the Behaviour of the BottomSheetDialog so it expands over the whole screen
     * @param bottomSheetDialog Dialog, which behaviour needs to be set
     */
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * Gets the height of the window
     * @return Window height as int
     */
    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * Creates the SoundsMenu view that is displayed in the bottomSheetDialog
     * @param inflater factory to inflate the layout
     * @param container
     * @param savedInstanceState
     * @return The layout as view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.option_menu, container, false);
        createDeletionMenu();
        return view;
    }

    private void createDeletionMenu() {
        EditText title = view.findViewById(R.id.title);
        title.setText("Optionen");

        Button delete_track = view.findViewById(R.id.delete_track);
        Button share_track = view.findViewById(R.id.share_track);
        share_track.setBackgroundColor(Color.GRAY);
        delete_track.setBackgroundColor(Color.GRAY);

        delete_track.setOnClickListener(view -> {
            TrackDeletionMenu trackDeletionMenu = new TrackDeletionMenu(track, linearLayout, button);
            trackDeletionMenu.show(associatedManager, "TRACKDELETIONMENU_FRAGMENT_TAG");
        });

        share_track.setOnClickListener(view -> {
            File toShare = Recorder.getInstance().renderTrack(track);

            Uri fileUri = FileProvider.getUriForFile(requireContext(), "de.gruppe.e.klingklang.view.TrackSelectionMenus.fileprovider", toShare);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("audio/mpeg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "This audio was shared with KlingKlang");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share file using"));

            // toShare.delete();
        });

        ImageButton close = view.findViewById(R.id.returnButton);
        close.setOnClickListener(view -> dismiss());

    }
}


