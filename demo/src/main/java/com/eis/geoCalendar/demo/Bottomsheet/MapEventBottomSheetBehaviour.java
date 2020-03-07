package com.eis.geoCalendar.demo.Bottomsheet;

import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eis.geoCalendar.demo.Bottomsheet.listener.OnActionViewClickListener;
import com.eis.geoCalendar.demo.Bottomsheet.listener.OnRemoveViewClickListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Map;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN;

/**
 * Class built as an Object Pool, each View object can be used to create one and only Behaviour object
 *
 * Allows to build and control a Bottom Sheet for a Map of events, by default it has an Action View,
 * a Remove View and a Text View
 *
 * @author Turcato
 */
public class MapEventBottomSheetBehaviour implements BottomSheetBehaviour {
    private static Map<View, MapEventBottomSheetBehaviour> instances = new ArrayMap<>();

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
     * Builds an object starting from a BottomSheetBehavior defining its layout representation height
     *
     * @param bottomSheetBehavior A BottomSheetBehavior's instance
     * @param height              The height (pixels) of the layout object used for the BottomSheet
     */
    private MapEventBottomSheetBehaviour(BottomSheetBehavior bottomSheetBehavior, int height) {
        this.bottomSheetBehavior = bottomSheetBehavior;
        this.height = height;
        this.bottomSheetBehavior.setHideable(true);
        hide();
    }

    /**
     * @param view      A design object that will behave as a persistent BottomSheet, must have the property
     *                  layout_behavior="com.google.android.material.bottomsheet.BottomSheetBehavior">
     * @param height    The height of the layout object used for the BottomSheet
     *
     * @return the MapEventBottomSheetBehaviour associated with the view.
     */
    public static MapEventBottomSheetBehaviour from(@NonNull View view, int height) {
        if (instances.containsKey(view))
            return instances.get(view);

        MapEventBottomSheetBehaviour newInstance = new MapEventBottomSheetBehaviour(BottomSheetBehavior.from(view), height);
        instances.put(view, newInstance);
        return newInstance;
    }

    /**
     * @param view  A design object that will behave as a persistent BottomSheet, must have the property
     *      *                  layout_behavior="com.google.android.material.bottomsheet.BottomSheetBehavior">
     * @return The MapEventBottomSheetBehaviour associated with the view.
     */
    public static MapEventBottomSheetBehaviour from(@NonNull View view) {
        return new MapEventBottomSheetBehaviour(BottomSheetBehavior.from(view), 0);
    }

    /**
     * @return {@code True} if the Bottom Sheet is set to be shown on the screen, false otherwise
     */
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
            if (removeViewClickListener != null)
                removeViewClickListener.OnRemoveViewClick(v);
        }
    }

    /**
     * @return The defined layout's height for this Bottom Sheet, <=0 means it was probably not set
     */
    @Override
    public int getFullLayoutHeight() {
        return height;
    }

    /**
     * @param visible If set {@code True} will make the view visible, otherwise invisible
     */
    public void setActionViewVisible(boolean visible) {
        if (visible)
            actionView.setVisibility(View.VISIBLE);
        else
            actionView.setVisibility(View.INVISIBLE);
    }

    /**
     * @param visible If set {@code True} will make the view visible, otherwise invisible
     */
    public void setRemoveViewVisible(boolean visible) {
        if (visible)
            removeView.setVisibility(View.VISIBLE);
        else
            removeView.setVisibility(View.INVISIBLE);
    }
}
