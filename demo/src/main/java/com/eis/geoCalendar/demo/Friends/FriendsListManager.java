package com.eis.geoCalendar.demo.Friends;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;

import com.eis.geoCalendar.demo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all operations related to sharedPreferences, such as load saved friends, save a new contact or remove one.
 *
 * @author Tonin Alessandra
 */
public class FriendsListManager {

    private static final String[] CONTACTS_PERMISSIONS = {Manifest.permission.READ_CONTACTS};

    private final String PREFIX = "friend";

    private final Context context;
    private final SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String PrefFileName;


    public FriendsListManager(Context context) {
        this.context = context;
        this.PrefFileName = context.getResources().getString(R.string.ContactsActivityPreferences);
        this.sharedPref = context.getSharedPreferences(PrefFileName, Context.MODE_PRIVATE);
    }

    /**
     * Saves on ContactsActivity's sharedPreferences file a key-value pair, only if it is not present.
     * Key is the common PREFIX + the parsed phone number, value is the contact's name on device's address book.
     *
     * @param key   The number of the new friend.
     * @param value The name of the new friend.
     * @return True if the pair has been saved, false otherwise.
     */
    public boolean saveFriend(String key, String value) {
        if (sharedPref.getString(PREFIX + key, null) == null) {
            editor = sharedPref.edit();
            editor.putString(PREFIX + key, value);
            editor.apply();

            return true;
        }
        return false;
    }

    /**
     * Removes a friend from the list, deleting it from the ContactsActivity's sharedPreferences file.
     *
     * @param key The number to be removed from the list.
     */
    public void removeFriend(String key) {
        editor = sharedPref.edit();
        editor.remove(PREFIX + key);
        editor.apply();
    }

    /**
     * Gets all friends actually saved on the ContactsActivity's sharedPreferences file.
     *
     * @return An HashMap<String, String> containing all key-value pairs. They will be filtered when needed
     *        (In this case, when calling {@code loadContacts()} from {@link com.eis.geoCalendar.demo.ContactsActivity}).
     */
    public HashMap<String, String> loadFriends() {
        Map<String, ?> storedFriends = sharedPref.getAll();
        return (HashMap<String, String>) storedFriends;

    }

    /**
     * @return An Array containing the required system permission to read contacts from the address book.
     */
    public static String[] getPermissions() {
        return CONTACTS_PERMISSIONS;
    }
}
