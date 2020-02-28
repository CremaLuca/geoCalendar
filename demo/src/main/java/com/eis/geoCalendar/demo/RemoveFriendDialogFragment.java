package com.eis.geoCalendar.demo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * This class handles the creation of a dialog when clicking on an item of the friends' list.
 * It is used to delete a contact from this list.
 *
 * @author Tonin Alessandra
 */
public class RemoveFriendDialogFragment extends DialogFragment {

    private final static String CLASS_EXCEPTION = "ContactsActivity must implement NoticeDialogListener";

    /**
     * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     */
    public interface RemoveFriendDialogListener {
        /**
         * Callback when the positive button of the dialog is clicked.
         *
         * @param dialog The dialog containing the clicked button.
         */
        void onDialogPositiveClick(DialogFragment dialog);

        /**
         * Callback when the negative button of the dialog is clicked.
         *
         * @param dialog The dialog containing the clicked button.
         */
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private RemoveFriendDialogListener removeListener;

    // Instantiates the RemoveFriendDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            removeListener = (RemoveFriendDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(CLASS_EXCEPTION);
        }
    }

    /**
     * Builds an AlertDialog object and sets up the button click handlers.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     * @return The AlertDialog object created.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.REMOVE_FRIEND)
                .setMessage(R.string.dialog_remove_friend)
                .setPositiveButton(R.string.REMOVE, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeListener.onDialogPositiveClick(RemoveFriendDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeListener.onDialogNegativeClick(RemoveFriendDialogFragment.this);
                    }
                });
        return builder.create();
    }
}