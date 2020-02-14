package com.eis.geoCalendar.demo.Bottomsheet;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

/**
 * @author Turcato
 */
public class MapEventBottomSheetBehaviour extends AbstractMapEventBottomSheetBehaviour {

    private BottomSheetBehavior bottomSheetBehavior;
    private View actionView;
    private View removeView;
    private TextView textView;
    private OnActionViewClickListener actionButtonClickListener;
    private OnRemoveViewClickListener removeViewClickListener;
    private boolean isShown;
    private int height;

    /**
     * This constructor is not accessible because the user should call method {@code from(View)}
     * to obtain an instance of this class
     *
     * @param bottomSheetBehavior A BottomSheetBehavior's instance
     * @param height              The height of the layout object used for the BottomSheet
     */
    private MapEventBottomSheetBehaviour(BottomSheetBehavior bottomSheetBehavior, int height) {
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.height = height;
        this.bottomSheetBehavior.setHideable(true);
        hide();
    }

    /**
     * @param view   A design object that will behave as a persistent BottomSheet
     * @param height The height of the layout object used for the BottomSheet
     * @return the MapEventBottomSheetBehaviour associated with the view.
     */
    public static MapEventBottomSheetBehaviour from(@NonNull View view, int height) {
        return new MapEventBottomSheetBehaviour(BottomSheetBehavior.from(view), height);
    }

    /**
     * @param view A design object that will behave as a persistent BottomSheet
     * @return the MapEventBottomSheetBehaviour associated with the view.
     */
    public static MapEventBottomSheetBehaviour from(@NonNull View view) {
        return new MapEventBottomSheetBehaviour(BottomSheetBehavior.from(view), 0);
    }

    public boolean isShown() {
        return isShown;
    }

    /**
     * Sets the BottomSheet to Show up with animation
     */
    @Override
    public void show() {
        bottomSheetBehavior.setState(STATE_EXPANDED);
        isShown = true;
    }

    /**
     * Sets the BottomSheet to hide with animation
     */
    @Override
    public void hide() {
        bottomSheetBehavior.setState(STATE_HIDDEN);
        isShown = false;
    }

    /**
     * @param text The text that will be displayed by the TextView
     */
    @Override
    public void setDisplayedText(CharSequence text) {
        if (textView != null)
            textView.setText(text);
    }

    /**
     * @param textView A TextView Contained in the BottomSheet layout that created this instance
     */
    @Override
    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    /**
     * @param actionView an action Button Contained in the BottomSheet layout that created this instance
     */
    @Override
    public void setActionView(View actionView) {
        this.actionView = actionView;
        this.actionView.setOnClickListener(this);
    }

    @Override
    public void setRemoveView(View removeView) {
        this.removeView = removeView;
        this.removeView.setOnClickListener(this);
    }

    /**
     * Sets a Listener for the action View
     *
     * @param listener A Listener for the action View
     */
    @Override
    public void setOnActionViewClickListener(OnActionViewClickListener listener) {
        this.actionButtonClickListener = listener;
    }

    /**
     * Sets a Listener for the remove View
     *
     * @param listener A Listener for the remove View
     */
    @Override
    public void setOnRemoveViewClickListener(OnRemoveViewClickListener listener) {
        this.removeViewClickListener = listener;
    }

    /**
     * Called by the action Button when a click occurs, calls the previously set listener
     *
     * @param v The View object that caused this callback
     */
    @Override
    public void onClick(View v) {
        if (v == actionView) {
            if (actionButtonClickListener != null)
                actionButtonClickListener.OnActionViewClick(v);
        } else if (v == removeView) {
            removeViewClickListener.OnRemoveViewClick(v);
        }
    }

    @Override
    public int getFullLayoutHeight() {
        return height;
    }
}
