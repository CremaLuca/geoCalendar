package com.eis.geoCalendar.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles a friend's list, picking up contacts from the device's address book and saving them into app's sharedPreferences file.
 * Contacts can be deleted from friends' list by clicking them.
 * <p>
 * Contacts will be displayed as a String in a TextView, inside a LinearLayout of a ScrollView.
 * The string's format will be #contactName + " " + #contactNumber.
 * <p>
 * N.B.: the contact's number is parsed before displaying to remove spaces or parenthesis, if present.
 *
 * @author Tonin Alessandra
 */

public class ContactsActivity extends FragmentActivity implements RemoveFriendDialogFragment.RemoveFriendDialogListener {

    private final String EMPTY_STRING = "";
    private final String SPACE = " ";
    private final String HAS_PHONE = "1";
    private final String REGEX = "[-() ]";
    private final String NO_NUMBER = "This contact has no phone number";
    private static final int PICK_CONTACT = 1;
    private static final String REMOVEDIALOGFRAGMENT_TAG = "RemoveDialogFragment";

    private LinearLayout scrollLinLayout;
    private String numberToRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        scrollLinLayout = findViewById(R.id.scrollLinLayout);

        //this method takes friends' list saved in preferences and visualizes them in this activity
        loadContacts();
    }

    /**
     * Method to open the system address book
     *
     * @param view The view calling the method
     */
    public void openAddressBook(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    /**
     * Method to handle the picked contact: extracts the name and the number of the contact, and displays them in a TextView
     *
     * @param requestCode The code of the request
     * @param resultCode  The result of  the request
     * @param data        The data of the result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                String number = EMPTY_STRING;
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                if (hasPhone.equals(HAS_PHONE)) {
                    Cursor phones = getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll(REGEX, EMPTY_STRING);
                    }
                    phones.close();

                    String contactInfo = name + SPACE + number;

                    //need this reduntand variable to show the dialog inside the onClick method
                    final String currentNumber = number;

                    //save new friend in preferences, only if it is not yet saved, and display it in the current activity
                    if (ContactsActivity.this.getPreferences(Context.MODE_PRIVATE).getString(number, null) == null) {
                        savePreferences(number, name);

                        //add a TextView to the LinearLayout
                        TextView contact = new TextView(this);
                        contact.setText(contactInfo);

                        //set an onClickListener for each friend
                        contact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showRemoveFriendDialog(currentNumber);
                            }
                        });
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
     * This method takes friends' list saved in preferences and displays them
     */
    private void loadContacts() {
        SharedPreferences sharedPref = ContactsActivity.this.getPreferences(Context.MODE_PRIVATE);
        Map<String, ?> storedFriends = sharedPref.getAll();
        HashMap<String, String> storedFriendsHashMap = (HashMap<String, String>) storedFriends;
        // Iterate all key/value pairs
        for (final Map.Entry<String, String> friend : storedFriendsHashMap.entrySet()) {
            String contactInfo = friend.getValue() + SPACE + friend.getKey();
            TextView contact = new TextView(this);
            contact.setText(contactInfo);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showRemoveFriendDialog(friend.getKey());
                }
            });
            scrollLinLayout.addView(contact);
        }

    }

    /**
     * This method saves friends' list on sharedPreferences file
     *
     * @param key   The number of the new friend
     * @param value The name of the new friend
     */
    private void savePreferences(String key, String value) {
        SharedPreferences sharedPref = ContactsActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //save key-value pairs: key is the number, value is the name
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * This method removes a friend from the list, canceling it from the sharedPreferences file
     *
     * @param key The number to be removed from the list
     */
    public void removeFriend(String key) {
        SharedPreferences.Editor editor = ContactsActivity.this.getPreferences(Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Creates an instance of {@link RemoveFriendDialogFragment}, allowing user to choose if remove it or not
     *
     * @param number The phone number of contact to remove (this is the key saved on sharedPreferences file)
     */
    public void showRemoveFriendDialog(String number) {
        numberToRemove = number;
        // Create an instance of the dialog fragment and show it
        DialogFragment removeDialog = new RemoveFriendDialogFragment();
        removeDialog.show(getSupportFragmentManager(), REMOVEDIALOGFRAGMENT_TAG);
    }

    /**
     * Called when user touched the dialog's remove button
     *
     * @param dialog The dialog the user touched.
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        removeFriend(numberToRemove);
        scrollLinLayout.removeAllViews();
        loadContacts();
    }

    /**
     * Called when user touched the dialog's cancel button
     *
     * @param dialog The dialog the user touched.
     */
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
