package com.example.tpflashlight.bases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tpflashlight.R;

public class BaseActivity extends AppCompatActivity {
    public void goToFragmentWithAdd(int intContainerId, Fragment fragment, Boolean addBackStack){
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        String strTag;

        fragmentManager=this.getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        if(addBackStack){
            strTag = fragment.getClass().getName();
            fragmentTransaction.add(intContainerId, fragment, strTag);
            fragmentTransaction.addToBackStack(strTag);

        }
        else {
            fragmentTransaction.replace(intContainerId, fragment);
        }
        fragmentTransaction.commit();
    }
    public void goToFragmentWithReplace(int intContainerId, Fragment fragment, Boolean addBackStack){
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        String strTag;

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                R.anim.enter_left_to_right, R.anim.exit_left_to_right);

        if(addBackStack){
            strTag=fragment.getClass().getName();
            fragmentTransaction.replace(intContainerId, fragment,strTag);
            fragmentTransaction.addToBackStack(strTag);

        }
        else{
            fragmentTransaction.replace(intContainerId, fragment);
        }
        fragmentTransaction.commit();
    }


}
