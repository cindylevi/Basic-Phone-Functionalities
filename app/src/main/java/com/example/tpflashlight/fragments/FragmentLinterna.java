package com.example.tpflashlight.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.tpflashlight.R;
import com.example.tpflashlight.bases.FragmentBase;

import static androidx.core.content.ContextCompat.getSystemService;

public class FragmentLinterna extends FragmentBase {
    View layoutRoot = null;

    public FragmentLinterna() {
        // Required empty public constructor
    }

    ImageButton ibApagar;
    ImageView ivFlashlight;
    Button btnPanicButton;
    Boolean LinternaPrendida = false;
    //private Camera camera;
    //Parameters param;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_linterna, container, false);
        }
        RequestAccess();
        ObtenerReferencias();
        CargarDatos();
        SetearListeners();
        return layoutRoot;
    }
    private void CargarDatos(){
        btnPanicButton.setText(getContainerActivity().cargarConfig());
        //if (ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //    ActivityCompat.requestPermissions(getContainerActivity(), new String[]{Manifest.permission.CAMERA,}, 1000);
        //} else {
//
//
        //    if (getContainerActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
        //        camera = Camera.open();
        //        param = camera.getParameters();
        //    }
        //}
    }
    private void SetearListeners() {
        ibApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LinternaPrendida) {
                     ibApagar.setImageResource(R.mipmap.ic_verdebutton_background);
                    ivFlashlight.setImageResource(R.mipmap.ic_noflashlight);
                    LinternaPrendida = false;
                    PrenderLinterna(LinternaPrendida);

                } else {
                    ibApagar.setImageResource(R.mipmap.ic_redbutton_background);
                    ivFlashlight.setImageResource(R.mipmap.ic_flashlight);
                    LinternaPrendida = true;
                    PrenderLinterna(LinternaPrendida);
                }
            }
        });
        btnPanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getContainerActivity().cargarConfig() != "No hay un numero de telefono"){
                    MakeCall();
                }
                else {
                    Toast.makeText(getContainerActivity(), "Ingrese un numero de telefono!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void MakeCall(){
        String number = getContainerActivity().cargarConfig();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        startActivity(callIntent);
    }
    private void ObtenerReferencias() {
        ibApagar = (ImageButton) layoutRoot.findViewById(R.id.iBApagar);
        ivFlashlight = (ImageView) layoutRoot.findViewById(R.id.iVFlashlight);
        btnPanicButton = (Button) layoutRoot.findViewById(R.id.btnPanic);

    }

    //public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    //    if (requestCode == 1000) {
    //        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
    //            Toast.makeText(getContainerActivity(), "Comencemos!", Toast.LENGTH_SHORT).show();
//
    //        }
    //        else{
    //            Toast.makeText(getContainerActivity(), "sos un muerto kpo!", Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //    else{
    //        Toast.makeText(getContainerActivity(), "que hiciste mal kpo!", Toast.LENGTH_SHORT).show();
    //    }
    //}

    void RequestAccess(){
        if (ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContainerActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getContainerActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE}, 1000);
        }
        return;
    }

    void PrenderLinterna(boolean Prender){
        CameraManager camManager = (CameraManager) getContainerActivity().getSystemService(Context.CAMERA_SERVICE);
        String cameraId = null;
        try {
            cameraId = camManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            Toast.makeText(getContainerActivity(), "No hay acceso a la camara", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            camManager.setTorchMode(cameraId, Prender);
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(getContainerActivity(), "No hay acceso a la camara", Toast.LENGTH_SHORT).show();
        }
    }
}