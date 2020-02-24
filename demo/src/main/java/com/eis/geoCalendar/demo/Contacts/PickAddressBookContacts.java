package com.eis.geoCalendar.demo.Contacts;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eis.geoCalendar.demo.R;

/**
 * Class to pick contacts from the device's address book.
 *
 * @author Alessandra Tonin
 * @since 23/02/2020
 */

public class PickAddressBookContacts extends AppCompatActivity {

    //This code will be used to start an activity and then passed to onActivityResult();
    //It's useful to differentiate between different results.
    private static final int PICK_CONTACT = 1;

    /**
     * Method to open the system address book
     *
     * @param view The view calling the method
     * @author Alessandra Tonin
     */
    public void openAddressBook(ScrollView view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    /**
     * Method to handle the picked contact
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
                String number = "";
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                if (hasPhone.equals("1")) {
                    Cursor phones = getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
                    }
                    phones.close();

                    //Put the number in the scrollView
                    LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.linLayout);
                    //Create a new TextView with contact number
                    TextView contactName = new TextView(this);
                    contactName.setText(number);
                    //Adding textView to LinearLayout
                    linearLayout.addView(contactName);

                } else {
                    Toast.makeText(getApplicationContext(), "This contact has no phone number", Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }
        }
    }
}

