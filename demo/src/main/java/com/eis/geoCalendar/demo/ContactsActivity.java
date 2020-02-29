package com.eis.geoCalendar.demo;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.geoCalendar.demo.Friends.FriendsListManager;
import com.eis.geoCalendar.demo.Friends.RemoveFriendDialogFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles a friend's list, picking up contacts from the device's address book and saving them into
 * ContactsActivity's sharedPreferences file.
 * Contacts can be deleted from this list by clicking them.
 * <p>
 * Contacts will be displayed as a String in a TextView, inside a LinearLayout of a ScrollView.
 * The string's format will be #contactName + " " + #contactNumber.
 * <p>
 * N.B.: the contact's number is parsed before displaying, to remove possible spaces or parenthesis.
 *
 * @author Tonin Alessandra
 */

public class ContactsActivity extends FragmentActivity implements RemoveFriendDialogFragment.RemoveFriendDialogListener {

    private final String EMPTY_STRING = "";
    private final String SPACE = " ";
    private final String EQUALS = " = ";
    private final String HAS_PHONE_TRUE = "1";
    private final String REGEX = "[-() ]";
    private final String NO_NUMBER = "This contact has no phone number";
    private static final int PICK_CONTACT = 1;
    private static final String REMOVE_DIALOG_FRAGMENT_TAG = "RemoveDialogFragment";

    private final String PREFIX = "friend";
    private final static int BEGIN_INDEX = 0;
    private final static int END_INDEX = 6;


    private LinearLayout scrollLinLayout;
    private String numberToRemove;
    private FriendsListManager friendsListManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        scrollLinLayout = findViewById(R.id.scrollLinLayout);

        friendsListManager = new FriendsListManager(this);

        //this method takes friends' list saved in preferences and visualizes it in this activity
        loadContacts();
    }

    /**
     * Opens the system address book.
     *
     * @param view The view calling the method (the view from which the address book is opened).
     */
    public void openAddressBook(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    /**
     * Method to handle the picked contact: extracts the name and the number of the contact, and displays them in the friends' list.
     *
     * @param requestCode The code of the request.
     * @param resultCode  The result of  the request.
     * @param data        The data of the result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK && !(data.getData() == null)) {
                Uri contactData = data.getData();
                String number = EMPTY_STRING;
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                if (hasPhone.equals(HAS_PHONE_TRUE)) {
                    Cursor phoneNumber = getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + EQUALS + contactId, null, null);
                    while (phoneNumber.moveToNext()) {
                        number = phoneNumber.getString(phoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll(REGEX, EMPTY_STRING);
                    }
                    phoneNumber.close();

                    String contactInfo = name + SPACE + number;

                    //need this redundant variable to show the dialog inside the onClick method
                    final String currentNumber = number;

                    //save new friend in preferences, only if it is not yet saved, and display it in the current activity
                    if (friendsListManager.saveFriend(number, name)) {

                        //add a TextView to the LinearLayout
                        TextView contact = new TextView(this);
                        contact.setText(contactInfo);

                        //set an onClickListener for each friend
                        contact.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                showRemoveFriendDialog(currentNumber);
                                return true;
                            }
                        });

                        //display the new contact
                        scrollLinLayout.addView(contact);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), NO_NUMBER, Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        }
    }

    /**
     * This method retrieves friends' list saved in preferences and displays it.
     */
    private void loadContacts() {
        HashMap<String, String> storedFriendsHashMap = friendsListManager.loadFriends();
        // Iterate all key/value pairs
        for (final Map.Entry<String, String> friend : storedFriendsHashMap.entrySet()) {
            if (friend.getKey().substring(BEGIN_INDEX, END_INDEX).equals(PREFIX)) {
                String contactInfo = friend.getValue() + SPACE + friend.getKey().substring(END_INDEX);
                TextView contact = new TextView(this);
                contact.setText(contactInfo);
                contact.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        showRemoveFriendDialog(friend.getKey().substring(END_INDEX));
                        return true;
                    }
                });
                scrollLinLayout.addView(contact);
            }
        }
    }

    /**
     * Creates an instance of {@link RemoveFriendDialogFragment} and shows it, allowing user to choose if remove a friend or not.
     *
     * @param number The phone number of contact to remove (this is the key saved on sharedPreferences file).
     */
    public void showRemoveFriendDialog(String number) {
        numberToRemove = number;
        DialogFragment removeDialog = new RemoveFriendDialogFragment();
        removeDialog.show(getSupportFragmentManager(), REMOVE_DIALOG_FRAGMENT_TAG);
    }

    /**
     * Called when user touched the dialog's remove button.
     * Removes the clicked contact and reload friends' list to update the UI.
     *
     * @param dialog The dialog the user touched.
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        friendsListManager.removeFriend(numberToRemove);
        scrollLinLayout.removeAllViews();
        loadContacts();
    }

    /**
     * Called when user touched the dialog's cancel button.
     * Closes the dialog.
     *
     * @param dialog The dialog the user touched.
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}

