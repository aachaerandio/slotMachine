package com.aachaerandio.slotmachine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aachaerandio.slotmachine.data.State;

public class ClaimDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_claim_dialog, null);

        ImageView image = (ImageView)view.findViewById(R.id.dialog_image);
        TextView winnerText = (TextView)view.findViewById(R.id.winner_text);
        State.SlotIcon slotIcon = State.SlotIcon.values()[getArguments().getInt(MainActivity.WINNER_ICON)];
        image.setImageResource(slotIcon.slotId);
        winnerText.setText(slotIcon.toString());
        builder.setView(view);
        // Add action buttons
 /*               .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });*/
        return builder.create();
    }
}
