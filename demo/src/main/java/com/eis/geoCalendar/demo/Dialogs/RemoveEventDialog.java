package com.eis.geoCalendar.demo.Dialogs;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.demo.Behaviour.listener.RemoveEventListener;
import com.eis.geoCalendar.demo.R;
import com.google.android.gms.maps.model.Marker;

/**
 * This class defines a DialogFragment to remove a Located Event mark
 *
 * @author Kumar
 */
public class RemoveEventDialog extends AbstractRemoveEventDialog {
    private Marker marker;
    private RemoveEventListener removeEventListener;

    /**
     * Creates the dialog fragment using AlertDialog.Builder
     *
     * @return An AlertDialog object defined to remove a located event
     */
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setTitle(R.string.RemoveEventDialogTitle); //defined in strings.xml
        // Add the buttons
        builder.setPositiveButton(R.string.REMOVE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked DELETE
                removeEventListener.removeMark(marker);

            }
        });
        builder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });

        return builder.create();
    }

    /**
     * @param marker Set the marker to be remove
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    /**
     * @param removeEventListener Listener to call when DELETE is pressed
     */
    public void setRemoveEventListener(RemoveEventListener removeEventListener) {
        this.removeEventListener = removeEventListener;
    }
}
