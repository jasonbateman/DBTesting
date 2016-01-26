package com.example.batemanj16.dbtesting;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    List<Contact> contacts = new ArrayList<Contact>();
    ListView contactListView;
    Uri imageURI = Uri.parse("android.resource://com.example.batemanj16.dbtesting/drawable/ic_menu_camera");

    EditText nameTxt, phoneTxt, emailTxt, addressTxt;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        nameTxt = (EditText) findViewById(R.id.txtName);
        phoneTxt = (EditText) findViewById(R.id.txtPhone);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        addressTxt = (EditText) findViewById(R.id.txtAdress);
        contactListView = (ListView) findViewById(R.id.testListView);
        dbHandler = new DBHandler(getApplicationContext());


        //Tab Handler
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);


        dbHandler = new DBHandler(getApplicationContext());

        final Button addBtn = (Button) findViewById(R.id.btnCreate);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view) {
                    Contact contact = new Contact(dbHandler.getContactsCount(),
                        String.valueOf(nameTxt.getText()),
                        String.valueOf(phoneTxt.getText()),
                        String.valueOf(emailTxt.getText()),
                        String.valueOf(addressTxt.getText()),
                        imageURI);
                dbHandler.createContact(contact);
                contacts.add(contact);
                Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + " has been created!", Toast.LENGTH_SHORT).show();
                populateList();
                nameTxt.setText("");
                phoneTxt.setText("");
                emailTxt.setText("");
                addressTxt.setText("");

            }
        });


        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        List<Contact> addableContacts = dbHandler.getAllContacts();
        int contactCount= dbHandler.getContactsCount();

        for(int i = 0; i<contactCount; i++){
            contacts.add(addableContacts.get(i));
        }
        if(!addableContacts.isEmpty())
            populateList();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private class ContactListAdapter extends ArrayAdapter<Contact>{

        public ContactListAdapter(){
            super (HomeScreen.this, R.layout.listview_item, contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Contact currentContact = contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            TextView phone = (TextView) view.findViewById((R.id.phone));
            TextView email = (TextView) view.findViewById(R.id.email);
            TextView address = (TextView) view.findViewById(R.id.address);

            name.setText(currentContact.getName());
            phone.setText(currentContact.getPhone());
            email.setText(currentContact.getEmail());
            address.setText(currentContact.getAddress());

            return view;


        }
    }

    private void addContacts(String name, String phone, String email, String address){
        int HOLDER_PLACE_ID = 1;
        Uri HOLDER_PLACE_URI = imageURI;

        contacts.add(new Contact(HOLDER_PLACE_ID,  name, phone, email, address, HOLDER_PLACE_URI));

    }

    private void populateList(){
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
