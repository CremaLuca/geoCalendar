package com.eis.geoCalendar.demo.Bottomsheet;

import android.view.View;
import android.widget.TextView;

/**
 * Class Representing the Behaviour for a {@code MapEventBottomSheetBehaviour} object
 * <p>
 * By Default this Object is projected to have an Action Button and a Text
 *
 * @author Turcato
 */
public abstract class AbstractMapEventBottomSheetBehaviour //extends BottomSheetBehavior
        implements View.OnClickListener {

    /**
     * @param actionView An action Button Contained in the BottomSheet layout that created this instance
     */
    public abstract void setActionView(View actionView);

    /**
     * @param removeView A "remove" Button Contained in the BottomSheet layout that created this instance
     */
    public abstract void setRemoveView(View removeView);


    /**
     * @param textView A TextView Contained in the BottomSheet layout that created this instance
     */
    public abstract void setTextView(TextView textView);


    /**
     * Sets a Listener for the action View
     *
     * @param listener A Listener for the action View single click action
     */
    public abstract void setOnActionViewClickListener(OnActionViewClickListener listener);

    /**
     * Sets a Listener for the remove View
     *
     * @param listener A Listener for the remove View single click action
     */
    public abstract void setOnRemoveViewClickListener(OnRemoveViewClickListener listener);

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

    /**
     * @param visible If set {@code True} will make the view visible, otherwise invisible
     */
    public abstract void setRemoveViewVisible(boolean visible);

    /**
     * @param visible If set {@code True} will make the view visible, otherwise invisible
     */
    public abstract void setActionViewVisible(boolean visible);

}
