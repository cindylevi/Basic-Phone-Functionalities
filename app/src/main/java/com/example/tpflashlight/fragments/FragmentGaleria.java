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
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.tpflashlight.ImageGallery;
import com.example.tpflashlight.R;
import com.example.tpflashlight.bases.BaseActivity;
import com.example.tpflashlight.bases.FragmentBase;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FragmentGaleria extends FragmentBase {
    View layoutRoot = null;
    ArrayList<String> list = new ArrayList<>();
    GridView gridView;
    private static final int MY_READ_PERMISSION_CODE = 101;


    public FragmentGaleria() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_galeria, container, false);
        }
        setActivityTitle("Contactos");
        ObtenerReferencias();
        RequestAccess();
        SetearListeners();
        return layoutRoot;
    }
    private void SetearListeners() {

    }
    private void ObtenerReferencias() {
        gridView = (GridView) layoutRoot.findViewById(R.id.gridView);
    }

    void RequestAccess(){
        if (ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getContainerActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        }
        else{
            cargarImagen();
        }
        return;
    }

    private void cargarImagen() {
        list = ImageGallery.listofImages(getContainerActivity());
        if(list.size() != 0){
            gridView.setAdapter(new gridAdapter());
        }
        else {
            Toast.makeText(getContainerActivity(), "No hay ninguna imagen en Camara", Toast.LENGTH_SHORT).show();
        }
    }

    public class gridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView=getLayoutInflater().inflate(R.layout.row_layout, parent, false);
                ImageView miImagen= (ImageView) convertView.findViewById(R.id.imagen);
                miImagen.setImageURI(Uri.parse(list.get(position)));
            }
            return convertView;
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MY_READ_PERMISSION_CODE){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                cargarImagen();
            }else{
                Toast.makeText(getContainerActivity(), "Acepta el permiso.", Toast.LENGTH_SHORT).show();
            }
        }
    }

  // public static List<String> getCameraImages(Context context) {
  //     final String[] projection = { MediaStore.Images.Media.DATA };
  //     final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
  //     final String[] selectionArgs = {  };
  //     final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
  //             projection,
  //             selection,
  //             selectionArgs,
  //             null);
  //     ArrayList<String> result = new ArrayList<String>(cursor.getCount());
  //     if (cursor.moveToFirst()) {
  //         final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
  //         do {
  //             final String data = cursor.getString(dataColumn);
  //             result.add(data);
  //         } while (cursor.moveToNext());
  //     }
  //     cursor.close();
  //     return result;
  // }

}