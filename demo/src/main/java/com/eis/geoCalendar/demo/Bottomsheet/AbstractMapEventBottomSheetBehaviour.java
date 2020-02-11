package com.eis.geoCalendar.demo.Bottomsheet;

import android.widget.Button;
import android.widget.TextView;

/**
 * Class Representing the Behaviour for a {@code MapEventBottomSheetBehaviour} object
 * <p>
 * By Default this Object is projected to have an Action Button and a Text
 *
 * @author Turcato
 */
public abstract class AbstractMapEventBottomSheetBehaviour //extends BottomSheetBehavior
        implements Button.OnClickListener {

    /**
     * @param actionBtn An action Button Contained in the BottomSheet layout that created this instance
     */
    public abstract void setActionButton(Button actionBtn);

    /**
     * @param textView A TextView Contained in the BottomSheet layout that created this instance
     */
    public abstract void setTextView(TextView textView);


    /**
     * Sets a Listener for the action Button
     *
     * @param listener A Listener for the action Button single click action
     */
    public abstract void setOnActionButtonClickListener(OnActionButtonClickListener listener);

    /**
     * @param text The text that will be displayed by the TextView
     */
    public abstract void setDisplayedText(CharSequence text);

    /**
     * Expands the BottomSheet
     */
    public abstract void show();

    /**
     * Collapses the BottomSheet
     */
    public abstract void hide();

    /**
     * @return {@code True} if the sheet is shown in the window, {@code False} otherwise
     */
    public abstract boolean isShown();

    /**
     * @return The height of the BottomSheet when shown in the window
     */
    public abstract int getFullLayoutHeight();

}
