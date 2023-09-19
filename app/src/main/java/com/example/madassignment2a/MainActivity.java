package com.example.madassignment2a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    MainActivityData mainActivityDataViewModel;
    String verifyScreenState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityDataViewModel = new ViewModelProvider(this).get(MainActivityData.class);

        String tmpScreenState = "Home";
        if(savedInstanceState != null)
        {
            mainActivityDataViewModel.setDisplayScreen(savedInstanceState.getString("screenValue"));
            tmpScreenState = savedInstanceState.getString("screenValue");
        }
        verifyScreenState = tmpScreenState;

        mainActivityDataViewModel.DisplayScreen.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {

                if (string.compareTo(verifyScreenState) != 0) {
                    Log.d("String", string);

                    if (string.equals("Home")) {
                        loadHomePageFragment();
                    }
                    if (string.equals("Play")) {
                        loadPlayPageFragment();
                    }
                    verifyScreenState = string;
                }
            }
        });
    }

    public void loadContactsFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerView);
        if(frag==null){
            fm.beginTransaction().add(R.id.fragmentContainerView,playPageFragment).commit();
        }
        else{
            fm.beginTransaction().replace(R.id.fragmentContainerView,playPageFragment).commit();
        }
    }

    public void loadContactInfoFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragmentContainerView);
        if(frag==null){
            fm.beginTransaction().add(R.id.fragmentContainerView,playPageFragment).commit();
        }
        else{
            fm.beginTransaction().replace(R.id.fragmentContainerView,playPageFragment).commit();
        }
    }
}