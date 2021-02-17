package com.example.tpflashlight.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tpflashlight.R;
import com.example.tpflashlight.bases.FragmentBase;

public class navBar extends FragmentBase {
    View layoutRoot = null;

    public navBar() {
        // Required empty public constructor
    }

    Button btnLinterna, btnConfig, btnFotos, btnContactos, btnGaleria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_menu, container, false);
        }
        ObtenerReferencias();
        SetearListeners();
        return layoutRoot;
    }


    private void SetearListeners() {
        btnLinterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContainerActivity().showLinterna();
            }
        });
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContainerActivity().showConfig();
            }
        });
        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContainerActivity().showFotos();
            }
        });
        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContainerActivity().showContactos();
            }
        });
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContainerActivity().showGaleria();
            }
        });
    }


    private void ObtenerReferencias() {
        btnLinterna = (Button) layoutRoot.findViewById(R.id.btnLinterna);
        btnConfig = (Button) layoutRoot.findViewById(R.id.btnConfig);
        btnFotos = (Button) layoutRoot.findViewById(R.id.btnFotos);
        btnContactos = (Button) layoutRoot.findViewById(R.id.btnContactos);
        btnGaleria = (Button) layoutRoot.findViewById(R.id.btnGaleria);
    }
}