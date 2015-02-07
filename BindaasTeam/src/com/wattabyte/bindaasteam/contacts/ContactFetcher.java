package com.wattabyte.bindaasteam.contacts;

import java.util.ArrayList;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ContactFetcher {
	
	private Context context;

    public ContactFetcher(Context c) {
        this.context = c;
    }

    public ArrayList<ContactUtil> fetchAll() {
        ArrayList<ContactUtil> listContacts = new ArrayList<ContactUtil>();
        CursorLoader cursorLoader = new CursorLoader(context, 
                ContactsContract.Contacts.CONTENT_URI, // uri
                null, // the columns to retrieve (all)
                null, // the selection criteria (none)
                null, // the selection args (none)
                null // the sort order (default)
        );
                // This should probably be run from an AsyncTask
        Cursor c = cursorLoader.loadInBackground();
        if (c.moveToFirst()) {
            do {
                ContactUtil contact = loadContactData(c);
                listContacts.add(contact);
            } while (c.moveToNext());
        }
        c.close();
        return listContacts;
    }

    private ContactUtil loadContactData(Cursor c) {
        // Get Contact ID
        int idIndex = c.getColumnIndex(ContactsContract.Contacts._ID);
        String contactId = c.getString(idIndex);
        // Get Contact Name
        int nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String contactDisplayName = c.getString(nameIndex);
        ContactUtil contact = new ContactUtil(contactId, contactDisplayName);
        fetchContactNumbers(c, contact);
        return contact;
    }


    public void fetchContactNumbers(Cursor cursor, ContactUtil contact) {
        // Get numbers
        final String[] numberProjection = new String[] { Phone.NUMBER, Phone.TYPE, };
        Cursor phone = new CursorLoader(context, Phone.CONTENT_URI, numberProjection,
                Phone.CONTACT_ID + "= ?", 
                new String[] { String.valueOf(contact.id) },
                null).loadInBackground();

        if (phone.moveToFirst()) {
            final int contactNumberColumnIndex = phone.getColumnIndex(Phone.NUMBER);
            final int contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE);

            while (!phone.isAfterLast()) {
                final String number = phone.getString(contactNumberColumnIndex);
                final int type = phone.getInt(contactTypeColumnIndex);
                String customLabel = "Custom";
                CharSequence phoneType = 
                                   ContactsContract.CommonDataKinds.Phone.getTypeLabel(
                        context.getResources(), type, customLabel);
                contact.addNumber(number, phoneType.toString());
                phone.moveToNext();
            }

        }
        phone.close();
    }

   
}
