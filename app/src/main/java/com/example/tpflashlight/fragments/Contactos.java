package com.example.tpflashlight.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.tpflashlight.R;
import com.example.tpflashlight.bases.FragmentBase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Contactos extends FragmentBase {
    View layoutRoot = null;
    ListView lvContactos;

    public Contactos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_contactos, container, false);
        }
        setActivityTitle("Contactos");
        ObtenerReferencias();

        SetearListeners();
        fetchContacts();
        return layoutRoot;
    }
    private void SetearListeners() {
        lvContactos.setOnItemClickListener(lvContactos_click);
    }
    private void ObtenerReferencias() {
        lvContactos = (ListView)layoutRoot.findViewById(R.id.lvContactos);
    }
    void RequestAccess(){

    }

    private void fetchContacts(){
        ArrayList<String> contactos = new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        ContentResolver resolver = getContainerActivity().getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactos.add(name+" / "+number);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContainerActivity(), android.R.layout.simple_list_item_1, contactos);
        lvContactos.setAdapter(itemsAdapter);
    }
    AdapterView.OnItemClickListener lvContactos_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String contacto = (String) lvContactos.getItemAtPosition(position);
            String[] parts = contacto.split("/");
            String number = parts[1];
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            startActivity(callIntent);
        }
    };
}