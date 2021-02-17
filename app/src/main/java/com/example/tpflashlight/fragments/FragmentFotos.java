package com.example.tpflashlight.fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tpflashlight.R;
import com.example.tpflashlight.bases.FragmentBase;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class FragmentFotos extends FragmentBase {
        View layoutRoot = null;

        public FragmentFotos() {
            // Required empty public constructor
        }

        ImageView ivImage;
        Button btnGaleria;
        Button btnCamara;
        boolean eligioCamara = false;
        boolean eligioGaleria = false;
        Uri image_uri;
        private static final int IMAG_PICK_CODE = 1000;
        private static final int PERMISSION_CODE = 1001;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (layoutRoot == null) {
                layoutRoot = inflater.inflate(R.layout.fragment_fotos, container, false);
            }
            ObtenerReferencias();
            CargarDatos();
            SetearListeners();
            return layoutRoot;
        }
        private void CargarDatos(){

        }
        private void SetearListeners() {
            btnGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eligioCamara = false;
                    eligioGaleria = true;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(permissions, PERMISSION_CODE);
                        }
                        else{
                            pickImageFromGallery();
                        }
                    }else{
                        pickImageFromGallery();

                    }
                }
            });
            btnCamara.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View v) {
                    eligioCamara = true;
                    eligioGaleria = false;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permissions, PERMISSION_CODE);
                        }
                        else{
                            OpenCamera();
                        }
                    }else{
                        OpenCamera();
                    }
                }
            });

        }

    private void OpenCamera() {
       Intent camOpen = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       startActivityForResult(camOpen, 100);

    }

    private void pickImageFromGallery() {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK);
            pickPhoto.setType("image/*");
            startActivityForResult(pickPhoto , IMAG_PICK_CODE);
        }




        private void ObtenerReferencias() {
            ivImage = (ImageView) layoutRoot.findViewById(R.id.imageView);
            btnGaleria = (Button) layoutRoot.findViewById(R.id.btnGaleria);
            btnCamara = (Button) layoutRoot.findViewById(R.id.btnCamara);

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode){
                case PERMISSION_CODE: {
                    if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        if(eligioGaleria){
                            pickImageFromGallery();
                        }
                        if(eligioCamara){
                            OpenCamera();
                        }
                    }
                    else {
                        Log.d(TAG, "onRequestPermissionsResult: denied");
                        Toast.makeText(getContainerActivity(), "El permiso ha sido rechazado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode==IMAG_PICK_CODE){
            if(eligioGaleria){
                ivImage.setImageURI(data.getData());

            }
        }
        if(requestCode == 100){
            if(eligioCamara){
                Bitmap captureImage = (Bitmap) data.getExtras().get("data");
                ivImage.setImageBitmap(captureImage);
            }
        }
    }
}