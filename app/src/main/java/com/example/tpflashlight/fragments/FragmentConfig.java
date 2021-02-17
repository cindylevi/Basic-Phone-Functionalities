package com.example.tpflashlight.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpflashlight.R;
import com.example.tpflashlight.bases.FragmentBase;

public class FragmentConfig extends FragmentBase {
    View layoutRoot = null;
    Button Guardar;
    EditText edNumTel;
    public FragmentConfig() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_config, container, false);
        }
       setActivityTitle("Configuracion");
        ObtenerReferencias();
        SetearListeners();
        return layoutRoot;
    }
    private void SetearListeners() {
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContainerActivity().guardarConfig(edNumTel.getText().toString());
                Toast.makeText(getContainerActivity(), "El numero se ha guardado", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void ObtenerReferencias() {
        Guardar = (Button) layoutRoot.findViewById(R.id.btnGuardar);
        edNumTel = (EditText) layoutRoot.findViewById(R.id.edNumTel);

    }
}