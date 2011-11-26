package org.pravin.android.sendcontact;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListContactsActivity extends Activity implements OnItemClickListener {
	private static final String TAG= "ListContactsActivity";
	
    private ListView contactsList;

	private Cursor cursor; 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        contactsList = (ListView) findViewById(R.id.listContacts);

        contactsList.setOnItemClickListener(this);
        
        populateContactList();
    }
	private void populateContactList() {
		cursor = getContacts();
		String[] fields = new String[] {
			ContactsContract.Data.DISPLAY_NAME
		};
	    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
	                fields, new int[] {R.id.contactName});
	        contactsList.setAdapter(adapter);
	}
	private Cursor getContacts() {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME,
		};
		String selection = null, 
			   selectionArgs[] = null, 
			   sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		return managedQuery(uri, projection, selection, selectionArgs, sortOrder);
	}
	public void onItemClick(AdapterView<?> list, View item, int pos, long id) {
		cursor.moveToPosition(pos);
		Log.d(TAG,cursor.getString(1));
	}
}