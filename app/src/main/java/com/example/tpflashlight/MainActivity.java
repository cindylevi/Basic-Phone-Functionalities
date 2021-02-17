package com.example.tpflashlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.tpflashlight.bases.BaseActivity;
import com.example.tpflashlight.fragments.Contactos;
import com.example.tpflashlight.fragments.FragmentConfig;
import com.example.tpflashlight.fragments.FragmentFotos;
import com.example.tpflashlight.fragments.FragmentGaleria;
import com.example.tpflashlight.fragments.FragmentLinterna;

public class MainActivity extends BaseActivity {
    FragmentLinterna fragmentLinterna;
    FragmentConfig fragmentConfig;
    FragmentFotos fragmentFotos;
    Contactos fragContactos;
    FragmentGaleria fragmentGaleria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFragments();
        showInitialFragment();
    }
    private void createFragments(){
        fragmentLinterna = new FragmentLinterna();
        fragmentConfig = new FragmentConfig();
        fragmentFotos = new FragmentFotos();
        fragContactos = new Contactos();
        fragmentGaleria = new FragmentGaleria();
    }
    private void showInitialFragment(){
        goToFragmentWithReplace(R.id.fragContenedor, fragmentLinterna, false);
    }
    public void showLinterna(){
        goToFragmentWithReplace(R.id.fragContenedor, fragmentLinterna, true);
    }
    public void showContactos(){
        goToFragmentWithReplace(R.id.fragContenedor, fragContactos, true);
    }
    public void showGaleria(){
        goToFragmentWithReplace(R.id.fragContenedor, fragmentGaleria, true);
    }
    public void showFotos(){
        goToFragmentWithReplace(R.id.fragContenedor, fragmentFotos, true);
    }
    public void showConfig(){
        goToFragmentWithReplace(R.id.fragContenedor, fragmentConfig, true);
    }
    public void guardarConfig(String NumTel){
        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NumTel", NumTel);
        editor.commit();
    }
    public String cargarConfig(){
        SharedPreferences preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        String NumTel = preferences.getString("NumTel","No hay un numero de telefono");
        return NumTel;
    }

}