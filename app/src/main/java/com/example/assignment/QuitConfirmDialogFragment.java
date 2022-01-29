package com.example.assignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class QuitConfirmDialogFragment extends DialogFragment {
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GamePage.Instance.finish();
                        System.exit(0);
                        IsShown = false;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        IsShown = false;
                    }
                });
        return builder.create();
    }

}
